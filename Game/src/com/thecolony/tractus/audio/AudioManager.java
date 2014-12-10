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
    
    private static AudioNode SHIP_DEATH;
    
    public static void loadAudio(AssetManager assetManager, Node node)
    {      
        SHIP_DEATH = new AudioNode(assetManager, "Sounds/Battle/laser2.ogg", false);
        SHIP_DEATH.setLooping(false);
        SHIP_DEATH.setPositional(false);
        node.attachChild(SHIP_DEATH);
        
        BATTLE_LASER_1 = new AudioNode(assetManager, "Sounds/Battle/laser1.ogg", false);
        BATTLE_LASER_1.setLooping(false);
        BATTLE_LASER_1.setPositional(false);
        node.attachChild(BATTLE_LASER_1);
        
        BATTLE_LASER_2 = new AudioNode(assetManager, "Sounds/Battle/laser2.ogg", false);
        BATTLE_LASER_2.setLooping(false);
        BATTLE_LASER_2.setPositional(false);
        node.attachChild(BATTLE_LASER_2);
        
        BATTLE_LASER_3 = new AudioNode(assetManager, "Sounds/Battle/laser3.ogg", false);
        BATTLE_LASER_3.setLooping(false);
        BATTLE_LASER_3.setPositional(false);
        node.attachChild(BATTLE_LASER_3);
        
        BATTLE_LASER_4 = new AudioNode(assetManager, "Sounds/Battle/laser4.ogg", false);
        BATTLE_LASER_4.setLooping(false);
        BATTLE_LASER_4.setPositional(false);
        node.attachChild(BATTLE_LASER_4);
        
        CREATE_CREATE = new AudioNode(assetManager, "Sounds/Unit_Creation/create.ogg", false);
        CREATE_CREATE.setLooping(false);
        CREATE_CREATE.setPositional(false);
        node.attachChild(CREATE_CREATE);
        
        CREATE_DC_WIELDING = new AudioNode(assetManager, "Sounds/Unit_Creation/dc_wielding.ogg", false);
        CREATE_DC_WIELDING.setLooping(false);
        CREATE_DC_WIELDING.setPositional(false);
        node.attachChild(CREATE_DC_WIELDING);
        
        CREATE_GEAR_BURSTS = new AudioNode(assetManager, "Sounds/Unit_Creation/gear_bursts.ogg", false);
        CREATE_GEAR_BURSTS.setLooping(false);
        CREATE_GEAR_BURSTS.setPositional(false);
        node.attachChild(CREATE_GEAR_BURSTS);
        
        CREATE_HAMMER = new AudioNode(assetManager, "Sounds/Unit_Creation/hammer.ogg", false);
        CREATE_HAMMER.setLooping(false);
        CREATE_HAMMER.setPositional(false);
        node.attachChild(CREATE_HAMMER);
    }
    
    public static void setVolume(float volumeLevel)
    {        
        BATTLE_LASER_1.setVolume(volumeLevel);
        BATTLE_LASER_2.setVolume(volumeLevel);
        BATTLE_LASER_3.setVolume(volumeLevel);
        BATTLE_LASER_4.setVolume(volumeLevel);
        
        CREATE_CREATE.setVolume(volumeLevel);
        CREATE_DC_WIELDING.setVolume(volumeLevel);
        CREATE_GEAR_BURSTS.setVolume(volumeLevel);
        CREATE_HAMMER.setVolume(volumeLevel);
        
        SHIP_DEATH.setVolume(volumeLevel);
    }
    
    public static AudioNode getBattleLaser(int index)
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
    
    public static AudioNode getCreate()
    {
        return CREATE_CREATE.clone();
    }
    public static AudioNode getDCWielding()
    {
        return CREATE_DC_WIELDING.clone();
    }
    public static AudioNode getGearBursts()
    {
        return CREATE_GEAR_BURSTS.clone();
    }
    public static AudioNode getHammer()
    {
        return CREATE_HAMMER.clone();
    }
    public static AudioNode getShipDeath()
    {
        return SHIP_DEATH.clone();
    }
}