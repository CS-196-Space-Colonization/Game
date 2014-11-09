package com.spacecolonization.game;

import com.spacecolonization.graphics.spatialentities.Planet;
import com.spacecolonization.graphics.spatialentities.Star;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Plane;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import com.spacecolonization.graphics.GameModels;
import com.spacecolonization.military.vehicles.ships.Fighter;

/**
 * @author Joe Pagliuco
 */
public class Game extends SimpleApplication
{
    public static int M_WIDTH, M_HEIGHT;
    
    private Planet[] mPlanets;
    private Node mPlanetsNode;
    
    private Star mSun;
    
    private Fighter[] mFighters;
        
    private Plane mMovementPlane;
    
    private boolean mIsControlPressed;
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// START INITIALIZATION METHODS /////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void simpleInitApp()
    {
        M_WIDTH = settings.getWidth();
        M_HEIGHT = settings.getHeight();
        
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
        
        initializeKeys();
        
        Picture p = new Picture("P");
        p.setImage(assetManager, "Textures/box_select.png", true);
        p.setLocalTranslation(0, 0, 0);
        p.setWidth(200);
        p.setHeight(200);
        guiNode.attachChild(p);
    }
    
    private void adjustCameraSettings()
    {
        cam.setLocation(new Vector3f(0.0f, 50.0f, -30.0f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setFrustumFar(2000.0f);
        
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
        mPlanetsNode = new Node();       
        mPlanets = new Planet[10];
        
        for (int i = 0; i < mPlanets.length; i++)
        {
            mPlanets[i] = generatePlanet(i);
            rootNode.attachChild(mPlanets[i].getModel());
            mPlanetsNode.attachChild(mPlanets[i].getModel());
        }
        rootNode.attachChild(mPlanetsNode);
    }
    
    private void loadSun()
    {
        mSun = new Star(Vector3f.ZERO, "StarX", assetManager, 4.0f);
        rootNode.attachChild(mSun.getModel());
        rootNode.addLight(mSun.getPointLight());
    }
    
    private void loadMovementPlane()
    {
        mMovementPlane = new Plane(Vector3f.UNIT_Y, 0.0f);
    }
    
    private void loadShips()
    {
        mFighters = new Fighter[5];
        
        for (int i = 0; i < mFighters.length; i++)
        {
            mFighters[i] = new Fighter(new Vector3f(0.0f, 0.0f, -(10 + i * 5)), "Ship " + i);
            rootNode.attachChild(mFighters[i].getModel());
        }
    }
    
    private void initializeKeys()
    {
        mIsControlPressed = false;
        
        inputManager.addMapping("Control", new KeyTrigger(KeyInput.KEY_LCONTROL));
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(mMouseActionListener, new String[] { "Control", "Left Click" } );
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
            if (name.equals("Control"))
                mIsControlPressed = isPressed;
            
            if (name.equals("Left Click") && mIsControlPressed && isPressed)
            {
                Ray r = getMouseRay();
                Vector3f newPos = new Vector3f();
                r.intersectsWherePlane(mMovementPlane, newPos);
                mFighters[0].setTarget(newPos);
            }
        }
    };
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// END LISTENERS ////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void simpleUpdate(float tpf)
    {
// Check collision's with planets...
        Geometry closestCollision = checkClosestCollisionWithMouse(mPlanetsNode);
        if (closestCollision != null)
        {
            String name = closestCollision.getName();
            for (int i = 0; i < mPlanetsNode.getQuantity(); i++)
            {
                if (name.equals(mPlanetsNode.getChild(i).getName()))
                    System.out.println(mPlanets[i].toString());
            }
        }
        
// Update ships...
        for (int i = 0; i < mFighters.length; i++)
            mFighters[i].update(tpf);
    }
    
    private Geometry checkClosestCollisionWithMouse(Node node)
    {
        CollisionResults results = new CollisionResults();
        
        Ray ray = getMouseRay();
        
        mPlanetsNode.collideWith(ray, results);
        if (results.getClosestCollision() == null)
            return null;
        else
            return results.getClosestCollision().getGeometry();
    }
    
    private Ray getMouseRay()
    {
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(
            new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(
            new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        return new Ray(click3d, dir);
    }
}