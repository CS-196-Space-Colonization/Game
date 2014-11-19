package com.thecolony.tractus.graphics.threedmovement.game;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.cursors.plugins.JmeCursor;
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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.GameModels;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities.Planet;
import com.thecolony.tractus.graphics.threedmovement.drawableobjects.spatialentities.Star;
import com.thecolony.tractus.graphics.threedmovement.military.ships.Ship;

/**
 * @author Joe Pagliuco
 */
public class Game extends SimpleApplication
{
    public static int M_WIDTH, M_HEIGHT;
    
    private Planet[] mPlanets;
    private Node mPlanetsNode;
    
    private Star mSun;
    
    private Ship[] mFighters;
    private Node mFightersNode;
    
    private Node mSelectedObjectModels;
        
    private Plane mMovementPlane;
    
    private boolean mIsShiftPressed;
    private boolean mIsMPressed;
    private boolean mIsRPressed;
    private boolean mIsBPressed;
    
    private JmeCursor mCursorSmiley;
    
    private Picture mPictureBoxSelect;
    
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
        
        GameModels.loadModels(assetManager);
        
        adjustCameraSettings();
        
        loadPlanets();
        loadSun();
        loadShips();
        
        loadAmbientLight();
        
        loadSkybox();
        
        loadMovementPlane();
        
        initializeListeners();
        
        mSelectedObjectModels = new Node("Selected Object Models");
        
        loadPictures();
        
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
        Texture skybox_tex2 = assetManager.loadTexture("Textures/space_skybox_2.png");
        rootNode.attachChild(SkyFactory.createSky(assetManager, skybox_tex, skybox_tex, skybox_tex2, skybox_tex, skybox_tex, skybox_tex));
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
        mFighters = new Ship[30];
        mFightersNode = new Node("Fighters Node");
        
        for (int i = 0; i < mFighters.length; i++)
        {
            mFighters[i] = new Ship(Ship.SHIP_TYPE.Fighter, new Vector3f(0.0f, 0.0f, -(30 + i * 3)), "Fighter " + i);
            mFightersNode.attachChild(mFighters[i].getModel());
        }
    }
    
    private void initializeListeners()
    {
        mIsShiftPressed = false;
        mIsMPressed = false;
        mIsRPressed = false;
        mIsBPressed = false;
        
        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(mMouseActionListener, new String[] { "Right Click", "Left Click" } );
        
        inputManager.addMapping("Shift", new KeyTrigger(KeyInput.KEY_LSHIFT), new KeyTrigger(KeyInput.KEY_RSHIFT));
        inputManager.addMapping("M", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("R", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("B", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addListener(mKeyboardActionListener, new String[] { "Shift", "M", "R", "B" } );
    }
    
    private void addNodes()
    {
        rootNode.attachChild(mSelectedObjectModels);
        rootNode.attachChild(mPlanetsNode);
        rootNode.attachChild(mFightersNode);
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
            if (name.equals("Right Click") && !mIsShiftPressed && isPressed)
            {
                // Check collision with ships
                int closestCollisionIndex = getClosestCollisionWithMouseIndex(mFightersNode);
                if (closestCollisionIndex != -1) // A ship has been selected
                {
                    clearSelectedObjects();                    
                    addSelectedObject(mFighters[closestCollisionIndex], closestCollisionIndex);
                }
                else // Nothing selected, deselect all
                    clearSelectedObjects();
            }
            
            if (name.equals("Right Click") && mIsShiftPressed && isPressed)
            {
                int closestCollisionIndex = getClosestCollisionWithMouseIndex(mFightersNode);
                if (closestCollisionIndex != -1)
                {
                    // Check to see if object is already selected...
                    if (mFighters[closestCollisionIndex].isSelected())
                    {
                        // Deselect it...
                        mSelectedObjectModels.detachChildNamed(
                                (String)mFighters[closestCollisionIndex].getModel().getUserData("Type") + 
                                Integer.toString((Integer)mFighters[closestCollisionIndex].getModel().getUserData("ID")));
                        mFighters[closestCollisionIndex].setIsSelected(false);
                    }
                    else
                    {
                        // Select it...
                        mSelectedObjectModels.attachChild(addSelectedObjectModel(mFighters[closestCollisionIndex].getModel(), closestCollisionIndex));
                        mFighters[closestCollisionIndex].setIsSelected(true);
                    }
                }
            }
            
            if (name.equals("Left Click") && isPressed)
            {
                if (mIsMPressed || mIsRPressed)
                {
                    Vector3f targetPoint = getMouseRayIntersectionPoint();
                    for (int i = 0; i < mSelectedObjectModels.getQuantity(); i++)
                    {
                        int index = mSelectedObjectModels.getChild(i).getUserData("Array Index");
                        mFighters[index].setTarget(targetPoint, mIsMPressed);
                    }
                    mIsMPressed = mIsRPressed = false;
                    inputManager.setMouseCursor(null);
                }
                else if (mIsBPressed)
                {
                    Vector2f cursorPos = inputManager.getCursorPosition();
                    mPictureBoxSelect.setUserData("Initial Position", cursorPos.clone());
                    guiNode.attachChild(mPictureBoxSelect);
                }
            }
            if (name.equals("Left Click") && !isPressed)
            {
                if (mIsBPressed)
                {
                    // Deselect all
                    for (int i = 0; i < mSelectedObjectModels.getQuantity(); i++)
                           ((Spatial)(mSelectedObjectModels.getChild(i).getUserData("Selected Object"))).setUserData("Selected", false);
                    mSelectedObjectModels.detachAllChildren();

                    // Create big ass selector cube...
                    Vector3f min = getMouseRayIntersectionPoint();
                    Vector3f max = Vector3f.ZERO;
                    Ray r = createRayFromPoint((Vector2f)mPictureBoxSelect.getUserData("Initial Position"));
                    r.intersectsWherePlane(mMovementPlane, max);
                    Box bigAssSelectorCube = new Box(min, max);

                    // Loop through ships to see if they're in the selected area...
                    for (int i = 0; i < mFighters.length; i++)
                    {
                        BoundingVolume shipVolume = mFighters[i].getModel().getWorldBound();
                        if (bigAssSelectorCube.getBound().intersects(shipVolume) || bigAssSelectorCube.getBound().contains(shipVolume.getCenter()))
                        {
                            mSelectedObjectModels.attachChild(addSelectedObjectModel(mFighters[i].getModel(), i));
                            mFighters[i].setIsSelected(true);
                        }
                    }

                    guiNode.detachChild(mPictureBoxSelect);
                    mIsBPressed = false;
                    inputManager.setMouseCursor(null);
                    flyCam.setEnabled(true);
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
            else if (name.equals("M") && isPressed)
            {
                mIsMPressed = !mIsMPressed;
                mIsRPressed = false;
                mIsBPressed = false;
            }
            else if (name.equals("R") && isPressed)
            {
                mIsMPressed = false;
                mIsRPressed = !mIsRPressed;
                mIsBPressed = false;
            }
            else if (name.equals("B") && isPressed)
            {
                mIsMPressed = false;
                mIsRPressed = false;
                mIsBPressed = !mIsBPressed;
                flyCam.setEnabled(!flyCam.isEnabled());
            }
            
            if (mIsMPressed || mIsRPressed || mIsBPressed)
                inputManager.setMouseCursor(mCursorSmiley);
            else
                inputManager.setMouseCursor(null);
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
        if (mIsBPressed)
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
        
// Rotate selected object models
        for (int i = 0; i < mSelectedObjectModels.getQuantity(); i++)
            mSelectedObjectModels.getChild(i).rotate(0, tpf*2, 0);
        
// Update ships...
        for (int i = 0; i < mFighters.length; i++)
            mFighters[i].update(tpf);
        
// Update selected object models...
        for (int i = 0; i < mSelectedObjectModels.getQuantity(); i++)
        {
            Spatial s = mSelectedObjectModels.getChild(i);
            float sY = s.getLocalTranslation().getY();
            Spatial o = (Spatial)s.getUserData("Selected Object");
            s.setLocalTranslation(o.getLocalTranslation().x, sY, o.getLocalTranslation().z);
        }
    }
    
    /**
     * Finds closest collision with node from cursor ray.
     * @param node The node to check collisions with.
     * @return The geometry of the closest collision.
     */
    private Geometry getClosestCollisionWithMouseGeometry(Node node)
    {
        CollisionResults results = new CollisionResults();
        
        Ray ray = getMouseRay();
        
        node.collideWith(ray, results);
        if (results.getClosestCollision() == null)
            return null;
        else
            return results.getClosestCollision().getGeometry();
    }
    
    /**
     * Finds closest collision with node from cursor ray.
     * @param node The node to check collisions with.
     * @return The index (ID minus one) of the closest collision 
     * from the cursor ray.
     */
    private int getClosestCollisionWithMouseIndex(Node node)
    {
        Geometry g = getClosestCollisionWithMouseGeometry(node);
        
        if (g == null)
            return -1;
        
        int id = g.getParent().getUserData("ID");
        
        return id - 1;
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
     * @param index The index of this ship in it's own array.
     */
    private void addSelectedObject(Ship ship, int index)
    {
         mSelectedObjectModels.attachChild(addSelectedObjectModel(ship.getModel(), index));
         ship.setIsSelected(true);
    }
    
    /**
     * Creates a model to hover the selected object.
     * @param selectedObject The selected object.
     * @param index The index of the ship in it's own array.
     * @return 
     */
    private Spatial addSelectedObjectModel(Spatial selectedObject, int index)
    {
        Spatial s = GameModels.getSelectedObjectModel2();
        
        BoundingBox selectedObjectBound = (BoundingBox)selectedObject.getWorldBound();
        BoundingBox selectedModelBound = (BoundingBox)s.getWorldBound();
        float xScale = selectedObjectBound.getXExtent() / selectedModelBound.getXExtent();
        float zScale = selectedObjectBound.getZExtent() / selectedModelBound.getZExtent();
        float scale = Math.max(xScale, zScale);
        
        s.scale(scale, 1.0f, scale);
        s.setLocalTranslation(selectedObject.getLocalTranslation().add(new Vector3f(0.0f, -selectedModelBound.getYExtent(), 0.0f)));
        
        s.setName((String)selectedObject.getUserData("Type") + Integer.toString((Integer)selectedObject.getUserData("ID")));
        s.setUserData("Selected Object", selectedObject);
        s.setUserData("Array Index", index);
        return s;
    }
    
    /**
     * Clears all of the data in the selected objects node.
     */
    private void clearSelectedObjects()
    {
        for (int i = 0; i < mSelectedObjectModels.getQuantity(); i++)
            ((Spatial)(mSelectedObjectModels.getChild(i).getUserData("Selected Object"))).setUserData("Selected", false);
                    
        mSelectedObjectModels.detachAllChildren();
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// END UPDATE METHODS ///////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
}