/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.graphics.GUI;

import com.jme3.system.AppSettings;
import com.thecolony.tractus.audio.MenuAudio;
import com.thecolony.tractus.game.Game;
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
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author Arturo
 */
public class SinglePlayerOptions extends JFrame
{

    private JPanel window;
    private JButton newGame, load, back;
    private Rectangle rStartServer, rConnectToServer, rBack;
    private JLabel title;
    private final int WIDTH = 400;
    private final int HEIGHT = 600;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 100;
    private final int TITLE_WIDTH = BUTTON_WIDTH;
    private final int LABLE_WIDTH = TITLE_WIDTH - 100;
    private final Font BUTTON_FONT = new Font("Comic Sans MS", 0, 20);
    private final Font TITLE_FONT = new Font("Comic Sans MS", 0, 30);
    private final Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR;

    public SinglePlayerOptions()
    {
        window = new JPanel();
        BACKGROUND_COLOR = MainMenu.GUIColors.remove((int)(Math.random() * MainMenu.GUIColors.size()));
        TEXT_COLOR = MainMenu.GUIColors.remove((int)(Math.random() * MainMenu.GUIColors.size()));
        MainMenu.GUIColors.add(BACKGROUND_COLOR);
        MainMenu.GUIColors.add(TEXT_COLOR);
        try
        {
	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e)
        {
	  System.out.println("Failed to set look and feel");
        }
        setUndecorated(true);
        setTitle("Single Player Menu");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(window);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        window.setLayout(null);
        window.setBackground(BACKGROUND_COLOR);

        addLabels();
        addButtons();
        repaint();
    }

    private void addButtons()
    {
        newGame = new JButton("New Game");
        rStartServer = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        newGame.setBounds(rStartServer);
        newGame.setFont(BUTTON_FONT);
        window.add(newGame);

        load = new JButton("Load");
        rConnectToServer = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 350, BUTTON_WIDTH, BUTTON_HEIGHT);
        load.setBounds(rConnectToServer);
        load.setFont(BUTTON_FONT);
        window.add(load);

        back = new JButton("Back");
        rBack = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 450, BUTTON_WIDTH, BUTTON_HEIGHT);
        back.setFont(BUTTON_FONT);
        back.setBounds(rBack);
        window.add(back);

        newGame.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      MenuAudio.playSound(MenuAudio.YAY);
	      boolean fullscreen = OptionWindow.fullscreen;          

	      Game game = new Game(false);

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

        load.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      MenuAudio.playSound(MenuAudio.YAY);
	      boolean fullscreen = OptionWindow.fullscreen;          

	      Game game = new Game(true);

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

        back.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      MenuAudio.playSound(MenuAudio.CLICK);
	      new MainMenu();
	      dispose();
	  }
        });
    }

    private void addLabels()
    {
        title = new JLabel("Single Player");
        title.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 0, TITLE_WIDTH, BUTTON_HEIGHT);
        title.setForeground(TEXT_COLOR);
        title.setFont(TITLE_FONT);
        window.add(title);
    }
}
