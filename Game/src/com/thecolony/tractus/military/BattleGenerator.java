package com.thecolony.tractus.military;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.thecolony.tractus.graphics.GraphicsManager;
import com.thecolony.tractus.military.battle.FlotillaBattler;
import com.thecolony.tractus.military.ships.Flotilla;
import com.thecolony.tractus.military.ships.Ship;
import com.thecolony.tractus.player.Player;
import com.thecolony.tractus.saveInfo.Filer;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wes on 12/9/14.
 */
public class BattleGenerator {
    private static Node rootNode, loneShipsNode, flotillasNode;
    private static ArrayList<Ship> loneShips,notLoneships;
    private static ArrayList<Flotilla> flotillas;
    private static ArrayList<FlotillaBattler> flotillaBattles;
    private static double[] stats;
    private static Filer filer;
    public static void loadBattlers(boolean loadSave, Node rootNode, Node loneShipsNode, ArrayList<Ship> ships, Node flotillasNode, ArrayList<Flotilla> flotillas, ArrayList<FlotillaBattler> battles, double[] stats, Filer filer){
        BattleGenerator.stats=stats;
        BattleGenerator.rootNode=rootNode;
        BattleGenerator.loneShipsNode=loneShipsNode;
        BattleGenerator.loneShips=ships;
        BattleGenerator.flotillasNode=flotillasNode;
        BattleGenerator.flotillas=flotillas;
        BattleGenerator.flotillaBattles=battles;
        BattleGenerator.filer=filer;
        File file=new File("resources/tractus.xml");
        if(!loadSave || !file.exists()){
            makeShips();
            makeFlotillas();
            addThings();
        }
        else {
            loadShip();
            loadFlotilla();
            loadBattles();
        }
    }
    private static Ship generateShip(int player, Node node, int num, double[] stats) {
        return generateShip(player, node, num, Vector3f.ZERO, stats);
    }

    private static Ship generateShip(int player, Node node, int num, Vector3f pos, double[] stats) {
        return generateShip(player, node, num, Ship.SHIP_TYPE.Fighter, pos, stats);
    }

    private static Ship generateShip(int player, Node node, int num, Ship.SHIP_TYPE type, double[] stats) {
        return generateShip(player, node, num, type, Vector3f.ZERO, stats);
    }

    private static Ship generateShip(int player, Node node, int num, Ship.SHIP_TYPE type, Vector3f pos, double[] stats) {
        Player playah = new Player(player);
        Ship shit = new Ship(playah, type, type.toString() + num, node, pos, stats, 0, 0, 0, 0.0);
        shit.getMoveableObject3d().getModel().setMaterial(GraphicsManager.generateMaterial(shit.getPlayer().getColor()));
        return shit;
    }
    private static void makeShips()
    {
        loneShips = new ArrayList<Ship>();

        for (int i = 0; i < 5; i++)
        {
            loneShips.add(generateShip(3, loneShipsNode, i, new Vector3f(0.0f, 0.0f, -(30 + i * 3)), stats));
        }
    }
    private static void addShips(Ship s){
        Element ship=filer.addObject("ship","name",s.getName());
        String type="?";
        switch (s.getType()) {
            case Fighter: type="Fi";
                break;
            case Frigate: type="Fr";
                break;
            case Cruiser: type="Cr";
                break;
            case CapitalShip: type="Ca";
                break;
        }
        String stat="";
        for(double d:stats) stat+=d+",";
        String loc=s.getPosition().getX()+",0,"+s.getPosition().getZ();
        String node=s.getMoveableObject3d().getNode().toString();
        filer.addInfo(ship,"type",""+type);
        filer.addInfo(ship,"player",""+s.getPlayer().getPlayerNumber());
        filer.addInfo(ship,"node",""+node);
        filer.addInfo(ship,"loc",""+loc);
        filer.addInfo(ship,"stats",""+stat.substring(0,stat.length()-1));
        filer.addInfo(ship,"cost","0");
        filer.addInfo(ship,"crew","0");
        filer.addInfo(ship,"ammo","0");
        filer.addInfo(ship,"fuel","0");
    }
    private static void makeFlotillas(){
        Ship[] ships1 = new Ship[25];
        for (int i = 0; i < ships1.length; i++)
        {
            ships1[i] = generateShip(0, flotillasNode, i, stats);
        }

        Ship[] ships2 = new Ship[9];
        for (int i = 0; i < ships2.length; i++)
        {
            ships2[i] = generateShip(1, flotillasNode, i, Ship.SHIP_TYPE.CapitalShip, stats);
        }

        Ship[] ships3 = new Ship[25];
        for (int i = 0; i < ships3.length; i++)
        {
            ships3[i] = generateShip(2, flotillasNode, i, stats);
        }

        flotillas = new ArrayList<Flotilla>();
        flotillas.add(new Flotilla(ships1, false, new Vector3f(-50.0f, 0.0f, 100.0f), "Flotilla 1"));
        flotillas.add(new Flotilla(ships2, false, new Vector3f(50.0f, 0.0f, 0.0f), "Flotilla 2"));
        flotillas.add(new Flotilla(ships3, false, new Vector3f(100.0f, 0.0f, 25.0f), "Flotilla 3"));
        flotillaBattles = new ArrayList<FlotillaBattler>();
    }
    private static void addFlotillas(Flotilla f){
        Element flotilla=filer.addObject("flotilla","name",f.getName());
        String ship="";
        for(Ship s:f.getFlotilla()) ship+=s.getName();
        String loc=f.getPosition().getX()+",0,"+f.getPosition().getZ();
        filer.addInfo(flotilla,"ships",""+ship);
        filer.addInfo(flotilla,"loc",""+loc);
    }
    private static void addFlotillaBattler(FlotillaBattler f){
        Element flotillaBattle=filer.addObject("flotillaBattles");
        filer.addInfo(flotillaBattle,"attacker",""+f.getAttacker().toString());
        filer.addInfo(flotillaBattle,"defender",""+f.getDefender().toString());
    }
    private static void addThings(){
        for (int i=0;i<loneShips.size();i++) addShips(loneShips.get(i));
        for (int i=0;i<flotillas.size();i++){
            for (int j=0;j<flotillas.get(i).getFlotilla().length;j++) addShips(flotillas.get(i).getShip(j));
            addFlotillas(flotillas.get(i));
        }
        for (int i=0;i<flotillaBattles.size();i++) addFlotillaBattler(flotillaBattles.get(i));
    }
    private static void loadShip(){
        NodeList list=filer.getObject("ship");
        for(int i=0;i<list.getLength();i++){
            if(list.item(i).getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
                Element el=(Element)list.item(i);
                String name=el.getAttribute("name");
                String pos=el.getElementsByTagName("loc").item(0).getTextContent(), poss[]=pos.split(",");
                Vector3f vect=new Vector3f(Float.parseFloat(poss[0]),Float.parseFloat(poss[1]),Float.parseFloat(poss[2]));
                String stat=el.getElementsByTagName("stats").item(0).getTextContent(), statss[]=stat.split(",");
                double[] stats=new double[statss.length];
                for(int j=0;j<statss.length;j++) stats[j]=Double.parseDouble(statss[j]);
                String ply=el.getElementsByTagName("player").item(0).getTextContent();;
                int playa =Integer.parseInt(ply);
                String typ=el.getElementsByTagName("loc").item(0).getTextContent();Ship.SHIP_TYPE type;
                if(typ.equals("Fi")) {type= Ship.SHIP_TYPE.Fighter;}
                else if(typ.equals("Fr")) {type= Ship.SHIP_TYPE.Frigate;}
                else if(typ.equals("Cr")) {type= Ship.SHIP_TYPE.Cruiser;}
                else {type= Ship.SHIP_TYPE.CapitalShip;}
                if (el.getElementsByTagName("node").item(0).getTextContent().equals(loneShipsNode.toString())){
                    loneShips.add(generateShip(playa,loneShipsNode,i,vect,stats));
                }
                else if(el.getElementsByTagName("node").item(0).getTextContent().equals(flotillasNode.toString())) {
                    notLoneships.add(generateShip(playa,flotillasNode,i,type,stats));
                }
            }

        }
    }
    private static void loadFlotilla(){
        NodeList list=filer.getObject("flotilla");
        flotillas = new ArrayList<Flotilla>();
        for(int i=0;i<list.getLength();i++){
            if(list.item(i).getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
                Element el=(Element)list.item(i);
                String name=el.getAttribute("name");
                String pos=el.getElementsByTagName("loc").item(0).getTextContent(), poss[]=pos.split(",");
                Vector3f vect=new Vector3f(Float.parseFloat(poss[0]),Float.parseFloat(poss[1]),Float.parseFloat(poss[2]));
                String ship=el.getElementsByTagName("ships").item(0).getTextContent(), ships[]=ship.split(",");
                ArrayList<Ship> flot=new ArrayList<Ship>();
                for (int j=0;j<notLoneships.size();j++) {
                    if (notLoneships.get(j).getPlayer().getPlayerNumber()==i) flot.add(notLoneships.get(j));
                }
                flotillas.add(new Flotilla(flot.toArray(new Ship[0]), false, vect, name));
            }
        }
    }
    private static void loadBattles() {
        NodeList list = filer.getObject("flotilla");
        flotillaBattles=new ArrayList<FlotillaBattler>();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element el=(Element)list.item(i);
                String attack=el.getElementsByTagName("attacker").item(0).getTextContent();
                String defender=el.getElementsByTagName("defender").item(0).getTextContent();
                Flotilla ataturk=null,defLeppard=null;
                for(int j=0;j<flotillas.size();j++){
                    if(flotillas.get(j).getName().equals(attack)) ataturk=flotillas.get(j);
                    if(flotillas.get(j).getName().equals(defender)) defLeppard=flotillas.get(j);
                }
                flotillaBattles.add(new FlotillaBattler(ataturk,defLeppard));
            }
        }
    }
}
