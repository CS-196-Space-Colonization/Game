/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.thecolony.tractus.networking.messages.UpdateMessage;
import com.jme3.network.serializing.Serializer;

public class Globals
{

    public static String NAME = "Server Name";
    public static String DEFAULT_SERVER = "localhost";//172.17.218.92
    public static int VERSION = 1;
    public static int DEFAULT_PORT = 6143;

    public static void registerClasses()
    {
        Serializer.registerClass(UpdateMessage.class);
        Serializer.registerClass(TestClass.class);
        Serializer.registerClass(TestSub.class);
    }

    public static void setInfo(String n, String v, String i, String p)
    {
        NAME = n;
        DEFAULT_SERVER = i;
        VERSION = Integer.parseInt(v);
        DEFAULT_PORT = Integer.parseInt(p);
    }
    
    public static void setName(String n)
    {
        NAME = n;
    }
}
