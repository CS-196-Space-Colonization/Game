package com.thecolony.tractus;

import com.jme3.system.AppSettings;
import com.thecolony.tractus.graphics.game.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Main menu for the game. That we will use forever. FOREVER.
 * @author Arturo
 */
public class MainMenu extends JFrame
{
    private JPanel window;
    private JButton singlePlayer, multiplayer, quit;
    private Rectangle rSinglePlayer, rMultiplayer, rquit;
    private JLabel title;
    
    private int width = 400;
    private int height = 600;
    private int buttonWidth = 200;
    private int buttonHeight = 100;
    
    private int titleWidth = buttonWidth - 0;
    
    private Font buttonFont = new Font("Comic Sans MS", 0, 25);
    private Font titleFont = new Font("Comic Sans MS", 0, 50);
    
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
        setUndecorated(true);
        setTitle("Tractus - Main Menu");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(window);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        window.setLayout(null);
        window.setBackground(Color.yellow);
        
        addButtons();
        addTitleLabel();
        repaint();
    }
    
    private void addButtons()
    {
        singlePlayer = new JButton("Single Player");
        rSinglePlayer = new Rectangle((width / 2) - (buttonWidth / 2), 200, buttonWidth, buttonHeight);
        singlePlayer.setBounds(rSinglePlayer);
        singlePlayer.setFont(buttonFont);
        window.add(singlePlayer);
        
        multiplayer = new JButton("Multiplayer");
        rMultiplayer = new Rectangle((width / 2) - (buttonWidth / 2), 300, buttonWidth, buttonHeight);
        multiplayer.setBounds(rMultiplayer);
        multiplayer.setFont(buttonFont);
        window.add(multiplayer);
        
        quit = new JButton("Quit");
        rquit = new Rectangle((width / 2) - (buttonWidth / 2), 400, buttonWidth, buttonHeight);
        quit.setFont(buttonFont);
        quit.setBounds(rquit);
        window.add(quit);
        
        singlePlayer.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      boolean fullscreen = false;
	      int input = JOptionPane.showConfirmDialog(null, "Full Screen Mode?");
	      if (input == JOptionPane.YES_OPTION)
		fullscreen = true;
	      else if (input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION)
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
	      } catch (Exception ex) { settings.setFullscreen(false); }

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
	      dispose();
	  }
        });
        
        multiplayer.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      new MultiplayerMenu();
	      dispose();
	  }
        });
        
        quit.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      System.exit(0);
	  }
        });
    } 
    
    private void addTitleLabel()
    {
        title = new JLabel("Tractus");
        title.setBounds((width / 2) - (titleWidth / 2), 0, titleWidth, buttonHeight);
        title.setForeground(Color.red);
        title.setFont(titleFont);
        window.add(title);
    }
}
