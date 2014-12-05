package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.resources.Res;
import java.io.Serializable;

/**
 * Created by chthonic7 on 10/15/14.
 */
@com.jme3.network.serializing.Serializable
public abstract class Territory implements Serializable {
    protected Vector3f location; //Or any sort of spatial positioning...
    protected String name, owner;
    protected Res res;
    protected Territory superTerr, subTerr[];
    protected static int ID_COUNT=0;
    protected int ID;

    protected Territory(float locX, float locZ, Territory superTerr, Territory[] terr, Res res, String name, String owner){
        this.subTerr=terr;
        this.superTerr=superTerr;
        this.location=new Vector3f(locX,0,locZ);
        this.res=res;
        this.name=name;
        this.owner=owner;
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    //These next few getters/setters are for convenience. One can also just get the vector directly.
    public final float getLocationX() {
        return location.getX();
    }

    public final void setLocationX(float locationX) {
        this.location.setX(locationX);
    }

    public final float getlocationZ() {
        return location.getZ();
    }

    public final void setlocationZ(float locationZ) {
        this.location.setZ(locationZ);
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public int getID() {
        return ID;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getOwner() {
        return owner;
    }

    public final void setOwner(String owner) {
        this.owner = owner;
    }

    public final Territory getSuperTerr() {
        return superTerr;
    }

    public final void setSuperTerr(Territory superTerr) {
        this.superTerr = superTerr;
    }

    public final Territory[] getSubTerr() {
        return subTerr;
    }

    public final void setSubTerr(Territory[] subTerr) {
        this.subTerr = subTerr;
    }

    public final double getResource(String resourceName){
        return res.getResource(resourceName);
    }

    public final boolean setResource(String resourceName, double value){
        return res.setResource(resourceName,value);
    }

    public final void updateResources(){
        if (subTerr!=null) {
            for (Territory terr : this.subTerr) {
                for (String str : terr.listResources()) {
                    this.res.setResource(str, this.res.getResource(str) + terr.getResource(str));
                }
            }
        }
    }

    protected final String[] listResources(){
        return this.res.listResource();
    }

    public final String toString(){
        String str=this.getClass().toString(); str=str.substring(str.lastIndexOf('.')+1);
        return ((superTerr==null)?"":superTerr.toString())+" "+str+" "+name+((str.equals("Continent"))?"":(" at ("+location.getX()+","+location.getZ()+")"))+" is owned by "+owner+"\n";
    }

    public final boolean equals(Object o){
        return (o.getClass().equals(this.getClass())) && ((Territory)o).getID()==this.ID;
    }
}
