/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
/**
 *
 * @author Arturo
 */
@Serializable(id = 0)
public class UpdateClientMessage extends AbstractMessage
{
   private String greeting = "Hello SpiderMonkey!"; // your message data
     public UpdateClientMessage() {}                  // empty default constructor
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
     
     public void generateWorld()
     {
         Game game = new Game();
     }
}
