package com.thecolony.tractus.game;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Plane;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import com.thecolony.tractus.audio.AudioManager;
import com.thecolony.tractus.economics.BasicMarket;
import com.thecolony.tractus.economics.Firm;
import com.thecolony.tractus.economics.Inventory;
import com.thecolony.tractus.economics.Market;
import com.thecolony.tractus.economics.products.*;
import com.thecolony.tractus.graphics.GUI.PauseMenu;
import com.thecolony.tractus.graphics.GUI.ScrollText;
import com.thecolony.tractus.graphics.GraphicsManager;
import com.thecolony.tractus.player.Player;
import com.thecolony.tractus.military.battle.BattleObject;
import com.thecolony.tractus.military.battle.FlotillaBattler;
import com.thecolony.tractus.military.ships.Flotilla;
import com.thecolony.tractus.military.ships.SelectedFamily;
import com.thecolony.tractus.military.ships.Ship;
import com.thecolony.tractus.saveInfo.Filer;
import com.thecolony.tractus.worldgen.TerrorGenerator;
import com.thecolony.tractus.worldgen.SpatialEntities.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wes on 12/5/14.
 */
public class GameLoader
{
    private static Node planetsNode, starsNode, rootNode, guiNode, selectedShipsNode, selectedFlotillasNode, loneShipsNode, flotillasNode;
    private static AssetManager assetManager;
    private static InputManager inputManager;
    private static AudioRenderer audioRenderer;
    private static ViewPort guiViewport;
    private static Planet[] mPlanets;
    private static Star[] mSuns;
    private static Market market;
    private static ArrayList<Ship> loneShips;
    private static ArrayList<Flotilla> flotillas;
    private static ArrayList<FlotillaBattler> flotillaBattles;
    private static JmeCursor mCursorSmiley;
    private static Picture mPictureBoxSelect, mOverlay;
    private static BitmapFont guiFont;
    private static ScrollText mInfoHubText;
    private static int M_WIDTH, M_HEIGHT;
    private static final float M_INFO_HUB_WIDTH_PERCENTAGE = 10.0f / 1920.0f;
    private static final float M_INFO_HUB_HEIGHT_PERCENTAGE = 1.0f - 612.0f / 1080.0f;
    private static Plane mvmtPlane;
    private static SelectedFamily selectedFamily;
    private static boolean loadSave;

    public static Object[] loadGame(boolean loadSave,AssetManager assetManager, InputManager inputManager, Node guiNode, Node rootNode,
            AudioRenderer audioRenderer, ViewPort guiViewport, BitmapFont guiFont, int M_WIDTH, int M_HEIGHT)
    {
        GameLoader.loadSave=loadSave;
        GameLoader.assetManager = assetManager;
        GameLoader.inputManager = inputManager;
        GameLoader.rootNode = rootNode;
        GameLoader.audioRenderer = audioRenderer;
        GameLoader.guiNode = guiNode;
        GameLoader.guiFont = guiFont;
        GameLoader.guiViewport = guiViewport;
        GameLoader.M_HEIGHT = M_HEIGHT;
        GameLoader.M_WIDTH = M_WIDTH;
        
        GraphicsManager.loadGraphics(assetManager);
        AudioManager.loadAudio(assetManager, rootNode);
        planetsNode = new Node("Planets Node");
        mPlanets = new Planet[10];
        market = new BasicMarket();
        starsNode = new Node("Stars Node");
        mSuns = new Star[1];

        TerrorGenerator.loadTerritories(loadSave,rootNode, planetsNode, mPlanets, starsNode, mSuns, assetManager, new Filer("tractus"));
        loadSuckyEconomicSystem();
        loadShips();
        loadAmbientLight();
        loadSkybox();
        loadMovementPlane();
        loadPictures();
        loadText();
        addNodes();
        loadCursors();
        loadSelectedFamily();
        
        Object[] arr =
        {
            rootNode, guiNode, guiFont, inputManager, planetsNode, mPlanets, starsNode, mSuns, 
            loneShipsNode, loneShips, flotillasNode, flotillas, flotillaBattles, mvmtPlane, 
            mCursorSmiley, mPictureBoxSelect, mInfoHubText, selectedFamily, market
        };
        return arr;
    }
    
    private static void loadSuckyEconomicSystem()
    {
        Class[] products = new Class[]
                {
                    Carbon.class,
                    Iron.class,
                    OakWood.class,
                    Steel.class
                };
        Firm ironFirm = new Firm(market, new Iron());
        //Firm carbonFirm = new Firm(market, new Carbon());
        //Firm steelFirm = new Firm(market, new Steel());
        Map<Product, Quantity> moneyQuantity = new HashMap<Product, Quantity>();
        moneyQuantity.put(new Money(), new Quantity(new Money(), 10.0));
        Inventory moneyInventory = new Inventory(moneyQuantity);
        mPlanets[0].addFirm(ironFirm);
        
//        for(Planet p: mPlanets)
//        {
//            // Choose a random product
//            Class productClass = products[(int)(Math.random()*products.length)];
//            Constructor c = null;
//            try {
//                c = productClass.getConstructor();
//            } catch (NoSuchMethodException ex) {
//                Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SecurityException ex) {
//                Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                //Every planet produces labour
//                p.addFirm(new Firm(market, new Labor()));
//                //Add a random production firm on each planet
//                // and give it some money because an economy needs money to work
//                Firm f1 = new Firm(market, (Product)c.newInstance());
//                Map<Product, Quantity> money = new HashMap<Product, Quantity>();
//                Money m = new Money();
//                money.put(m, new Quantity(m, 10.0));
//                Inventory moneyInv = new Inventory(money);
//                f1.give(moneyInv);
//                p.addFirm(f1);
//            } catch (InstantiationException ex) {
//                Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IllegalAccessException ex) {
//                Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IllegalArgumentException ex) {
//                Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InvocationTargetException ex) {
//                Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    private static void loadSkybox()
    {
        Texture skybox_tex = assetManager.loadTexture("Textures/space_skybox.png");
        rootNode.attachChild(SkyFactory.createSky(assetManager, skybox_tex, skybox_tex, skybox_tex, skybox_tex, skybox_tex, skybox_tex));
    }

    private static void loadAmbientLight()
    {
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.7f));
        rootNode.addLight(ambientLight);
    }

    private static void loadMovementPlane()
    {
        mvmtPlane = new Plane(Vector3f.UNIT_Y, 0.0f);
    }

    private static void loadCursors()
    {
        mCursorSmiley = (JmeCursor) assetManager.loadAsset("Textures/cursor_smiley.cur");
        mCursorSmiley.setxHotSpot(mCursorSmiley.getWidth() >> 1);
        mCursorSmiley.setyHotSpot(mCursorSmiley.getHeight() >> 1);
        inputManager.setMouseCursor(null);
    }

    private static void loadPictures()
    {
        mPictureBoxSelect = new Picture("Box Select Picture");
        mPictureBoxSelect.setImage(assetManager, "Textures/box_select.png", true);
        mPictureBoxSelect.setUserData("Initial Position", Vector2f.ZERO);

        mOverlay = new Picture("Overlay Picture");
        mOverlay.setImage(assetManager, "Textures/in_game_background.png", true);
        mOverlay.setPosition(0.0f, 0.0f);
        mOverlay.setWidth(M_WIDTH);
        mOverlay.setHeight(M_HEIGHT);
        guiNode.attachChild(mOverlay);
    }

    private static void loadText()
    {
        float fontSize = (guiFont.getCharSet().getRenderedSize() * ((float) M_WIDTH / (float) M_HEIGHT)) / (1920.0f / 1080.0f);
        mInfoHubText = new ScrollText(M_HEIGHT, fontSize, M_INFO_HUB_WIDTH_PERCENTAGE * M_WIDTH,
                M_INFO_HUB_HEIGHT_PERCENTAGE * M_HEIGHT, guiFont, guiNode);
    }

    private static Ship generateShip(int player, Node node, int num, double[] stats)
    {
        return generateShip(player, node, num, Vector3f.ZERO, stats);
    }

    private static Ship generateShip(int player, Node node, int num, Vector3f pos, double[] stats)
    {
        return generateShip(player, node, num, Ship.SHIP_TYPE.Fighter, pos, stats);
    }

    private static Ship generateShip(int player, Node node, int num, Ship.SHIP_TYPE type, double[] stats)
    {
        return generateShip(player, node, num, type, Vector3f.ZERO, stats);
    }

    private static Ship generateShip(int player, Node node, int num, Ship.SHIP_TYPE type, Vector3f pos, double[] stats)
    {
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
    
    private static void addNodes()
    {
        rootNode.attachChild(planetsNode);
        rootNode.attachChild(starsNode);
        rootNode.attachChild(loneShipsNode);
        rootNode.attachChild(flotillasNode);
    }
    
    private static void loadSelectedFamily()
    {
        selectedFamily = new SelectedFamily(rootNode);
    }
}