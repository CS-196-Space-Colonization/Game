/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus;

import com.thecolony.tractus.networking.ClientMain;
import com.thecolony.tractus.networking.ServerMain;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
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
    private int width = 600;
    private int height = 450;
    private int buttonWidth = 275;
    private int buttonHeight = 100;
    private int titleWidth = buttonWidth;
    private int lableWidth = titleWidth - 100;
    private Font buttonFont = new Font("Comic Sans MS", 0, 25);
    private Font titleFont = new Font("Comic Sans MS", 0, 50);

    public MultiplayerMenu()
    {
        window = new JPanel();
        try
        {
	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e)
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
        window.setBackground(Color.cyan);

        addLabels();
        addButtons();
        repaint();
    }

    private void addButtons()
    {
        startServer = new JButton("Start Server");
        rStartServer = new Rectangle((width / 2) - (buttonWidth / 2), 100, buttonWidth, buttonHeight);
        startServer.setBounds(rStartServer);
        startServer.setFont(buttonFont);
        window.add(startServer);

        connectToServer = new JButton("Connect to Server");
        rConnectToServer = new Rectangle((width / 2) - (buttonWidth / 2), 200, buttonWidth, buttonHeight);
        connectToServer.setBounds(rConnectToServer);
        connectToServer.setFont(buttonFont);
        window.add(connectToServer);

        back = new JButton("Back");
        rBack = new Rectangle((width / 2) - (buttonWidth / 2), 300, buttonWidth, buttonHeight);
        back.setFont(buttonFont);
        back.setBounds(rBack);
        window.add(back);

        startServer.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      new ServerMain();
	      dispose();
	  }
        });

        connectToServer.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      new ClientMain();
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
        title.setBounds((width / 2) - (titleWidth / 2), 0, titleWidth, buttonHeight);
        title.setForeground(Color.orange);
        title.setFont(titleFont);
        window.add(title);
    }
}
