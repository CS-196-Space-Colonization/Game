/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.graphics.GUI;

import com.thecolony.tractus.audio.MenuAudio;
import com.thecolony.tractus.networking.ClientMain;
import com.thecolony.tractus.networking.Globals;
import com.thecolony.tractus.networking.ServerMain;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Arturo
 */
public class ServerInfoWindow extends JFrame
{

    public static final int SERVER = 0;
    public static final int CLIENT = 1;
    private JPanel window;
    private JButton ok, back;
    private Rectangle rOk, rBack;
    private JLabel title;
    private final int WIDTH = 500;
    private final int HEIGHT = 300;
    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 40;
    private final int TITLE_WIDTH = 175;
    private final Font BUTTON_FONT = new Font("Comic Sans MS", 0, 20);
    private final Font TITLE_FONT = new Font("Comic Sans MS", 0, 30);
    private final Font TEXT_FONT = new Font("Comic Sans MS", 0, 20);
    private String name, version, ip, port;
    private JTextField tName, tVersion, tIp, tPort;
    private final int TYPE;
    private final Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR;

    public ServerInfoWindow(int t)
    {
        TYPE = t;
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
        setTitle("Server Info");
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
        addTextFields();
        repaint();
    }

    private void addButtons()
    {
        ok = new JButton("OK");
        rOk = new Rectangle(25, HEIGHT - 65, BUTTON_WIDTH, BUTTON_HEIGHT);
        ok.setBounds(rOk);
        ok.setFont(BUTTON_FONT);
        window.add(ok);

        back = new JButton("Back");
        rBack = new Rectangle((WIDTH) - (BUTTON_WIDTH) - 25, HEIGHT - 65, BUTTON_WIDTH, BUTTON_HEIGHT);
        back.setFont(BUTTON_FONT);
        back.setBounds(rBack);
        window.add(back);

        ok.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      MenuAudio.playSound(MenuAudio.YAY);
	      name = tName.getText();
	      if (TYPE == CLIENT)
	      {
		version = tVersion.getText();
		ip = tIp.getText();
		port = tPort.getText();
		Globals.setInfo(name, version, ip, port);
	      }
	      Globals.setName(name);
	      if (TYPE == CLIENT)
	      {
		new ClientMain();
	      } else
	      {
		new ServerMain();
	      }
	      dispose();
	  }
        });

        back.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      MenuAudio.playSound(MenuAudio.CLICK);
	      new MultiplayerMenu();
	      dispose();
	  }
        });
    }

    private void addLabels()
    {
        title = new JLabel("Server info?");
        title.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 0, TITLE_WIDTH, BUTTON_HEIGHT);
        title.setForeground(TEXT_COLOR);
        title.setFont(TITLE_FONT);
        window.add(title);
    }

    private void addTextFields()
    {
        tName = new JTextField("Server Name");
        tName.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 50, TITLE_WIDTH, BUTTON_HEIGHT);
        tName.setFont(TEXT_FONT);
        window.add(tName);
        if (TYPE == CLIENT)
        {
	  tVersion = new JTextField("Version");
	  tVersion.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 100, TITLE_WIDTH, BUTTON_HEIGHT);
	  tVersion.setFont(TEXT_FONT);
	  window.add(tVersion);

	  tIp = new JTextField("IP Address");
	  tIp.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 150, TITLE_WIDTH, BUTTON_HEIGHT);
	  tIp.setFont(TEXT_FONT);
	  window.add(tIp);

	  tPort = new JTextField("Port Number");
	  tPort.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 200, TITLE_WIDTH, BUTTON_HEIGHT);
	  tPort.setFont(TEXT_FONT);
	  window.add(tPort);
        }
    }
}