package SpaceColonization;

import SpaceColonization.Graphics.SpatialEntities.Planet;
import SpaceColonization.Graphics.SpatialEntities.Star;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;

/**
 * @author Joe Pagliuco
 */
public class Game extends SimpleApplication
{
    public static int M_WIDTH, M_HEIGHT;
    
    private Planet[] mPlanets;
    private Node mPlanetsNode;
    
    private Star mSun;
    
    private Picture mHUDbkgr;
    
    @Override
    public void simpleInitApp()
    {
        M_WIDTH = settings.getWidth();
        M_HEIGHT = settings.getHeight();
        
        setDisplayFps(true);
        setDisplayStatView(false);
        
        adjustCameraSettings();
        
        loadPlanets();
        loadSun();
        
        loadAmbientLight();
        
        loadSkybox();
        
        loadPicture();
    }
    
    private void adjustCameraSettings()
    {
        cam.setLocation(new Vector3f(0.0f, 200.0f, 0.0f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setFrustumFar(2000.0f);
        
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(50.0f);
    }
    
    private Planet generatePlanet(int index)
    {
        int radius = (int)(Math.random() * 10.0) + 1;
        int posNeg = (Math.random() < 0.5) ? -1 : 1;
        int orbitRadius = 65 + (35 * (index+1));
        float xPos = posNeg * (int)(Math.random() * orbitRadius);
        posNeg = (Math.random() < 0.5) ? -1 : 1;
        float zPos =  posNeg * (float)Math.sqrt(orbitRadius * orbitRadius - xPos * xPos);
        
        return new Planet(new Vector3f(xPos, 0.0f, zPos), "Planet " + Integer.toString(index), assetManager, radius, ColorRGBA.randomColor());
    }
    
    private void loadSkybox()
    {
        rootNode.attachChild(assetManager.loadModel("Scenes/Skybox.j3o"));
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
        mSun = new Star(Vector3f.ZERO, "StarX", assetManager, 40.0f);
        rootNode.attachChild(mSun.getModel());
        rootNode.addLight(mSun.getPointLight());
    }
    
    private void loadPicture()
    {
        Picture p = new Picture("Picture p");
        p.setImage(assetManager, "Textures/sun.jpg", true);
        p.setPosition(0, 0);
        p.setWidth(M_WIDTH / 4);
        p.setHeight(M_HEIGHT / 4);
        //guiNode.attachChild(p);
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// END INITIALIZATION METHODS ///////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void simpleUpdate(float tpf)
    {
        Planet p = checkMouseHoverOnPlanets();
        if (p != null)
        {
            System.out.println(p);
        }
    }
    
    private Planet checkMouseHoverOnPlanets()
    {
        CollisionResults results = new CollisionResults();
        
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(
            new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(
            new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);
        
        mPlanetsNode.collideWith(ray, results);
        if (results.getClosestCollision() == null)
            return null;
        
        String ccName = results.getClosestCollision().getGeometry().getName();
        for (int i = 0; i < mPlanetsNode.getQuantity(); i++)
        {
            if (ccName.equals(mPlanetsNode.getChild(i).getName()))
                return mPlanets[i];
        }
        return null;
    }
}