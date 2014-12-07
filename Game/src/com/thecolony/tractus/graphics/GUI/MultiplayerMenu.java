/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.graphics.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
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
public class MultiplayerMenu extends JFrame
{

    private JPanel window;
    private JButton startServer, connectToServer, back;
    private Rectangle rStartServer, rConnectToServer, rBack;
    private JLabel title;
    private final int WIDTH = 600;
    private final int HEIGHT = 450;
    private final int BUTTON_WIDTH = 275;
    private final int BUTTON_HEIGHT = 100;
    private final int TITLE_WIDTH = BUTTON_WIDTH;
    private final int LABLE_WIDTH = TITLE_WIDTH - 100;
    private final Font BUTTON_FONT = new Font("Comic Sans MS", 0, 25);
    private final Font TITLE_FONT = new Font("Comic Sans MS", 0, 50);
    private final Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR;

    public MultiplayerMenu()
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
        setTitle("Multiplayer Menu");
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
        startServer = new JButton("Start Server");
        rStartServer = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        startServer.setBounds(rStartServer);
        startServer.setFont(BUTTON_FONT);
        window.add(startServer);

        connectToServer = new JButton("Connect to Server");
        rConnectToServer = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 200, BUTTON_WIDTH, BUTTON_HEIGHT);
        connectToServer.setBounds(rConnectToServer);
        connectToServer.setFont(BUTTON_FONT);
        window.add(connectToServer);

        back = new JButton("Back");
        rBack = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 300, BUTTON_WIDTH, BUTTON_HEIGHT);
        back.setFont(BUTTON_FONT);
        back.setBounds(rBack);
        window.add(back);

        startServer.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      new ServerInfoWindow(ServerInfoWindow.SERVER);
	      dispose();
	  }
        });

        connectToServer.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      new ServerInfoWindow(ServerInfoWindow.CLIENT);
	      dispose();
	  }
        });

        back.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      new MainMenu();
	      dispose();
	  }
        });
    }

    private void addLabels()
    {
        title = new JLabel("Mutiplayer");
        title.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 0, TITLE_WIDTH, BUTTON_HEIGHT);
        title.setForeground(TEXT_COLOR);
        title.setFont(TITLE_FONT);
        window.add(title);
    }
}
