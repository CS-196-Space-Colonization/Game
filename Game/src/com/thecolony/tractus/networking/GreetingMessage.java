/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacecolonization.networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


@Serializable(id=0)
public class GreetingMessage extends AbstractMessage {
     private String greeting = "Hello SpiderMonkey!"; // your message data
     public GreetingMessage() {}                  // empty default constructor
     public GreetingMessage(String s) {           // custom constructor
       greeting = s;
     }                  
     public void setGreeting(String s){greeting = s;}
     public String getGreeting(){return greeting;}
     
}


