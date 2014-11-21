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
import com.jme3.network.serializing.Serializer;
import com.jme3.scene.Spatial;
import com.jme3.system.JmeContext;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities.Planet;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain extends SimpleApplication implements ConnectionListener {

    Server myServer;
    int connections = 0;
    int connectionsOld = -1;
    
    UpdateClientMessage update;
    
    static Planet[] myPlanets;
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());
    
    public static void main(String[] args) {
        logger.setLevel(Level.SEVERE);
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
        
        //GreetingMessage hm =  new GreetingMessage("Hi server, do you hear me?", myPlanets);
    }

    @Override
    public void simpleInitApp() {
        
        
        try {

            myServer = Network.createServer(Globals.NAME, Globals.VERSION, 6143, 6143);
            myServer.start();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not create network connection.");
        }
        Serializer.registerClasses(GreetingMessage.class, UpdateClientMessage.class);
        // Serializer.registerClass(PlanetMessage.class);
        myServer.addMessageListener(new ServerListener(), GreetingMessage.class);
        
        //Message gm = new GreetingMessage("Generating map", myPlanets);
        //myServer.broadcast(gm);
        
        // myServer.addMessageListener(new ServerListener(), PlanetMessage.class);

        


        /*
         Serializer.registerClass(CubeMessage.class);
         myServer.addMessageListener(new ServerListener(this, myServer),
         CubeMessage.class);
         */
        
        update = new UpdateClientMessage("Im updating");
        update.setInfo(new Vector3f(20.0f, 0.0f, 20.0f).toString(), ColorRGBA.Blue.toString());
    }
    
    @Override
    public void simpleUpdate(float deltaTime) {
        connections = myServer.getConnections().size();
        if (connectionsOld != connections) {
            System.out.println("Server connections: " + connections);
            connectionsOld = connections;
	  myServer.broadcast(update);
        }
        
        
        //myServer.broadcast(update);
        
        try
        {
	  Thread.sleep(500);
        } catch (InterruptedException ex)
        {
	  Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
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
