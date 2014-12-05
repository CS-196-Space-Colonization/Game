package com.thecolony.tractus;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
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
    private JButton singlePlayer, multiplayer, quit;
    private Rectangle rSinglePlayer, rMultiplayer, rquit;
    
    private int width = 400;
    private int height = 600;
    private int buttonWidth = 200;
    private int buttonHeight = 100;
    
    public MainMenu()
    {
        window = new JPanel();
        try
        {
	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
	  System.out.println("Failed to set look and feel");
        }
        setTitle("Tractus - Main Menu");
        setSize(width, height);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        window.setLayout(null);
        add(window);
        addButtons();
        repaint();
    }
    
    private void addButtons()
    {
        singlePlayer = new JButton("Single Player");
        rSinglePlayer = new Rectangle((width / 2) - (buttonWidth / 2), 200, buttonWidth, buttonHeight);
        singlePlayer.setBounds(rSinglePlayer);
        window.add(singlePlayer);
        
        multiplayer = new JButton("Multiplayer");
        rMultiplayer = new Rectangle((width / 2) - (buttonWidth / 2), 300, buttonWidth, buttonHeight);
        multiplayer.setBounds(rMultiplayer);
        window.add(multiplayer);
        
        quit = new JButton("Quit");
        rquit = new Rectangle((width / 2) - (buttonWidth / 2), 400, buttonWidth, buttonHeight);
        quit.setBounds(rquit);
        window.add(quit);
        
        quit.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      System.exit(0);
	  }
        });
    } 
}
