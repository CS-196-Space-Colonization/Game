/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacecolonization.networking;

import com.jme3.app.SimpleApplication;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This package contains an example that shows how you enqueue changes to the
 * scene graph correctly from the network thread -- see ClientListener.
 */
public class ServerMain extends SimpleApplication implements ConnectionListener {

    Server myServer;
    int connections = 0;
    int connectionsOld = -1;

    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) {
        logger.setLevel(Level.SEVERE);
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        try {
            myServer = Network.createServer("My Cool Game", 1, 6143, 6143);
            myServer.start();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not create network connection.");
        }
        Serializer.registerClass(GreetingMessage.class);
        myServer.addMessageListener(new ServerListener(), GreetingMessage.class);
        Serializer.registerClass(InetAddressMessage.class);
        Serializer.registerClass(InetAddress.class, new InetAddressSerializer());
        
        myServer.addMessageListener(new ServerListener(), InetAddressMessage.class);
        myServer.addConnectionListener(this);
        Serializer.registerClass(TextMessage.class);
        myServer.addMessageListener(new ServerListener(), TextMessage.class);
        
        /*
        Serializer.registerClass(CubeMessage.class);
        myServer.addMessageListener(new ServerListener(this, myServer),
                CubeMessage.class);
        */
    }

    @Override
    public void update() {
        connections = myServer.getConnections().size();
        if (connectionsOld != connections) {
            System.out.println("Server connections: " + connections);
            connectionsOld = connections;
        }

    }

    @Override
    public void destroy() {
        try {
            myServer.close();
        } catch (Exception ex) {
        }
        super.destroy();
    }

    /**
     * Specify what happens when a client connects to this server
     */
    public void connectionAdded(Server server, HostedConnection client) {
    }

    /**
     * Specify what happens when a client disconnects from this server
     */
    public void connectionRemoved(Server server, HostedConnection client) {
    }
}