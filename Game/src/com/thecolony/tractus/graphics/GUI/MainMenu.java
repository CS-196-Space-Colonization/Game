package com.thecolony.tractus.graphics.GUI;

import com.jme3.system.AppSettings;
import com.thecolony.tractus.game.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    public static ArrayList<Color> GUIColors;
    static {
        GUIColors = new ArrayList<Color>();
        GUIColors.add(Color.yellow);
        GUIColors.add(Color.red);
        GUIColors.add(Color.cyan);
        GUIColors.add(new Color(0xff, 0x4f, 0x00));//international orange
        GUIColors.add(new Color(0xcc, 0x88, 0x99));//puce
        GUIColors.add(new Color(127, 51, 0));//brown?
        GUIColors.add(new Color(224,20,147));//deep pink
        GUIColors.add(new Color(57,255,20));//neon green
    }
    
    private JPanel window;
    private JButton singlePlayer, multiplayer, options, quit;
    private Rectangle rSinglePlayer, rMultiplayer, rOptions, rquit;
    private JLabel title;
    
    private final int WIDTH = 400;
    private final int HEIGHT = 600;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 100;
    
    private final int TITLE_WIDTH = BUTTON_WIDTH;
    
    private final Font BUTTON_FONT = new Font("Comic Sans MS", 0, 25);
    private final Font TITLE_FONT = new Font("Comic Sans MS", 0, 50);
    
    private final Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR;
    
    public static void startGame(Game game)
    {
        
    }
    
    public MainMenu()
    {
        window = new JPanel();
        BACKGROUND_COLOR = MainMenu.GUIColors.remove((int)(Math.random() * MainMenu.GUIColors.size()));
        TEXT_COLOR = MainMenu.GUIColors.remove((int)(Math.random() * MainMenu.GUIColors.size()));
        MainMenu.GUIColors.add(BACKGROUND_COLOR);
        MainMenu.GUIColors.add(TEXT_COLOR);
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
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(window);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        window.setLayout(null);
        window.setBackground(BACKGROUND_COLOR);
        
        addButtons();
        addTitleLabel();
        repaint();
    }
    
    private void addButtons()
    {
        singlePlayer = new JButton("Single Player");
        rSinglePlayer = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        singlePlayer.setBounds(rSinglePlayer);
        singlePlayer.setFont(BUTTON_FONT);
        window.add(singlePlayer);
        
        multiplayer = new JButton("Multiplayer");
        rMultiplayer = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 275, BUTTON_WIDTH, BUTTON_HEIGHT);
        multiplayer.setBounds(rMultiplayer);
        multiplayer.setFont(BUTTON_FONT);
        window.add(multiplayer);
        
        options = new JButton("Options");
        rOptions = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 375, BUTTON_WIDTH, BUTTON_HEIGHT);
        options.setBounds(rOptions);
        options.setFont(BUTTON_FONT);
        window.add(options);
        
        quit = new JButton("Quit");
        rquit = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 475, BUTTON_WIDTH, BUTTON_HEIGHT);
        quit.setFont(BUTTON_FONT);
        quit.setBounds(rquit);
        window.add(quit);
        
        singlePlayer.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      boolean fullscreen = OptionWindow.fullscreen;
//	      int input = JOptionPane.showConfirmDialog(null, "Full Screen Mode?");
//	      if (input == JOptionPane.YES_OPTION)
//		fullscreen = true;
//	      else if (input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION)
//		System.exit(0);            

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
		settings.setResolution(OptionWindow.resWidth, OptionWindow.resHeight);
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
        
        options.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      new OptionWindow();
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
        title.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 0, TITLE_WIDTH, BUTTON_HEIGHT);
        title.setForeground(TEXT_COLOR);
        title.setFont(TITLE_FONT);
        window.add(title);
    }
}
