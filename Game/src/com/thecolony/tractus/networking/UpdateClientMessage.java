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
@Serializable(id = 0)
public class UpdateClientMessage extends AbstractMessage
{    
     private Vector3f position;
     private ColorRGBA color;
    
     private String greeting = "Hello SpiderMonkey!"; // your message data
     public UpdateClientMessage() { }                  // empty default constructor
     public UpdateClientMessage(String s) 
     {           // custom constructor
       greeting = s;
     }                  
     public void setGreeting(String s)
     {
         greeting = s;
     }
     public String getGreeting()
     {
         return greeting;
     }
     
     public void setInfo(Vector3f position, ColorRGBA color)
     {
         this.position = position;
         this.color = color;
     }
     
     public Vector3f getPlanetPosition()
     {
         return position;
     }
     public ColorRGBA getPlanetColor()
     {
         return color;
     }
}
