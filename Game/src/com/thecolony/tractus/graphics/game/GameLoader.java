package com.thecolony.tractus.graphics.game;

import com.jme3.asset.AssetManager;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Plane;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import com.thecolony.tractus.economics.Firm;
import com.thecolony.tractus.graphics.GUI.ScrollText;
import com.thecolony.tractus.graphics.drawableobjects.GameGraphics;
import com.thecolony.tractus.player.Player;
import com.thecolony.tractus.player.ai.battle.BattleObject;
import com.thecolony.tractus.player.ai.battle.FlotillaBattler;
import com.thecolony.tractus.player.ai.battle.ships.Flotilla;
import com.thecolony.tractus.player.ai.battle.ships.Ship;
import com.thecolony.tractus.resources.Res;
import com.thecolony.tractus.worldgen.SpatialEntities.*;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by wes on 12/5/14.
 */
public class GameLoader {
    Node planetsNode,starsNode,rootNode,guiNode,selectedShipsNode,selectedFlotillasNode,loneShipsNode,flotillasNode;
    AssetManager assetManager;
    InputManager inputManager;
    Planet[] mPlanets;
    Star[] mSuns;
    ArrayList<Ship> loneShips;ArrayList<Flotilla> flotillas; ArrayList<FlotillaBattler> flotillaBattles;
    JmeCursor mCursorSmiley;
    Picture mPictureBoxSelect,mOverlay;
    BitmapText mSelectionModeText;
    BitmapFont guiFont;
    ScrollText mInfoHubText;
    int M_WIDTH,M_HEIGHT;
    private final float M_INFO_HUB_WIDTH_PERCENTAGE = 10.0f / 1920.0f;
    private final float M_INFO_HUB_HEIGHT_PERCENTAGE = 1.0f - 612.0f / 1080.0f;
    Plane mvmtPlane;
    Vector3f selectedNodeCenterPos;
    Game.Selection_Mode selectionMode;
    public GameLoader(AssetManager assetManager, InputManager inputManager, Node guiNode, Node rootNode, BitmapFont guiFont, int M_WIDTH, int M_HEIGHT){
        this.assetManager=assetManager;
        this.inputManager=inputManager;
        this.rootNode=rootNode;
        this.guiNode=guiNode;
        this.guiFont=guiFont;
        this.M_HEIGHT=M_HEIGHT;
        this.M_WIDTH=M_WIDTH;
    }
    public Object[] loadGame() {
        loadPlanets();
        loadSun();
        loadShips();
        loadAmbientLight();
        loadSkybox();
        loadMovementPlane();
        loadPictures();
        loadText();
        addNodes();
        loadCursors();
        Object[] arr = {rootNode, guiNode, guiFont, inputManager, planetsNode, mPlanets, starsNode, mSuns, selectedShipsNode, loneShipsNode, loneShips, selectedFlotillasNode, flotillasNode, flotillas, flotillaBattles, selectedNodeCenterPos, mvmtPlane, selectionMode, mCursorSmiley, mPictureBoxSelect, mOverlay, mInfoHubText, mSelectionModeText};
        return arr;
    }
    private void loadSkybox()
    {
        Texture skybox_tex = assetManager.loadTexture("Textures/space_skybox.png");
        rootNode.attachChild(SkyFactory.createSky(assetManager, skybox_tex, skybox_tex, skybox_tex, skybox_tex, skybox_tex, skybox_tex));
    }

    private void loadAmbientLight()
    {
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.7f));
        rootNode.addLight(ambientLight);
    }


    private void loadMovementPlane()
    {
        mvmtPlane = new Plane(Vector3f.UNIT_Y, 0.0f);
    }
    private void loadCursors()
    {
        mCursorSmiley = (JmeCursor)assetManager.loadAsset("Textures/cursor_smiley.cur");
        mCursorSmiley.setxHotSpot(mCursorSmiley.getWidth() >> 1);
        mCursorSmiley.setyHotSpot(mCursorSmiley.getHeight() >> 1);
        inputManager.setMouseCursor(null);
    }

    private void loadPictures()
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

    private void loadText()
    {
        float fontSize = (guiFont.getCharSet().getRenderedSize() * ((float)M_WIDTH / (float)M_HEIGHT)) / (1920.0f / 1080.0f);
        mInfoHubText = new ScrollText(M_HEIGHT, fontSize, M_INFO_HUB_WIDTH_PERCENTAGE * M_WIDTH,
                M_INFO_HUB_HEIGHT_PERCENTAGE * M_HEIGHT, guiFont, guiNode);
        mSelectionModeText = new BitmapText(guiFont);
        mSelectionModeText.setSize(fontSize);
        mSelectionModeText.setColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 0.75f));
        mSelectionModeText.setText("Selection Mode: Ship (Press 'Tab' to switch)");
        mSelectionModeText.setLocalTranslation(M_WIDTH - mSelectionModeText.getLineWidth(), mSelectionModeText.getLineHeight(), 0.0f);
        guiNode.attachChild(mSelectionModeText);
    }
    private Planet generatePlanet(int index)
    {
        VisualType type=null;
        switch ((int)(Math.random()*5)){
            case(0): type=VisualType.LAVA_PLANET;
                break;
            case(1): type=VisualType.SUBEARTH_PLANET;
                break;
            case(2): type=VisualType.TERRESTRIAL_PLANET;
                break;
            case(3): type=VisualType.MININEPTUNE_PLANET;
                break;
            case(4): type=VisualType.GASGIANT_PLANET;
                break;
        }
        System.out.print(type.getRADIUS());
        int posNeg = (Math.random() < 0.5) ? -1 : 1;
        int orbitRadius = 15 + (25 * (index+1));
        float xPos = posNeg * (int)(Math.random() * orbitRadius);
        posNeg = (Math.random() < 0.5) ? -1 : 1;
        float zPos =  posNeg * (float)Math.sqrt(orbitRadius * orbitRadius - xPos * xPos);

        Planet p = new Planet(new Vector3f(xPos, 0.0f, zPos), null,null,null,"Planet " + Integer.toString(index),"no-one", planetsNode, assetManager, ColorRGBA.randomColor(),type);
        ArrayList<Firm> firms = new ArrayList<Firm>();
        int num = (int)(Math.random()*8);
        // Add firms to the firms list
        p.addFirms(firms);
        return p;
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
        VisualType type=null;
        switch ((int)(Math.random()*4)){
            case(0): type=VisualType.DWARF_STAR;
                break;
            case(1): type=VisualType.MAINSEQUENCE_STAR;
                break;
            case(2): type=VisualType.GIANT_STAR;
                break;
            case(3): type=VisualType.SUPERGIANT_STAR;
                break;
        }
        mSuns = new Star[1];
        mSuns[0] = new Star(Vector3f.ZERO,null,null,null,"StarX","no-one", starsNode,  assetManager, ColorRGBA.White,type);
        rootNode.addLight(mSuns[0].getPointLight());

    }
    private void loadShips()
    {
        selectedShipsNode = new Node("Selected Ships");
        selectedFlotillasNode = new Node("Selected Flotillas");
        selectionMode = Game.Selection_Mode.Ship_Selection;
        rootNode.attachChild(selectedShipsNode);
        selectedNodeCenterPos = new Vector3f();

        loneShipsNode = new Node("Lone Ships");

        double[] stats = new double[19];
        stats[BattleObject.BATTLE_STAT_MOVEMENT_SPEED] = 5.0;

        loneShips = new ArrayList<Ship>();

        for (int i = 0; i < 5; i++)
        {
            loneShips.add(new Ship(new Player(3), Ship.SHIP_TYPE.Fighter, "Fighter " + i, loneShipsNode, new Vector3f(0.0f, 0.0f, -(30 + i * 3)),
                    stats, 0, "Fighter " + i, 0, 0, 0.0));
            loneShips.get(i).getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(loneShips.get(i).getPlayer().getColor()));
        }

        rootNode.attachChild(loneShipsNode);

        ///////////////////////////////
        // Not Related /\ \/ //////////
        ///////////////////////////////

        flotillasNode = new Node("Flotillas Node");

        Ship[] ships1 = new Ship[25];
        for (int i = 0; i < ships1.length; i++)
        {
            ships1[i] = new Ship(new Player(0), Ship.SHIP_TYPE.Fighter, "Fighter " + i, flotillasNode, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            ships1[i].getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(ships1[i].getPlayer().getColor()));
        }

        Ship[] ships2 = new Ship[9];
        for (int i = 0; i < ships2.length; i++)
        {
            ships2[i] = new Ship(new Player(1), Ship.SHIP_TYPE.CapitalShip, "Capital Ship " + i, flotillasNode, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            ships2[i].getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(ships2[i].getPlayer().getColor()));
        }

        Ship[] ships3 = new Ship[25];
        for (int i = 0; i < ships3.length; i++)
        {
            ships3[i] = new Ship(new Player(2), Ship.SHIP_TYPE.Fighter, "Fighter " + i, flotillasNode, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            ships3[i].getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(ships3[i].getPlayer().getColor()));
        }

        flotillas = new ArrayList<Flotilla>();
        flotillas.add(new Flotilla(ships1, false, new Vector3f(-50.0f, 0.0f, 100.0f), "Flotilla 1"));
        flotillas.add(new Flotilla(ships2, false, new Vector3f(50.0f, 0.0f, 0.0f), "Flotilla 2"));
        flotillas.add(new Flotilla(ships3, false, new Vector3f(100.0f, 0.0f, 25.0f), "Flotilla 3"));

        rootNode.attachChild(flotillasNode);

        flotillaBattles = new ArrayList<FlotillaBattler>();
//        attackers = new ArrayList<ArrayList<Flotilla>>();
//        defenders = new ArrayList<Flotilla>();
    }
    private void addNodes()
    {
        rootNode.attachChild(selectedShipsNode);
        rootNode.attachChild(planetsNode);
        rootNode.attachChild(starsNode);
        rootNode.attachChild(loneShipsNode);
        rootNode.attachChild(flotillasNode);
    }

}
/*



 */