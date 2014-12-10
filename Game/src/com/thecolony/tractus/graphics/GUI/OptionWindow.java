package com.thecolony.tractus.graphics.GUI;

import com.thecolony.tractus.audio.MenuAudio;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Change resolution, fullscreen, audio level?
 *
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
    private final Font SLIDER_FONT = new Font("Comic Sans MS", 0, 10);
    private final Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR;
    private JPanel window;
    private JLabel title;
    private JButton save, back;
    private Choice resolution;
    private JLabel lblResolution;
    private JLabel lblVolume;
    private JLabel lblFullscreen;
    private JCheckBox checkFull;
    private JSlider sliderVolume;
    //Defualt values
    public static int volume = 100;
    public static int resWidth = 800;
    public static int resHeight = 600;
    public static boolean fullscreen = false;
    private final int DISPLACEMENT = 10;

    public OptionWindow()
    {
        window = new JPanel();
        BACKGROUND_COLOR = MainMenu.GUIColors.remove((int) (Math.random() * MainMenu.GUIColors.size()));
        TEXT_COLOR = MainMenu.GUIColors.remove((int) (Math.random() * MainMenu.GUIColors.size()));
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
        setTitle("Options");
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
        addChoice();
        addCheckBox();
        addSlider();
        repaint();
    }

    private void addSlider()
    {
        sliderVolume = new JSlider(0, 100, 100);
        sliderVolume.setBounds((WIDTH / 2) - 150, 125, 200, 75);
        sliderVolume.setMajorTickSpacing(10);
        sliderVolume.setMinorTickSpacing(1);
        sliderVolume.setPaintTicks(true);
        sliderVolume.setPaintLabels(true);
        sliderVolume.setBackground(BACKGROUND_COLOR);
        sliderVolume.setFont(SLIDER_FONT);
        window.add(sliderVolume);

        sliderVolume.addChangeListener(new ChangeListener()
        {
	  public void stateChanged(ChangeEvent e)
	  {
	      volume = sliderVolume.getValue();
	      lblVolume.setText("Volume:" + volume);
	      System.out.println(volume);
	      repaint();
	  }
        });
    }

    private void addCheckBox()
    {
        checkFull = new JCheckBox();
        checkFull.setBounds(WIDTH - 150, 85, 25, 25);
        checkFull.setBackground(BACKGROUND_COLOR);
        window.add(checkFull);
        
        checkFull.addActionListener(new ActionListener() {

	  public void actionPerformed(ActionEvent e)
	  {
	      if(checkFull.isSelected())
		resolution.setEnabled(false);
	      else
		resolution.setEnabled(true);
	  }
        });
    }

    private void addChoice()
    {
        resolution = new Choice();
        resolution.setBounds(new Rectangle((WIDTH / 2) - 150, 80, 150, 25));
        resolution.setFont(TEXT_FONT);
        resolution.add("640 x 480");
        resolution.add("800 x 600");
        resolution.add("1024 x 768");
        resolution.select(1);
        window.add(resolution);
    }

    private void addButtons()
    {
        save = new JButton("Save");
        save.setBounds(new Rectangle(25, HEIGHT - 65, BUTTON_WIDTH, BUTTON_HEIGHT));
        save.setFont(BUTTON_FONT);
        window.add(save);

        back = new JButton("Back");
        back.setFont(BUTTON_FONT);
        back.setBounds(new Rectangle((WIDTH) - (BUTTON_WIDTH) - 25, HEIGHT - 65, BUTTON_WIDTH, BUTTON_HEIGHT));
        window.add(back);

        save.addActionListener(new ActionListener()
        {
	  public void actionPerformed(ActionEvent e)
	  {
	      MenuAudio.playSound(MenuAudio.CLICK);
	      if(resolution.getSelectedIndex() == 0)
	      {
		resWidth = 640;
		resHeight = 480;
	      }
	      else if(resolution.getSelectedIndex() == 1)
	      {
		resWidth = 800;
		resHeight = 600;
	      }
	      else if(resolution.getSelectedIndex() == 2)
	      {
		resWidth = 1024;
		resHeight = 768;
	      }
	      volume = sliderVolume.getValue();
	      fullscreen = checkFull.isSelected();
	      new MainMenu();
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
        title = new JLabel("Options");
        title.setBounds((WIDTH / 2) - (TITLE_WIDTH / 2), 0, TITLE_WIDTH, BUTTON_HEIGHT);
        title.setForeground(TEXT_COLOR);
        title.setFont(TITLE_FONT);
        window.add(title);

        lblResolution = new JLabel("Resolution:");
        lblResolution.setBounds(DISPLACEMENT, 85, 115, 25);
        lblResolution.setFont(TEXT_FONT);
        window.add(lblResolution);

        lblFullscreen = new JLabel("Fullscreen:");
        lblFullscreen.setBounds((WIDTH / 2) + DISPLACEMENT, 85, 115, 25);
        lblFullscreen.setFont(TEXT_FONT);
        window.add(lblFullscreen);

        lblVolume = new JLabel("Volume :" + volume);
        lblVolume.setBounds(DISPLACEMENT, 150, 115, 25);
        lblVolume.setFont(TEXT_FONT);
        window.add(lblVolume);
    }

    public static void main(String[] args)
    {
        new OptionWindow();
    }
}
