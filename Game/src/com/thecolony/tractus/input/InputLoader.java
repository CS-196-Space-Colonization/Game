/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thecolony.tractus.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 *
 * @author Joe Pagliuco
 */
public class InputLoader
{
    private static String[] mouseActionListenerMappings;
    private static String[] keyboardActionListenerMappings;
    private static String[] keyboardAnalogListenerMappings;
    
    public static void loadInputMappings(InputManager inputManager)
    {
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        mouseActionListenerMappings = new String[] 
        {
            "Right Click", "Left Click"
        };

        inputManager.addMapping("Shift", new KeyTrigger(KeyInput.KEY_LSHIFT), new KeyTrigger(KeyInput.KEY_RSHIFT));
        inputManager.addMapping("Move", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("Box Select", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("Attack", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Compress", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("Decompress", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_BACK));
        inputManager.addMapping("More Ships", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addMapping("Scroll Up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Scroll Down", new KeyTrigger(KeyInput.KEY_DOWN));

        keyboardActionListenerMappings = new String[]
        {
            "Shift", "Move", "Rotate", "Box Select",
            "Attack", "Pause", "Exit", "Scroll Up",
            "Scroll Down", "More Ships"
        };
        
        keyboardAnalogListenerMappings = new String[]
        {
            "Compress", "Decompress"
        };
    }
    
    public static String[] getMouseActionMappings()
    {
        return mouseActionListenerMappings;
    }
    
    public static String[] getKeyboardActionMappings()
    {
        return keyboardActionListenerMappings;
    }
    
    public static String[] getKeyboardAnalogMappings()
    {
        return keyboardAnalogListenerMappings;
    }
}