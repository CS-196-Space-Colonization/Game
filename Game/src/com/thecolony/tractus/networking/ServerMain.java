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
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.JmeContext;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities.Planet;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;



public class ServerMain extends SimpleApplication implements ConnectionListener {

    Server myServer;
    int connections = 0;
    int connectionsOld = -1;
    private static Planet [] mPlanets; 

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
    /*
    private void generatePlanet(int index)
    {
        mPlanets = new Planet[10];
        for (int i = 0; i < mPlanets.length; i++) {
            float radius = (float)(Math.random() * 2);
        int posNeg = (Math.random() < 0.5) ? -1 : 1;
        int orbitRadius = 15 + (10 * (index+1));
        float xPos = posNeg * (int)(Math.random() * orbitRadius);
        posNeg = (Math.random() < 0.5) ? -1 : 1;
        float zPos =  posNeg * (float)Math.sqrt(orbitRadius * orbitRadius - xPos * xPos);
        
        
            mPlanets[i] = new Planet(new Vector3f(xPos, 0.0f, zPos), "Planet " + Integer.toString(index), assetManager, radius, ColorRGBA.randomColor());
        }
    }
    * /*
    * 
    */
    
    public static Planet[] getPlanets(){
        return mPlanets; 
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

