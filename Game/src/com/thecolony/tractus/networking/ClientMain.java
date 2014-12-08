/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Network;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.system.SystemListener;
import com.thecolony.tractus.game.Game;
import com.thecolony.tractus.graphics.GUI.OptionWindow;
import org.lwjgl.Sys;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

public class ClientMain extends SimpleApplication implements ClientStateListener
{

    private Client myClient;

    public ClientMain()
    {
        //network stuff
        start(JmeContext.Type.Headless);
        //graphics stuff
        boolean fullscreen = OptionWindow.fullscreen;
        Game game = new Game();

        game.setShowSettings(false);
        AppSettings settings = new AppSettings(true);

        settings.setTitle("Tractus");
        settings.setFrameRate(60);
        settings.setVSync(true);
        settings.setFrequency(60);

        try
        {
	  settings.setFullscreen(fullscreen);
        } catch (Exception e)
        {
	  settings.setFullscreen(false);
        }

        if (fullscreen)
        {
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  settings.setResolution(screenSize.width, screenSize.height);
        } else
        {
	  settings.setResolution(OptionWindow.resWidth, OptionWindow.resHeight);
        }

        game.addClient(this);
        game.setSettings(settings);
        game.start();
        
    }

    @Override
    public void simpleInitApp()
    {

        try
        {
            System.out.print("f");
	  myClient = Network.connectToServer(Globals.NAME, 
                  Globals.VERSION, 
                  Globals.DEFAULT_SERVER, 
                  Globals.DEFAULT_PORT);
            System.out.print("i");
	  myClient.start();
            System.out.print("s");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("Failed to connect to server in init");
        }
            System.out.print("H");
        Globals.registerClasses();
            System.out.print("Y");
        myClient.addMessageListener(new ClientListener(this), UpdateClientMessage.class);
            System.out.print("ne");
        myClient.addClientStateListener(this);
            System.out.print("ss");
    }

    @Override
    public void destroy()
    {
        System.exit(0);
    }

    /**
     * Specify what happens when this client connects to server
     */
    public void clientConnected(Client client)
    {
        /* example for client-server communication that changes the scene graph */
    }

    /**
     * Specify what happens when this client disconnects from server
     */
    public void clientDisconnected(Client client, ClientStateListener.DisconnectInfo info)
    {
    }
}
