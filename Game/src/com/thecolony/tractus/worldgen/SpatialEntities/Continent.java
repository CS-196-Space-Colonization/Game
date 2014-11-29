package com.thecolony.tractus.worldgen.SpatialEntities;

import com.thecolony.tractus.worldgen.resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Continent extends Territory {
    protected double size; //Placeholder for area of the continent
    protected int population;
    //Tech=techtree

    public Continent(Planet planet, Res res, int population, double size, String name, String owner){
        super(-1.0f,-1.0f,planet,null,res,name,owner);
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
    //population dynamics will be just implement logic using get/set pop/res

    //Something higher up will implement the game mechanics of the

}
