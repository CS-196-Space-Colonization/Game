/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.thecolony.tractus.networking.messages.UpdateClientMessage;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain extends SimpleApplication implements ConnectionListener
{

    Server myServer;
    int connections = 0;
    int connectionsOld = -1;
    Message update;
    boolean started = false;

    public ServerMain()
    {
        Logger.getLogger("").setLevel(Level.SEVERE);
        start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp()
    {
        try
        {
	  myServer = Network.createServer(Globals.NAME, Globals.VERSION, 6143, 6143);
	  update = new UpdateClientMessage();
	  myServer.start();
	  Globals.registerClasses();
	  myServer.addMessageListener(new ServerListener(), UpdateClientMessage.class);
	  new ClientMain();
	  
	  myServer.broadcast(update);
        } catch (IOException ex)
        {
	  System.out.println("Could not create network connection.");
        }
    }

    @Override
    public void simpleUpdate(float deltaTime)
    {
        connections = myServer.getConnections().size();
        if(connections == 1)
	  started = true;
        if (started && connections == 0)
        {
	  System.out.println("destroy");
	  destroy();
        }
        if (connectionsOld != connections)
        {
	  System.out.println("Server connections: " + connections);
	  connectionsOld = connections;
	  myServer.broadcast(update);
        }

    }

    @Override
    public void destroy()
    {
        try
        {
	  myServer.close();
        } catch (Exception ex)
        {
        }
        super.destroy();
    }

    /**
     * Specify what happens when a client connects to this server
     */
    public void connectionAdded(Server server, HostedConnection client)
    {
    }

    /**
     * Specify what happens when a client disconnects from this server
     */
    public void connectionRemoved(Server server, HostedConnection client)
    {
    }
}
