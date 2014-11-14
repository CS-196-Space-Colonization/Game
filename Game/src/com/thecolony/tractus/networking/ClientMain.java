/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacecolonization.networking;

import com.jme3.app.SimpleApplication;

import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;

import com.jme3.system.JmeContext;
import com.thecolony.tractus.player.Player;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * This package contains an example that shows how you enqueue changes to the
 * scene graph correctly from the network thread -- see ClientListener.
 */
public class ClientMain extends SimpleApplication implements ClientStateListener {

    private Client myClient;
    private String userMessage; 

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("").setLevel(Level.SEVERE);
        ClientMain app = new ClientMain();
        app.start(JmeContext.Type.Display);
    }

    @Override
    public void simpleInitApp() {
        try {
            myClient = Network.connectToServer("My Cool Game", 1, "localhost", 6143);
            myClient.start();
        } catch (IOException ex) {
        }
        /*
        Serializer.registerClass(CubeMessage.class);
        myClient.addMessageListener(new ClientListener(this, myClient), CubeMessage.class);
        myClient.addClientStateListener(this);
        */
        
        Serializer.registerClass(GreetingMessage.class);
        Serializer.registerClass(InetAddressMessage.class);
        Serializer.registerClass(InetAddress.class, new InetAddressSerializer());
        Serializer.registerClass(TextMessage.class);
        Serializer.registerClass(Player.class);

        myClient.addMessageListener(new ClientListener(), GreetingMessage.class);
        myClient.addMessageListener(new ClientListener(), InetAddressMessage.class);
        myClient.addMessageListener(new ClientListener(), TextMessage.class);
        myClient.addMessageListener(new ClientListener(), Player.class);
        
        myClient.addClientStateListener(this);

        // example 1 -- client-server communication
 
        
        Message m = new GreetingMessage("Hi server, do you hear me?");
        myClient.send(m);
        
        

        // example 2 -- transmitting data with a custom serializer
        try {
            Message message = new InetAddressMessage(
                    InetAddress.getByName("jmonkeyengine.org"));
            myClient.send(message);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        
        Scanner stdin = new Scanner( System.in );
        
        while(true){
            userMessage = stdin.nextLine();
            TextMessage um = new TextMessage(userMessage);
            myClient.send(um);
        }



        // attachCube("One Cube");
    }

    /* Add some demo content */
    

    @Override
    public void destroy() {
        try {
            myClient.close();
        } catch (Exception ex) {
        }
        super.destroy();
    }

    /**
     * Specify what happens when this client connects to server
     */
    public void clientConnected(Client client) {
        /* example for client-server communication that changes the scene graph */
        
    }

    /**
     * Specify what happens when this client disconnects from server
     */
    public void clientDisconnected(Client client, DisconnectInfo info) {
    }
}