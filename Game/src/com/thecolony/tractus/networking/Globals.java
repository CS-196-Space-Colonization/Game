/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.jme3.network.serializing.Serializer;

public class Globals {

    public static final String NAME = "My Server";
    public static final String DEFAULT_SERVER = "localhost"; 
    public static final int VERSION = 1;
    public static final int DEFAULT_PORT = 6143;
    
    public static void registerClasses()
    {
        Serializer.registerClasses(UpdateClientMessage.class, TestClass.class, TestSub.class);
    }
    
}
//172.17.159.108 ananaa

