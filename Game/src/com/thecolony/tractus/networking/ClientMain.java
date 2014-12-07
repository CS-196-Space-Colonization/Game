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
import com.thecolony.tractus.game.Game;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JOptionPane;

public class ClientMain extends SimpleApplication implements ClientStateListener
{

    private Client myClient;
    private Game game;

    public ClientMain()
    {
        //network stuff
        start(JmeContext.Type.Headless);
        //graphics stuff
        boolean fullscreen = false;
        int input = JOptionPane.showConfirmDialog(null, "Full Screen Mode?");
        if (input == JOptionPane.YES_OPTION)
        {
	  fullscreen = true;
        } else if (input == JOptionPane.CANCEL_OPTION)
        {
	  System.exit(0);
        }
        game = new Game();

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
	  settings.setResolution(800, 600);
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
	  myClient = Network.connectToServer(Globals.NAME, Globals.VERSION, Globals.DEFAULT_SERVER, Globals.DEFAULT_PORT);
	  myClient.start();
        } catch (IOException ex)
        {
	  System.out.println("Failed to connect to server");
        }

        Globals.registerClasses();

        myClient.addMessageListener(new ClientListener(this), UpdateClientMessage.class);
        myClient.addClientStateListener(this);
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
