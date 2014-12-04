package com.thecolony.tractus.graphics.threedmovement;

import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import com.thecolony.tractus.graphics.threedmovement.game.Game;

/**
 * @author Joe Pagliuco
 */
public class Main
{
    public static void main(String[] args)
    {
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
}