package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import resources.Res;

/**
 * Created by chthonic7 on 10/8/14.
 */
public class Star extends VisualEntity {
    private int ID;
    private static int ID_COUNT=0;
    public Star(Vector3f pos, String name, Node node, Spatial model, VisualType type){
        super(pos, name, node, model, type);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    public Star(Vector3f pos, StarSystem starSys, Planet[] planets, Res res, String name, String owner, Node node, AssetManager manager, ColorRGBA color, VisualType type){
        super(pos,starSys,planets,res,name,owner,node,manager,color,type);
        this.ID_COUNT++;
        this.ID=ID_COUNT;
    }
    @Override
    public int getID() {
        return this.ID;
    }
}
