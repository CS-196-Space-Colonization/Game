package com.thecolony.tractus.worldgen.SpatialEntities;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.thecolony.tractus.player.ai.battle.BattleObject;
import com.thecolony.tractus.resources.Res;

/**
 * Created by wes on 12/3/14.
 */
public class RegionalEntity extends Territory{
    protected BoundingBox box;
    public RegionalEntity(Vector3f centre, float xExt, float yExt, float zExt, Territory superTerr, Territory[] terr, Res res, String name, String owner){
        super(centre,superTerr,terr,res,name,owner);
        box=new BoundingBox(centre, xExt,yExt,zExt);
    }
    public boolean isInside(BattleObject o){
        return box.contains(o.getPosition());
    }
}
