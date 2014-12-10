package com.thecolony.tractus.worldgen;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.thecolony.tractus.saveInfo.Res;
import com.thecolony.tractus.saveInfo.Filer;
import com.thecolony.tractus.worldgen.SpatialEntities.Planet;
import com.thecolony.tractus.worldgen.SpatialEntities.Star;
import com.thecolony.tractus.worldgen.SpatialEntities.Territory;
import com.thecolony.tractus.worldgen.SpatialEntities.VisualType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;

/**
 * Created by wes on 12/6/14.
 */
public class TerrorGenerator {
    private static Planet[] mPlanets;
    private static Star[] mSuns;
    private static Node planetsNode,starsNode,rootNode;
    private static AssetManager assetManager;
    private static Filer filer;
    public static void loadTerritories(boolean loadSave, Node rootNode, Node planetsNode, Planet[] planets, Node starsNode, Star[] stars, AssetManager assetManager, Filer filer)
    {
        TerrorGenerator.planetsNode=planetsNode;
        TerrorGenerator.mPlanets=planets;
        TerrorGenerator.starsNode=starsNode;
        TerrorGenerator.mSuns=stars;
        TerrorGenerator.rootNode=rootNode;
        TerrorGenerator.assetManager=assetManager;
        TerrorGenerator.filer=filer;
        File file=new File("resources/tractus.xml");
        if(loadSave && file.exists()) {
            loadPlanet();
            loadStar();
            for (int i = 0; i < mPlanets.length; i++) mPlanets[i].setSuperTerr(mSuns[0]);
            mSuns[0].setSubTerr(mPlanets);
        }
        else {
            makePlanets();
            makeStars();
            for (int i = 0; i < mPlanets.length; i++) mPlanets[i].setSuperTerr(mSuns[0]);
            mSuns[0].setSubTerr(mPlanets);
            addTerrs();
        }
    }

    private static Planet generatePlanet(int index)
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
        int orbitRadius = 25 + (20 * (index + 1));
        float xPos = posNeg * (int) (Math.random() * orbitRadius);
        posNeg = (Math.random() < 0.5) ? -1 : 1;
        float zPos = posNeg * (float) Math.sqrt(orbitRadius * orbitRadius - xPos * xPos);
        Res res=new Res();
        return new Planet(new Vector3f(xPos, 0.0f, zPos), null, null,res, "Planet " + Integer.toString(index), "no-one", planetsNode, assetManager, ColorRGBA.randomColor(), type);
    }

    private static void makePlanets()
    {
        for (int i = 0; i < mPlanets.length; i++)
        {
            mPlanets[i] = generatePlanet(i);
        }
    }

    private static void makeStars()
    {
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
        Res res=new Res();
        mSuns[0] = new Star(Vector3f.ZERO, null, null, res, "StarX", "no-one", starsNode, assetManager, ColorRGBA.White, type);
        rootNode.addLight(mSuns[0].getPointLight());
    }
    private static void addTerrs(){
        for(Planet p:mPlanets) addPlanet(p);
        for(Star s:mSuns) addStar(s);
    }
    private static void addPlanet(Planet p){
        Element planet=filer.addObject("planet", "name", p.getName());
        String type="?";
        switch (p.getType()){
            case LAVA_PLANET: type="L";
                break;
            case SUBEARTH_PLANET: type="S";
                break;
            case TERRESTRIAL_PLANET: type="T";
                break;
            case MININEPTUNE_PLANET: type="M";
                break;
            case GASGIANT_PLANET: type="G";
                break;
        }
        String cont="";
        if(p.getSubTerr()!=null)for (Territory continent:p.getSubTerr()) cont+=continent.getName()+",";
        String loc=p.getLocation().getX()+",0,"+p.getLocation().getZ();
        filer.addInfo(planet, "type", type);
        filer.addInfo(planet, "loc", loc);
        filer.addInfo(planet, "color", "" + p.getColor().asIntRGBA());
        filer.addInfo(planet, "res", ""+p.getResName().substring(7));
        filer.addInfo(planet, "star", p.getSuperTerr().getName());
        filer.addInfo(planet, "conts", cont.length()==0?cont:cont.substring(0, cont.length() - 1));
        filer.write();
    }
    private static void addStar(Star s){
        Element star=filer.addObject("Star", "name", s.getName());
        String type="?";
        switch (s.getType()){
            case DWARF_STAR: type="D";
                break;
            case MAINSEQUENCE_STAR: type="M";
                break;
            case GIANT_STAR: type="G";
                break;
            case SUPERGIANT_STAR: type="S";
                break;
        }
        String planets="";
        if(s.getSubTerr()!=null)for (Territory planet:s.getSubTerr()) planets+=planet.getName()+",";
        String loc=s.getLocation().getX()+",0,"+s.getLocation().getZ();
        filer.addInfo(star, "type", type);
        filer.addInfo(star, "loc", loc);
        filer.addInfo(star, "color", "" + s.getColor().asIntRGBA());
        filer.addInfo(star, "res", ""+s.getResName().substring(7));
        filer.addInfo(star, "starSys", "");
        filer.addInfo(star, "planets", planets.length()==0?planets:planets.substring(0, planets.length() - 1));
        filer.write();
    }
    public static void loadPlanet(){
        NodeList list=filer.getObject("planet");
        for (int i=0;i<list.getLength();i++){
            if(list.item(i).getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
                Element el=(Element)list.item(i);
                String name=el.getAttribute("name");
                //System.out.print(el.getElementsByTagName("loc").item(0).getTextContent());
                String pos=el.getElementsByTagName("loc").item(0).getTextContent(), poss[]=pos.split(",");
                Vector3f vect=new Vector3f(Float.parseFloat(poss[0]),Float.parseFloat(poss[1]),Float.parseFloat(poss[2]));
                String color=el.getElementsByTagName("color").item(0).getTextContent(); ColorRGBA col=new ColorRGBA(); col.fromIntRGBA(Integer.parseInt(color));
                String res=el.getElementsByTagName("res").item(0).getTextContent();
                String star=el.getElementsByTagName("star").item(0).getTextContent();
                String conts=el.getElementsByTagName("conts").item(0).getTextContent();
                String typ=el.getElementsByTagName("type").item(0).getTextContent();
                VisualType type;
                if (typ.equals("L")) {type=VisualType.LAVA_PLANET;}
                else if (typ.equals("S")) {type=VisualType.SUBEARTH_PLANET;}
                else if (typ.equals("T")) {type=VisualType.TERRESTRIAL_PLANET;}
                else if (typ.equals("M")) {type=VisualType.MININEPTUNE_PLANET;}
                else {type=VisualType.GASGIANT_PLANET;}
                mPlanets[i]=new Planet(vect,null,null,new Res("resFile"+res),name,"no-one",planetsNode,assetManager,col,type);
            }
        }
    }
    public static void loadStar(){
        NodeList list=filer.getObject("Star");
        for (int i=0;i<list.getLength();i++){
            if(list.item(i).getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
                Element el=(Element)list.item(i);
                String name=el.getAttribute("name");
                String pos=el.getElementsByTagName("loc").item(0).getTextContent(), poss[]=pos.split(",");
                Vector3f vect=new Vector3f(Float.parseFloat(poss[0]),Float.parseFloat(poss[1]),Float.parseFloat(poss[2]));
                String color=el.getElementsByTagName("color").item(0).getTextContent(); ColorRGBA col=new ColorRGBA(); col.fromIntRGBA(Integer.parseInt(color));
                String res=el.getElementsByTagName("res").item(0).getTextContent();
                String star=el.getElementsByTagName("starSys").item(0).getTextContent();
                String conts=el.getElementsByTagName("planets").item(0).getTextContent();
                String typ=el.getElementsByTagName("type").item(0).getTextContent();
                VisualType type;
                if (typ.equals("D")) {type=VisualType.DWARF_STAR;}
                else if (typ.equals("M")) {type=VisualType.MAINSEQUENCE_STAR;}
                else if (typ.equals("G")) {type=VisualType.GIANT_STAR;}
                else {type=VisualType.SUPERGIANT_STAR;}
                mSuns[i]=new Star(vect,null,null,new Res("resFile"+res),name,"no-one",starsNode,assetManager,col,type);
                rootNode.addLight(mSuns[i].getPointLight());
            }
        }
    }
}
