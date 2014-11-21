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
     private float posX, posY, posZ;
     private float colR, colG, colB, colA;
    
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
         posX = position.x;
         posY = position.y;
         posZ = position.z;
         
         colR = color.r;
         colG = color.g;
         colB = color.b;
         colA = color.a;
     }
     
     public Vector3f getPlanetPosition()
     {
         return new Vector3f(posX, posY, posZ);
     }
     public ColorRGBA getPlanetColor()
     {
         return new ColorRGBA(colR, colG, colB, colA);
     }
}
