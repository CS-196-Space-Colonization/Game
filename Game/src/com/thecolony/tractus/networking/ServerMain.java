/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

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
	  update = new UpdateClientMessage(new Vector3f(20.0f, 0.0f, 20.0f), ColorRGBA.Blue);
	  new ClientMain();
	  myServer.start();
        } catch (IOException ex)
        {
	  System.out.println("Could not create network connection.");
        }
        Globals.registerClasses();
        myServer.addMessageListener(new ServerListener(), UpdateClientMessage.class);


    }

    @Override
    public void simpleUpdate(float deltaTime)
    {
        connections = myServer.getConnections().size();
        if (connections == 0)
        {
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
