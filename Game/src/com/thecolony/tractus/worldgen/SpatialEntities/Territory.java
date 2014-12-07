package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.math.Vector3f;
import com.thecolony.tractus.resources.Res;
import com.jme3.network.serializing.Serializable;

import java.util.ArrayList;

/**
 * Created by chthonic7 on 10/15/14.
 */
@Serializable
public abstract class Territory{
    protected Vector3f location; //Or any sort of spatial positioning...
    protected String name, owner, resName;
    protected transient Res res;
    protected Territory superTerr, subTerr[];
    protected String className;
    protected Territory(Vector3f pos, Territory superTerr, Territory[] terr, Res res, String name, String owner){
        this.subTerr=terr;
        this.superTerr=superTerr;
        this.location=pos;
        this.res=res;
        this.resName=(res==null)?"":res.toString();
        this.name=name;
        this.owner=owner;
        className=this.getClass().toString(); className=className.substring(className.lastIndexOf('.')+1);
    }
    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
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

    public final String getResName() {return resName;}

    public final void setResName(String resName) {this.resName=resName;}

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
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
        return (o.getClass().equals(this.getClass())) && this.getResName().equals(((Territory)o).getResName());
    }
    public final String[] getDisplayInfo(){
        ArrayList<String> infos=new ArrayList<String>();
        if (superTerr!=null){
            for(String str:superTerr.getDisplayInfo()) infos.add(str);
        }
        infos.add(this.className+": "+name);
        return infos.toArray(new String[0]);
    }
}
