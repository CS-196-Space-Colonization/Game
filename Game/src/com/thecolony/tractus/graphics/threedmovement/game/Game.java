package com.thecolony.tractus.graphics.threedmovement.game;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.GameGraphics;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities.Planet;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities.Star;
import com.thecolony.tractus.player.ai.battle.BattleObject;
import com.thecolony.tractus.player.ai.battle.ships.Flotilla;
import com.thecolony.tractus.player.ai.battle.ships.Ship;
import java.util.ArrayList;

/**
 * @author Joe Pagliuco
 */
public class Game extends SimpleApplication
{
    public static int M_WIDTH, M_HEIGHT;
    private final float M_INFO_HUB_WIDTH_PERCENTAGE = 10.0f / 1920.0f;
    private final float M_INFO_HUB_HEIGHT_PERCENTAGE = 1.0f - 612.0f / 1080.0f;
    
    private enum Selection_Mode { Ship_Selection, Flotilla_Selection };
    private Selection_Mode selectionMode;
    
    private Planet[] mPlanets;
    private Node mPlanetsNode;
    
    private Star mSun;
    
    private ArrayList<Ship> ships;
    
    private ArrayList<Flotilla> flotillas;
    
    private Node mSelectedShipsNode;
    private Node mSelectedFlotillasNode;
        
    private Plane mMovementPlane;
    
    private boolean mIsShiftPressed;
    private boolean mIsMToggleOn;
    private boolean mIsRToggleOn;
    private boolean mIsBToggleOn;
    private boolean mIsAToggleOn;
    
    private JmeCursor mCursorSmiley;
    
    private Picture mPictureBoxSelect;
    private Picture mOverlay;
    
    private BitmapText mInfoHubText;
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// START INITIALIZATION METHODS /////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void simpleInitApp()
    {
        M_WIDTH = settings.getWidth();
        M_HEIGHT = settings.getHeight();
        
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_HIDE_STATS);
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_MEMORY);
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_CAMERA_POS);
        setDisplayFps(true);
        setDisplayStatView(false);
        
        GameGraphics.loadGraphics(assetManager);
        
        adjustCameraSettings();
        
        loadPlanets();
        loadSun();
        loadShips();
        
        loadAmbientLight();
        
        loadSkybox();
        
        loadMovementPlane();
        
        initializeListeners();
        
        loadPictures();
        
        loadText();
        
        addNodes();
        
        loadCursors();
    }
    
    private void adjustCameraSettings()
    {
        cam.setLocation(new Vector3f(50.0f, 50.0f, -30.0f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setFrustumPerspective((float)Math.toDegrees(FastMath.PI / 4.0f), (float)M_WIDTH / (float)M_HEIGHT, 0.1f, 2000.0f);
        
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(50.0f);
    }
    
    private Planet generatePlanet(int index)
    {
        float radius = (float)(Math.random() * 2);
        int posNeg = (Math.random() < 0.5) ? -1 : 1;
        int orbitRadius = 15 + (10 * (index+1));
        float xPos = posNeg * (int)(Math.random() * orbitRadius);
        posNeg = (Math.random() < 0.5) ? -1 : 1;
        float zPos =  posNeg * (float)Math.sqrt(orbitRadius * orbitRadius - xPos * xPos);
        
        return new Planet(new Vector3f(xPos, 0.0f, zPos), "Planet " + Integer.toString(index), assetManager, radius, ColorRGBA.randomColor());
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
    
    private void loadPlanets()
    {
        mPlanetsNode = new Node("Planets Node");
        mPlanets = new Planet[10];
        
        for (int i = 0; i < mPlanets.length; i++)
        {
            mPlanets[i] = generatePlanet(i);
//            mPlanetsNode.attachChild(mPlanets[i].getModel());
        }
        rootNode.attachChild(mPlanetsNode);
    }
    
    private void loadSun()
    {
        mSun = new Star(Vector3f.ZERO, "StarX", assetManager, 10.0f);
        rootNode.attachChild(mSun.getModel());
        rootNode.addLight(mSun.getPointLight());
    }
    
    private void loadMovementPlane()
    {
        mMovementPlane = new Plane(Vector3f.UNIT_Y, 0.0f);
    }
    
    private void loadShips()
    {
        mSelectedShipsNode = new Node("Selected Ships");
        mSelectedFlotillasNode = new Node("Selected Flotillas");
        selectionMode = Selection_Mode.Ship_Selection;
        rootNode.attachChild(mSelectedShipsNode);
        
        double[] stats = new double[19];
        stats[BattleObject.BATTLE_STAT_MOVEMENT_SPEED] = 5.0;
        
        ships = new ArrayList<Ship>();
        
        for (int i = 0; i < 5; i++)
        {
            ships.add(new Ship(Ship.SHIP_TYPE.Fighter, "Fighter " + i, new Vector3f(0.0f, 0.0f, -(30 + i * 3)), 
                    stats, 0, "Fighter " + i, 0, 0, 0.0));
            
            rootNode.attachChild(ships.get(i).getDrawableObject3d().getModel());
        }
        
        ///////////////////////////////
        // Not Related /\ \/ //////////
        ///////////////////////////////

        Ship[] ships1 = new Ship[10];
        for (int i = 0; i < ships1.length; i++)
        {
            ships1[i] = new Ship(Ship.SHIP_TYPE.Fighter, "Fighter " + i, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            rootNode.attachChild(ships1[i].getDrawableObject3d().getModel());
        }
        
        Ship[] ships2 = new Ship[9];
        for (int i = 0; i < ships2.length; i++)
        {
            ships2[i] = new Ship(Ship.SHIP_TYPE.CapitalShip, "Capital Ship " + i, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            rootNode.attachChild(ships2[i].getDrawableObject3d().getModel());
        }
        
        flotillas = new ArrayList<Flotilla>();
        flotillas.add(new Flotilla(ships1, false, new Vector3f(50.0f, 0.0f, 0.0f), "Flotilla 1"));
        flotillas.add(new Flotilla(ships2, false, new Vector3f(50.0f, 0.0f, 50.0f), "Flotilla 2"));
    }
    
    private void initializeListeners()
    {
        mIsShiftPressed = false;
        mIsMToggleOn = false;
        mIsRToggleOn = false;
        mIsBToggleOn = false;
        mIsAToggleOn = false;
        
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(mMouseActionListener, new String[] { "Right Click", "Left Click" } );
        
        inputManager.addMapping("Shift", new KeyTrigger(KeyInput.KEY_LSHIFT), new KeyTrigger(KeyInput.KEY_RSHIFT));
        inputManager.addMapping("M", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("R", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("B", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("A", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Tab", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addListener(mKeyboardActionListener, new String[] { "Shift", "M", "R", "B", "A", "Tab" } );
    }
    
    private void addNodes()
    {       
        rootNode.attachChild(mSelectedShipsNode);
        rootNode.attachChild(mPlanetsNode);
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
        mInfoHubText = new BitmapText(guiFont);
        mInfoHubText.setSize(fontSize);
        mInfoHubText.setColor(ColorRGBA.White);
        mInfoHubText.setText("");
        mInfoHubText.setLocalTranslation(M_INFO_HUB_WIDTH_PERCENTAGE * M_WIDTH, M_INFO_HUB_HEIGHT_PERCENTAGE * M_HEIGHT, 0.0f);
        guiNode.attachChild(mInfoHubText);
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// END INITIALIZATION METHODS ///////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// START LISTENERS //////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private ActionListener mMouseActionListener = new ActionListener()
    {
        public void onAction(String name, boolean isPressed, float tpf)
        {
            if (selectionMode == Selection_Mode.Ship_Selection)
            {
                if (name.equals("Right Click") && isPressed)
                {
                    if (!mIsShiftPressed)
                        clearSelectedObjects();
                    
                    Ray r = getMouseRay();
                    
                    boolean selectedSomething = false;
                    for (int i = 0; i < ships.size(); i++)
                    {
                        Ship s = ships.get(i);
                        selectedSomething = ((BoundingBox)s.getDrawableObject3d().getModel().getWorldBound()).intersects(r);
                        if (selectedSomething)
                        {
                            addSelectedObject(s);
                            break;
                        }
                    }
                    if (!selectedSomething)
                        clearSelectedObjects();
                }
                
                if (name.equals("Left Click") && isPressed)
                {
                    if (mIsMToggleOn || mIsRToggleOn)
                    {
                        for (int i = 0; i < ships.size(); i++)
                        {
                            Ship s = ships.get(i);
                            if (s.isSelected())
                            {
                                Vector3f t = getMouseRayIntersectionPoint();
                                s.setTargetPoint(t, mIsMToggleOn);
                            }
                        }
                        
                        mIsMToggleOn = mIsRToggleOn = false;
                        inputManager.setMouseCursor(null);
                    }
                    else if (mIsBToggleOn)
                    {
                        Vector2f cursorPos = inputManager.getCursorPosition();
                        mPictureBoxSelect.setUserData("Initial Position", cursorPos.clone());
                        guiNode.attachChild(mPictureBoxSelect);
                    }
                }
                else if (name.equals("Left Click") && !isPressed)
                {
                    if (mIsBToggleOn)
                    {
                        clearSelectedObjects();
                        
                        // Create big ass selector cube...
                        Vector3f min = getMouseRayIntersectionPoint();
                        Vector3f max = Vector3f.ZERO;
                        Ray r = createRayFromPoint((Vector2f)mPictureBoxSelect.getUserData("Initial Position"));
                        r.intersectsWherePlane(mMovementPlane, max);
                        Box bigAssSelectorCube = new Box(min, max);

                        // Loop through ships to see if they're in the selected area...
                        for (int i = 0; i < ships.size(); i++)
                        {
                            BoundingVolume shipBound = ships.get(i).getDrawableObject3d().getModel().getWorldBound();
                            if (bigAssSelectorCube.getBound().intersects(shipBound))
                                addSelectedObject(ships.get(i));
                        }

                        guiNode.detachChild(mPictureBoxSelect);
                        mIsBToggleOn = false;
                        inputManager.setMouseCursor(null);
                        flyCam.setEnabled(true);
                    }
                }
            }
            else if (selectionMode == Selection_Mode.Flotilla_Selection)
            {
                if (name.equals("Right Click") && isPressed)
                {
                    if (!mIsShiftPressed)
                        clearSelectedObjects();
                    
                    Ray r = getMouseRay();
                    
                    boolean selectedSomething = false;
                    for (int i = 0; i < flotillas.size(); i++)
                    {
                        Flotilla f = flotillas.get(i);
                        selectedSomething = f.getBoundingBox().intersects(r);
                        if (selectedSomething)
                        {
                            addSelectedObject(f);
                            break;
                        }
                    }
                    if (!selectedSomething)
                        clearSelectedObjects();
                }
                
                if (name.equals("Left Click") && isPressed)
                {
                    if (mIsMToggleOn || mIsRToggleOn)
                    {
                        for (int i = 0; i < flotillas.size(); i++)
                        {
                            Flotilla f = flotillas.get(i);
                            if (f.isSelected())
                            {
                                Vector3f t = getMouseRayIntersectionPoint();
                                f.setTargetPoint(t, mIsMToggleOn);
                            }
                        }
                        
                        mIsMToggleOn = mIsRToggleOn = false;
                        inputManager.setMouseCursor(null);
                    }
                }
            }
        }
    };
    
    private ActionListener mKeyboardActionListener = new ActionListener()
    {
        public void onAction(String name, boolean isPressed, float tpf)
        {
            if (name.equals("Shift"))
                mIsShiftPressed = isPressed;
            
            
            if (name.equals("M") && isPressed)
            {
                mIsMToggleOn = !mIsMToggleOn;
                mIsRToggleOn = false;
                mIsBToggleOn = false;
                mIsAToggleOn = false;
            }
            else if (name.equals("R") && isPressed)
            {
                mIsMToggleOn = false;
                mIsRToggleOn = !mIsRToggleOn;
                mIsBToggleOn = false;
                mIsAToggleOn = false;
            }
            else if (name.equals("B") && isPressed)
            {
                mIsMToggleOn = false;
                mIsRToggleOn = false;
                mIsBToggleOn = !mIsBToggleOn;
                mIsAToggleOn = false;
                
                flyCam.setEnabled(!flyCam.isEnabled());
            }
            else if (name.equals("A") && isPressed)
            {
                mIsMToggleOn = false;
                mIsRToggleOn = false;
                mIsBToggleOn = false;
                mIsAToggleOn = !mIsAToggleOn;
            }
            
            
            if (mIsMToggleOn || mIsRToggleOn || mIsBToggleOn || mIsAToggleOn)
                inputManager.setMouseCursor(mCursorSmiley);
            else
                inputManager.setMouseCursor(null);
            
            
            if (name.equals("Tab") && isPressed)
            {
                System.out.print("Selection mode switched to ");
                
                if (selectionMode == Selection_Mode.Ship_Selection)
                {
                    selectionMode = Selection_Mode.Flotilla_Selection;
                    rootNode.detachChild(mSelectedShipsNode);
                    rootNode.attachChild(mSelectedFlotillasNode);
                    System.out.println("flotilla selection");
                }
                else if (selectionMode == Selection_Mode.Flotilla_Selection)
                {
                    selectionMode = Selection_Mode.Ship_Selection;
                    rootNode.detachChild(mSelectedFlotillasNode);
                    rootNode.attachChild(mSelectedShipsNode);
                    System.out.println("ship selection");
                }
                
                mIsShiftPressed = false;
                mIsMToggleOn = false;
                mIsRToggleOn = false;
                mIsBToggleOn = false;
                mIsAToggleOn = false;
                inputManager.setMouseCursor(null);
            }
        }
    };
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// END LISTENERS ////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// START UPDATE METHODS /////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void simpleUpdate(float tpf)
    {
// Update box select picture as needed...
        if (mIsBToggleOn)
        {
            Vector2f initPos = mPictureBoxSelect.getUserData("Initial Position");
            Vector2f cursorPos = inputManager.getCursorPosition();
            if (initPos.x < cursorPos.x && initPos.y > cursorPos.y)
                mPictureBoxSelect.setPosition(initPos.x, cursorPos.y);
            else if (initPos.x > cursorPos.x && initPos.y < cursorPos.y)
                mPictureBoxSelect.setPosition(cursorPos.x, initPos.y);
            else if (initPos.x > cursorPos.x && initPos.y > cursorPos.y)
                mPictureBoxSelect.setPosition(cursorPos.x, cursorPos.y);
            else
                mPictureBoxSelect.setPosition(initPos.x, initPos.y);
            
            mPictureBoxSelect.setWidth(Math.abs(cursorPos.x - initPos.x));
            mPictureBoxSelect.setHeight(Math.abs(initPos.y - cursorPos.y));
        }
        
        // Update ships...
        for (int i = 0; i < ships.size(); i++)
            ships.get(i).update(tpf);
        // Update flotillas...
        for (int i = 0; i < flotillas.size(); i++)
            flotillas.get(i).update(tpf);
        
        
        
        // Check if mouse hovering over anything to update info hub...
        Ray r = getMouseRay();
        boolean somethingSelected = false;
        if (selectionMode == Selection_Mode.Ship_Selection)
        {
            for (int i = 0; i < ships.size(); i++)
            {
                somethingSelected = ships.get(i).getDrawableObject3d().getModel().getWorldBound().intersects(r);
                if (somethingSelected)
                {
                    mInfoHubText.setText(ships.get(i).getName() + "\nNew Line?");
                    break;
                }
            }
            if (!somethingSelected)
            {
                for (int i = 0; i < flotillas.size(); i++)
                {
                    Flotilla f = flotillas.get(i);
                    for (int j = 0; j < f.getFlotilla().length; j++)
                    {
                        somethingSelected = f.getFlotilla()[j].getDrawableObject3d().getModel().getWorldBound().intersects(r);
                        if (somethingSelected)
                        {
                            mInfoHubText.setText(f.getFlotilla()[j].getName() + "\nNew Line?");
                            break;
                        }
                    }
                    if (somethingSelected)
                        break;
                }
            }
        }
        else if (selectionMode == Selection_Mode.Flotilla_Selection)
        {
            for (int i = 0; i < flotillas.size(); i++)
            {
                somethingSelected = flotillas.get(i).getBoundingBox().intersects(r);
                if (somethingSelected)
                {
                    mInfoHubText.setText(flotillas.get(i).getName() + "\nNew Line?");
                    break;
                }
            }
        }
        if (!somethingSelected)
            mInfoHubText.setText("");
    }
    
    /**
     * Creates a ray based off the 2d coordinate.
     * @param point The screen coordinates that make the base of the ray.
     * @return The created ray.
     */
    private Ray createRayFromPoint(Vector2f point)
    {
        Vector2f point2d = point;
        Vector3f point3d = cam.getWorldCoordinates(new Vector2f(point2d.x, point2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(new Vector2f(point2d.x, point2d.y), 1f).subtractLocal(point3d).normalizeLocal();
        return new Ray(point3d, dir);
    }
    
    /**
     * @return A ray from the current cursor position.
     */
    private Ray getMouseRay()
    {
        return createRayFromPoint(inputManager.getCursorPosition());
    }
    
    /**
     * @return The spot where the mouse intersects the plane at y = 0.
     */
    private Vector3f getMouseRayIntersectionPoint()
    {
        Ray r = getMouseRay();
        Vector3f v = new Vector3f();
        r.intersectsWherePlane(mMovementPlane, v);
        return v;
    }
    
    /**
     * Adds an object to the selected object node.
     * @param ship The reference to the ship that has been selected.
     */
    private void addSelectedObject(Ship ship)
    {
        mSelectedShipsNode.attachChild(ship.getWireBoxGeometry());
        
        ship.setIsSelected(true);
    }
    
    public void addSelectedObject(Flotilla flotilla)
    {        
        mSelectedFlotillasNode.attachChild(flotilla.getWireBoxGeometry());
        
        flotilla.setIsSelected(true);
    }
    
    /**
     * Clears all of the data in the selected objects node.
     */
    private void clearSelectedObjects()
    {
        if (selectionMode == Selection_Mode.Ship_Selection)
        {            
            for (int i = 0; i < ships.size(); i++)
                ships.get(i).setIsSelected(false);
            
            mSelectedShipsNode.detachAllChildren();
        }
        else if (selectionMode == Selection_Mode.Flotilla_Selection)
        {
            for (int i = 0; i < flotillas.size(); i++)
                flotillas.get(i).setIsSelected(false);
        
            mSelectedFlotillasNode.detachAllChildren();
        }
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// END UPDATE METHODS ///////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
}