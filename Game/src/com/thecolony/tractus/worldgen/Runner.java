package com.thecolony.tractus.worldgen;

import com.thecolony.tractus.worldgen.SpatialEntities.*;
import com.thecolony.tractus.worldgen.resources.*;

/**
 * Created by chthonic7 on 10/15/14.
 */
public class Runner {
    public static void main(String args[]) {
        Sector a = new Sector(1.0, 1.0, null, new Res(), "a", "no-one");
        Cluster a1 = new Cluster(1.0, 2.0, a, null, new Res(), "a1", "no-one");
        StarSystem a1a = new StarSystem(1.0, 2.0, a1, null, new Res(), "a1a", "no-one");
        Star a1a1 = new Star(1.0, 2.0, Star.StarType.RED_GIANT, a1a, null, new Res(), "a1a1", "no-one");
        Planet a1a1a = new Planet(1.0, 2.0, PlanetType.GASGIANT, a1a1, null, new Res(), "a1a1a", "no-one");
        Continent a1a1a1 = new Continent(a1a1a, new Res("resFile"), 5, 2, "a1a1a1", "no-one");
        a1a1a.setSubTerr(new Continent[]{a1a1a1});
        a1a1.setSubTerr(new Planet[]{a1a1a});
        a1a.setSubTerr(new Star[]{a1a1});
        a1.setSubTerr(new StarSystem[]{a1a});
        a.setSubTerr(new Cluster[]{a1});
        Territory[] terrs = new Territory[]{a1a1a1, a1a1a, a1a1, a1a, a1, a};
        for (Territory terr:terrs){
            //terr.updateResources();
            System.out.println(terr);
        }
        System.out.println("iron="+a1a1a1.getResource("iron"));
        System.out.println("Changed resource? "+a1a1a1.setResource("iron",4.0));
        System.out.println("iron="+a1a1a1.getResource("iron"));
        a1a1a1.updateResources();
    }
}
