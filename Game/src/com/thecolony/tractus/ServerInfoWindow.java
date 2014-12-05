/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus;

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
    
    private int width = 500;
    private int height = 300;
    private int buttonWidth = 100;
    private int buttonHeight = 40;
    private int titleWidth = 175;
    
    private Font buttonFont = new Font("Comic Sans MS", 0, 20);
    private Font titleFont = new Font("Comic Sans MS", 0, 30);
    private Font textFont = new Font("Comic Sans MS", 0, 20);
    
    private String name, version, ip, port;
    private JTextField tName, tVersion, tIp, tPort;
    private int type;

    public ServerInfoWindow(int t)
    {
        type = t;
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
        window.setBackground(new Color(0xcc, 0x88, 0x99));

        addLabels();
        addButtons();
        addTextFields();
        repaint();
    }

    private void addButtons()
    {
        ok = new JButton("OK");
        rOk = new Rectangle(25, height - 65, buttonWidth, buttonHeight);
        ok.setBounds(rOk);
        ok.setFont(buttonFont);
        window.add(ok);

        back = new JButton("Back");
        rBack = new Rectangle((width) - (buttonWidth) - 25, height - 65, buttonWidth, buttonHeight);
        back.setFont(buttonFont);
        back.setBounds(rBack);
        window.add(back);

        ok.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      name = tName.getText();
	      version = tVersion.getText();
	      ip = tIp.getText();
	      port = tPort.getText();
	      Globals.setInfo(name, version, ip, port);
	      if (type == CLIENT)
	      {
		new ClientMain();
	      } 
	      else
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
	      new MultiplayerMenu();
	      dispose();
	  }
        });
    }

    private void addLabels()
    {
        title = new JLabel("Server info?");
        title.setBounds((width / 2) - (titleWidth / 2), 0, titleWidth, buttonHeight);
        title.setForeground(new Color(127, 51, 0));
        title.setFont(titleFont);
        window.add(title);
    }

    private void addTextFields()
    {
        tName = new JTextField("Server Name");
        tName.setBounds((width / 2) - (titleWidth / 2), 50, titleWidth, buttonHeight);
        tName.setFont(textFont);
        window.add(tName);

        tVersion = new JTextField("Version");
        tVersion.setBounds((width / 2) - (titleWidth / 2), 100, titleWidth, buttonHeight);
        tVersion.setFont(textFont);
        window.add(tVersion);

        tIp = new JTextField("IP Address");
        tIp.setBounds((width / 2) - (titleWidth / 2), 150, titleWidth, buttonHeight);
        tIp.setFont(textFont);
        window.add(tIp);

        tPort = new JTextField("Port Number");
        tPort.setBounds((width / 2) - (titleWidth / 2), 200, titleWidth, buttonHeight);
        tPort.setFont(textFont);
        window.add(tPort);
    }
}