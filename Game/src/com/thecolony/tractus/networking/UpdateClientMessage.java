/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
/**
 *
 * @author Arturo
 */
@Serializable
public class UpdateClientMessage extends AbstractMessage
{    
     private String greeting = "Hello SpiderMonkey!"; // your message data
     
     private String position;
     private String color;
     
     public UpdateClientMessage() { }                  // empty default constructor
     public UpdateClientMessage(String s) 
     {           // custom constructor
       greeting = s;
     }                  
     
     public UpdateClientMessage(String pos, String col)
     {
         setInfo(pos, col);
     }   
     public void setGreeting(String s)
     {
         greeting = s;
     }
     public String getGreeting()
     {
         return greeting;
     }
     
     public void setInfo(String pos, String col)
     {
         position = pos;
         color = col;
     }
     
     public Vector3f getPosition()
     {
         String[] s = position.split(",");
         float x = Float.parseFloat(s[0].substring(1));
         float y = Float.parseFloat(s[1]);
         float z = Float.parseFloat(s[2].substring(0, s[2].length() - 2));
         return new Vector3f(x, y, z);
     }
     
     public ColorRGBA getColor()
     {
         String[] s = color.substring(6).split(",");
         float r = Float.parseFloat(s[0]);
         float g = Float.parseFloat(s[1]);
         float b = Float.parseFloat(s[2]);
         float a = Float.parseFloat(s[3].substring(0, s[3].length() - 2));
         return new ColorRGBA(r, g, b, a);
     }
}
