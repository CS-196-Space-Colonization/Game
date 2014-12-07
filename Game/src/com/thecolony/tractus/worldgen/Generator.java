package com.thecolony.tractus.worldgen;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.thecolony.tractus.worldgen.SpatialEntities.Planet;
import com.thecolony.tractus.worldgen.SpatialEntities.Star;
import com.thecolony.tractus.worldgen.SpatialEntities.VisualType;

/**
 * Created by wes on 12/6/14.
 */
public class Generator {
    Planet[] mPlanets; Star[] mSuns;Node planetsNode,starsNode,rootNode;AssetManager assetManager;
    private void loadThings()
    {
        loadPlanets();
        loadSun();
        for (int i = 0; i < mPlanets.length; i++)
        {
            mPlanets[i].setSuperTerr(mSuns[0]);
        }
        mSuns[0].setSubTerr(mPlanets);
    }

    private Planet generatePlanet(int index)
    {
        VisualType type = null;
        switch ((int) (Math.random() * 5))
        {
            case (0):
                type = VisualType.LAVA_PLANET;
                break;
            case (1):
                type = VisualType.SUBEARTH_PLANET;
                break;
            case (2):
                type = VisualType.TERRESTRIAL_PLANET;
                break;
            case (3):
                type = VisualType.MININEPTUNE_PLANET;
                break;
            case (4):
                type = VisualType.GASGIANT_PLANET;
                break;
        }
        int posNeg = (Math.random() < 0.5) ? -1 : 1;
        int orbitRadius = 15 + (25 * (index + 1));
        float xPos = posNeg * (int) (Math.random() * orbitRadius);
        posNeg = (Math.random() < 0.5) ? -1 : 1;
        float zPos = posNeg * (float) Math.sqrt(orbitRadius * orbitRadius - xPos * xPos);

        return new Planet(new Vector3f(xPos, 0.0f, zPos), null, null, null, "Planet " + Integer.toString(index), "no-one", planetsNode, assetManager, ColorRGBA.randomColor(), type);
    }

    private void loadPlanets()
    {
        planetsNode = new Node("Planets Node");
        mPlanets = new Planet[10];

        for (int i = 0; i < mPlanets.length; i++)
        {
            mPlanets[i] = generatePlanet(i);
        }
    }

    private void loadSun()
    {
        starsNode = new Node("Stars Node");
        VisualType type = null;
        switch ((int) (Math.random() * 4))
        {
            case (0):
                type = VisualType.DWARF_STAR;
                break;
            case (1):
                type = VisualType.MAINSEQUENCE_STAR;
                break;
            case (2):
                type = VisualType.GIANT_STAR;
                break;
            case (3):
                type = VisualType.SUPERGIANT_STAR;
                break;
        }
        mSuns = new Star[1];
        mSuns[0] = new Star(Vector3f.ZERO, null, null, null, "StarX", "no-one", starsNode, assetManager, ColorRGBA.White, type);
        rootNode.addLight(mSuns[0].getPointLight());
    }
}
