/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.AppSettings;
import com.thecolony.tractus.Map;
import com.thecolony.tractus.graphics.threedmovement.game.Game;
import com.thecolony.tractus.player.Player;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JOptionPane;


public class ClientMain extends SimpleApplication implements ClientStateListener {

    public static int M_WIDTH, M_HEIGHT;
    private Player mPlayer;
    private Client myClient;
    private Map mMap;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
// START INITIALIZATION METHODS /////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {

        boolean fullscreen = false;
        int input = JOptionPane.showConfirmDialog(null, "Full Screen Mode?");
        if (input == JOptionPane.YES_OPTION)
            fullscreen = true;
        else if (input == JOptionPane.CANCEL_OPTION)
            System.exit(0);
            
        Game game = new Game();
        
        game.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        
        settings.setTitle("Tractus");
        settings.setFrameRate(60);
        settings.setVSync(true);
        settings.setFrequency(60);
        
        try {
            settings.setFullscreen(fullscreen);
        } catch (Exception e) { settings.setFullscreen(false); }
        
        if (fullscreen)
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            settings.setResolution(screenSize.width, screenSize.height);
        }
        else
        {
            settings.setResolution(800, 600);
        }
        
        game.setSettings(settings);
        game.start();
    }
    
    

    @Override
    public void simpleInitApp() {

        try {
            myClient = Network.connectToServer(Globals.NAME, Globals.VERSION, Globals.DEFAULT_SERVER, Globals.DEFAULT_PORT);
            myClient.start();
	  System.out.println("client started");
        } catch (IOException ex) {
	  System.out.println("Failed to connect to server");
        }

        M_WIDTH = settings.getWidth();
        M_HEIGHT = settings.getHeight();

        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_HIDE_STATS);
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_MEMORY);
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_CAMERA_POS);
        setDisplayFps(true);
        setDisplayStatView(false);

        System.out.println("pre-registered classes");
        Serializer.registerClasses(UpdateClientMessage.class);
        System.out.println("registered classes");
        myClient.addMessageListener(new ClientListener(this), UpdateClientMessage.class);
        myClient.addClientStateListener(this);

        //Message m = new GreetingMessage("Hi server, do you hear me?");
        //myClient.send(m);
        
        mPlayer = new Player(mMap, myClient.getId());
    }
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
    public void clientDisconnected(Client client, ClientStateListener.DisconnectInfo info) {
    }
}
