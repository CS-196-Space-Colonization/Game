package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.saveInfo.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Continent extends Territory {
    protected double size; //Placeholder for area of the continent
    protected int population;
    public Continent(Planet planet, Res res, int population, double size, String name, String owner){
        super(new Vector3f(-1,-1,-1),planet,null,res,name,owner);
        //This Continents don't have a spatial position, nor do they have subterritories
        this.population=population;
        this.size=size;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) { this.population = population; }

}
