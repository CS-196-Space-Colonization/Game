/**
 * This is the main player class that contains all the player information.
 *
 * @author Arturo Guerrero
 */
package com.thecolony.tractus.player;

import java.util.ArrayList;

import com.thecolony.tractus.Map;
import com.thecolony.tractus.Trade;
import com.thecolony.tractus.Unit;
import com.thecolony.tractus.player.ai.battle.ships.Ship;
import com.thecolony.tractus.techonology.Technology;

public class Player
{
    // viewable to other players

    protected Technology currentTech;
    protected ArrayList<Player> allies;
    protected boolean atWar;
    // not viewable to other players
    protected double money;
    protected ArrayList<Technology> researched;
    protected ArrayList<Trade> trades;
    protected int playerNumber;
    // Fog of War (light)
    // explored terrain needs a way to be kept track of
    protected static Map map; // same map for all players
    protected ArrayList<Ship> ownShips; //units that you can control
    protected ArrayList<Ship> otherShips; //other units that don't belong to you and are visible to you?

    public Player(int playerNumber)
    {
        setCurrentTech(new Technology());
        setAllies(new ArrayList<Player>());
        setAtWar(false);
        setMoney(0);
        setResearched(new ArrayList<Technology>());
        setTrades(new ArrayList<Trade>());
        setPlayerNumber(playerNumber);
        setOwnShips(new ArrayList<Ship>());
        setOtherShips(new ArrayList<Ship>());
    }

    public void sendMessage()
    {
    }

    public void updatePlayer()
    {
    }
    
    protected boolean hasUnits()
    {
        return !ownShips.isEmpty();
    }
    
    // setters and getters for all instance variables, nothing special, will
    // probably altered later
    public Technology getCurrentTech()
    {
        return currentTech;
    }

    public void setCurrentTech(Technology currentTech)
    {
        this.currentTech = currentTech;
    }

    public ArrayList<Player> getAllies()
    {
        return allies;
    }

    public void setAllies(ArrayList<Player> allies)
    {
        this.allies = allies;
    }

    public boolean isAtWar()
    {
        return atWar;
    }

    public void setAtWar(boolean atWar)
    {
        this.atWar = atWar;
    }

    public double getMoney()
    {
        return money;
    }

    public void setMoney(double money)
    {
        this.money = money;
    }

    public ArrayList<Technology> getResearched()
    {
        return researched;
    }

    public void setResearched(ArrayList<Technology> researched)
    {
        this.researched = researched;
    }

    public ArrayList<Trade> getTrades()
    {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades)
    {
        this.trades = trades;
    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber)
    {
        this.playerNumber = playerNumber;
    }

    public Map getMap()
    {
        return map;
    }

    public void setMap(Map map)
    {
        Player.map = map;
    }

    public ArrayList<Ship> getOwnShips()
    {
        return ownShips;
    }

    public void setOwnShips(ArrayList<Ship> ships)
    {
        this.ownShips = ships;
    }

    public ArrayList<Ship> getOtherShips()
    {
        return otherShips;
    }

    public void setOtherShips(ArrayList<Ship> otherShips)
    {
        this.otherShips = otherShips;
    }
}
