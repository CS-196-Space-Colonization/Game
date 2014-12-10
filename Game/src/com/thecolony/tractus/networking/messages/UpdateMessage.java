/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking.messages;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.thecolony.tractus.military.ships.Flotilla;
import com.thecolony.tractus.military.ships.Ship;
import com.thecolony.tractus.worldgen.SpatialEntities.Planet;
import java.util.ArrayList;

/**
 *
 * @author Arturo
 */
@Serializable
public class UpdateMessage extends AbstractMessage
{

    private String message = "I'm updating!!!"; // your message data
//    private ArrayList<Planet> planets;
//    private ArrayList<Ship> ships;
//    private ArrayList<Flotilla> flotillas;

    public UpdateMessage(){}

    public UpdateMessage(String s)
    {
        message = s;
    }

    public void setMessage(String s)
    {
        message = s;
    }

    public String getMessage()
    {
        return message;
    }
    
//    public ArrayList<Planet> getPlanets()
//    {
//        return planets;
//    }
//
//    public void setPlanets(ArrayList<Planet> planets)
//    {
//        this.planets = planets;
//    }
//
//    public ArrayList<Ship> getShips()
//    {
//        return ships;
//    }
//
//    public void setShips(ArrayList<Ship> ships)
//    {
//        this.ships = ships;
//    }
//
//    public ArrayList<Flotilla> getFlotillas()
//    {
//        return flotillas;
//    }
//
//    public void setFlotillas(ArrayList<Flotilla> flotillas)
//    {
//        this.flotillas = flotillas;
//    }
}
