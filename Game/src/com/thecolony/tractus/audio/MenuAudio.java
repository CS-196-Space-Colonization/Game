/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.audio;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Arturo
 */
public class MenuAudio
{

    public static final int YAY = 0;
    public static final int QUIT = 1;

    private static String getFileName(int file)
    {
        switch (file)
        {
	  case YAY:
	      return System.getProperty("user.dir") + "/assets/Sounds/Menu/yay.wav";
	  case QUIT:
	      return System.getProperty("user.dir") + "/assets/Sounds/Menu/quit.wav";
	  default:
	      return "";
        }
    }
    private static Clip clip;

    public static void playSound(int fileNum)
    {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try
        {
	  File file = new File(getFileName(fileNum));
	  if (file.exists())
	  {
	      AudioInputStream sound = AudioSystem.getAudioInputStream(file);
	      // load the sound into memory (a Clip)
	      clip = AudioSystem.getClip();
	      clip.open(sound);
	      clip.setFramePosition(0);  // Must always rewind!
	      clip.start();
	  } else
	  {
	      throw new RuntimeException("Sound: file not found: ");
	  }
        } catch (MalformedURLException e)
        {
	  e.printStackTrace();
	  throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e)
        {
	  e.printStackTrace();
	  throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        } catch (IOException e)
        {
	  e.printStackTrace();
	  throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e)
        {
	  e.printStackTrace();
	  throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }

        // play, stop, loop the sound clip
    }

    public void play()
    {
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        clip.stop();
    }
}
