package com.thecolony.tractus.audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 * A class that manages all sound.
 * @author Joe Pagliuco
 */
public abstract class AudioManager
{
    private static AudioNode BATTLE_LASER_1;
    private static AudioNode BATTLE_LASER_2;
    private static AudioNode BATTLE_LASER_3;
    private static AudioNode BATTLE_LASER_4; 
    
    private static AudioNode CREATE_CREATE;
    private static AudioNode CREATE_DC_WIELDING;
    private static AudioNode CREATE_GEAR_BURSTS;
    private static AudioNode CREATE_HAMMER;
    
    public static void loadAudio(AssetManager assetManager, Node node)
    {        
        BATTLE_LASER_1 = new AudioNode(assetManager, "Sounds/Battle/laser1.ogg", false);
        BATTLE_LASER_1.setLooping(false);
        BATTLE_LASER_1.setPositional(false);
        BATTLE_LASER_2 = new AudioNode(assetManager, "Sounds/Battle/laser2.ogg", false);
        BATTLE_LASER_2.setLooping(false);
        BATTLE_LASER_2.setPositional(false);
        BATTLE_LASER_3 = new AudioNode(assetManager, "Sounds/Battle/laser3.ogg", false);
        BATTLE_LASER_3.setLooping(false);
        BATTLE_LASER_3.setPositional(false);
        BATTLE_LASER_4 = new AudioNode(assetManager, "Sounds/Battle/laser4.ogg", false);
        BATTLE_LASER_4.setLooping(false);
        BATTLE_LASER_4.setPositional(false);
        
        CREATE_CREATE = new AudioNode(assetManager, "Sounds/Unit_Creation/create.ogg", false);
        CREATE_DC_WIELDING = new AudioNode(assetManager, "Sounds/Unit_Creation/dc_wielding.ogg", false);
        CREATE_GEAR_BURSTS = new AudioNode(assetManager, "Sounds/Unit_Creation/gear_bursts.ogg", false);
        CREATE_HAMMER = new AudioNode(assetManager, "Sounds/Unit_Creation/hammer.ogg", false);
    }
    
    public void setVolume(float volumeLevel)
    {        
        BATTLE_LASER_1.setVolume(volumeLevel);
        BATTLE_LASER_2.setVolume(volumeLevel);
        BATTLE_LASER_3.setVolume(volumeLevel);
        BATTLE_LASER_4.setVolume(volumeLevel);
        
        CREATE_CREATE.setVolume(volumeLevel);
        CREATE_DC_WIELDING.setVolume(volumeLevel);
        CREATE_GEAR_BURSTS.setVolume(volumeLevel);
        CREATE_HAMMER.setVolume(volumeLevel);
    }
    
    public AudioNode getBattleLaser(int index)
    {
        switch (index)
        {
            case 1:
                return BATTLE_LASER_1.clone();
            case 2:
                return BATTLE_LASER_2.clone();
            case 3:
                return BATTLE_LASER_3.clone();
            case 4:
                return BATTLE_LASER_4.clone();
            default:
                return null;
        }
    }
    
    public AudioNode getCreate()
    {
        return CREATE_CREATE.clone();
    }
    public AudioNode getDCWielding()
    {
        return CREATE_DC_WIELDING.clone();
    }
    public AudioNode getGearBursts()
    {
        return CREATE_GEAR_BURSTS.clone();
    }
    public AudioNode getHammer()
    {
        return CREATE_HAMMER.clone();
    }
}