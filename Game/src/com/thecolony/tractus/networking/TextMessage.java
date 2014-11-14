/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacecolonization.networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


@Serializable(id=0)
public class TextMessage extends AbstractMessage {
     private String text = "Hello SpiderMonkey!"; // your message data
     public TextMessage() {}                  // empty default constructor
     public TextMessage(String s) {           // custom constructor
       text = s;
     }                  
     public void setText(String s){text = s;}
     public String getText(){return text;}
     
}