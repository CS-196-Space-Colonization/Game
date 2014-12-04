/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author Arturo
 */
public class MainMenu extends JFrame
{
    private JPanel window;
    public MainMenu()
    {
        window = new JPanel();
        try
        {
	  UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e)
        {
	  System.out.println("Failed to set look and feel");
        }
        setTitle("Tractus - Main Menu");
        setSize(400, 720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(window);
    }
    
}
