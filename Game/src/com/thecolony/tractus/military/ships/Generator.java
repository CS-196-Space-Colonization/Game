package com.thecolony.tractus.military.ships;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.thecolony.tractus.graphics.GraphicsManager;
import com.thecolony.tractus.military.battle.BattleObject;
import com.thecolony.tractus.military.battle.FlotillaBattler;
import com.thecolony.tractus.player.Player;

import java.util.ArrayList;

/**
 * Created by wes on 12/9/14.
 */
public class Generator {
    private static Node loneShipsNode, flotillasNode;
    private static ArrayList<Ship> loneShips;
    private static ArrayList<Flotilla> flotillas;
    private static ArrayList<FlotillaBattler> flotillaBattles;
    public static void loadBattlers(){

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
    private static void loadShips()
    {
        loneShipsNode = new Node("Lone Ships");

        double[] stats = new double[19];
        stats[BattleObject.BATTLE_STAT_MOVEMENT_SPEED] = 5.0;

        loneShips = new ArrayList<Ship>();

        for (int i = 0; i < 5; i++)
        {
            loneShips.add(generateShip(3, loneShipsNode, i, new Vector3f(0.0f, 0.0f, -(30 + i * 3)), stats));
        }
        ///////////////////////////////
        // Not Related /\ \/ //////////
        ///////////////////////////////

        flotillasNode = new Node("Flotillas Node");

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
//        attackers = new ArrayList<ArrayList<Flotilla>>();
//        defenders = new ArrayList<Flotilla>();
    }
}
