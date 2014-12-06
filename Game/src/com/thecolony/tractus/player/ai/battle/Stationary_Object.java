package com.thecolony.tractus.player.ai.battle;
//this class will be extended to make more specific objects

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.thecolony.tractus.player.Player;
import java.io.IOException;

public class Stationary_Object extends BattleObject {
	public Stationary_Object()
	{
		super();
	}
	public Stationary_Object(double HP)
	{
		super(HP);
	}
	public Stationary_Object(Player owner, String nameOfShip, double[] stats, int Cost, String display, int Crew, int ammo)
	{
		super(owner, nameOfShip, stats, Cost, display, Crew, ammo);
	}

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
}
