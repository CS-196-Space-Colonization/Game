package com.thecolony.tractus.worldgen.SpatialEntities;

import resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Continent extends Territory {
    protected double size; //Placeholder for area of the continent
    protected int population;
    private int ID;
    private static int ID_COUNT=0;
    public Continent(Planet planet, Res res, int population, double size, String name, String owner){
        super(-1.0f,-1.0f,planet,null,res,name,owner);
        //This Continents don't have a spatial position, nor do they have subterritories
        this.population=population;
        this.size=size;
        this.ID_COUNT++;
        this.ID=ID_COUNT;
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

    @Override
    public int getID() {
        return this.ID;
    }
}
