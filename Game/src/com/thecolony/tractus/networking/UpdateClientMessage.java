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
     private String greeting = "I'm updating!!!"; // your message data
     
     private Vector3f position;
     private ColorRGBA color;
     
     public UpdateClientMessage() { }                  // empty default constructor
     public UpdateClientMessage(String s)
     {           // custom constructor
       greeting = s;
     }                  
     
     public UpdateClientMessage(Vector3f pos, ColorRGBA col)
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
     
     public void setInfo(Vector3f pos, ColorRGBA col)
     {
         position = pos;
         color = col;
     }
     
     public Vector3f getPosition()
     {
         return position;
     }
     
     public ColorRGBA getColor()
     {
         return color;
     }
}
