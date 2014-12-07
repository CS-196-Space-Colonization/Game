
package com.thecolony.tractus.graphics.GUI;

import java.awt.Choice;
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
 * Change resolution, fullscreen, audio level?
 * @author Arturo
 */
public class OptionWindow extends JFrame
{
    private final int WIDTH = 550;
    private final int HEIGHT = 450;
    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 40;
    
    private final int TITLE_WIDTH = 125;
    
    private final Font BUTTON_FONT = new Font("Comic Sans MS", 0, 20);
    private final Font TITLE_FONT = new Font("Comic Sans MS", 0, 30);
    private final Font TEXT_FONT = new Font("Comic Sans MS", 0, 20);
    
    private final Color backgroundColor = new Color(224,20,147);//deep pink
    private final Color titleColor = new Color(57,255,20);//neon green
    
    private JPanel window;
    private JLabel title;
    private JButton back;
    private Choice resolution;
    
    private int masterVolume;
    private int resWidth;
    private int resHeight;
    private boolean fullscreen;
    
    public OptionWindow()
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
        setTitle("Options");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(window);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        window.setLayout(null);
        window.setBackground(backgroundColor);
        
        addLabels();
        addButtons();
        repaint();
    }
    
    private void addButtons()
    {
        back = new JButton("Back");
        back.setFont(BUTTON_FONT);
        back.setBounds(new Rectangle((WIDTH) - (BUTTON_WIDTH) - 25, HEIGHT - 65, BUTTON_WIDTH, BUTTON_HEIGHT));
        window.add(back);

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
        title = new JLabel("Options");
        title.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 0, TITLE_WIDTH, BUTTON_HEIGHT);
        title.setForeground(titleColor);
        title.setFont(TITLE_FONT);
        window.add(title);
    }
    
    public static void main(String[] args)
    {
        new OptionWindow();
    }
}


