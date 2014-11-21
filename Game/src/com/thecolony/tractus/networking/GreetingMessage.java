/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities.Planet;

@Serializable(id = 0)
public class GreetingMessage extends AbstractMessage {

    private Planet[] myPlanets;
    private String greeting = "Hello SpiderMonkey!";

    public GreetingMessage() {
    }

    public GreetingMessage(String s, Planet[] mp) {
        greeting = s;
        myPlanets = new Planet[mp.length];

        for (int i = 0; i < mp.length; i++) {
            myPlanets[i] = mp[i];
        }
    }
    
    public GreetingMessage(String s){
        greeting = s;
    }
    
    public void setGreeting(String s) {
        greeting = s;
    }

    public void setMap(Planet[] mp) {
        
        for (int i = 0; i < mp.length; i++) {
            myPlanets[i] = mp[i];
        }
    }

    public String getGreeting() {
        return greeting;
    }
    public Planet[] getMap(){
        return myPlanets;
    }
    
}
