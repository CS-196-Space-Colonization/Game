package mygame;

import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class used to represent the camera used in Tractus.
 * @author Joe Pagliuco
 */
public class TractusCamera
{
    private final int M_MOVEMENT_PIXEL_DISPLACEMENT = 10;
    
    private final int M_MOVE_LEFT    = 0;
    private final int M_MOVE_RIGHT   = 1;
    private final int M_MOVE_BACK    = 2;
    private final int M_MOVE_FORWARD = 3;
    
    private Camera mCamera;
    private InputManager mInputManager;
    
    private int mScreenWidth;
    private int mScreenHeight;
    
    private float mMovementSpeed;
    
    private float mFieldOfView;
    
    public TractusCamera(Camera cam, InputManager inputManager, int screenWidth, int screenHeight)
    {
        mCamera = cam;
        mInputManager = inputManager;
        
        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;
        
        mMovementSpeed = 10.0f;
        
        mFieldOfView = FastMath.PI / 4.0f;
        updateCamera();
        
        mInputManager.getCursorPosition().set(screenWidth >> 1, screenHeight >> 1);
        
        initializeListeners();
    }
    
    private void initializeListeners()
    {
        mInputManager.addMapping("Scroll Down", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        mInputManager.addMapping("Scroll Up", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        
        mInputManager.addListener(mMouseAnalogListener, new String[] { "Scroll Down", "Scroll Up" } );
    }
    
    private AnalogListener mMouseAnalogListener = new AnalogListener()
    {
        public void onAnalog(String name, float value, float tpf)
        {
            if (name.equals("Scroll Down"))
            {
                mFieldOfView += tpf;
                updateCamera();
            }
            else if (name.equals("Scroll Up"))
            {
                mFieldOfView -= tpf;
                updateCamera();
            }
        }        
    };
    
    public void setMovementSpeed(float speed)
    {
        mMovementSpeed = speed;
    }
    
    public void update(float deltaTime)
    {
        System.out.println(mInputManager.getCursorPosition().x + ", " + mInputManager.getCursorPosition().y);
        
        if (mInputManager.getCursorPosition().x < M_MOVEMENT_PIXEL_DISPLACEMENT)
            moveCamera(M_MOVE_LEFT, deltaTime);
        else if (mInputManager.getCursorPosition().x > mScreenWidth - M_MOVEMENT_PIXEL_DISPLACEMENT)
            moveCamera(M_MOVE_RIGHT, deltaTime);
        
        if (mInputManager.getCursorPosition().y < M_MOVEMENT_PIXEL_DISPLACEMENT)
            moveCamera(M_MOVE_BACK, deltaTime);
        else if (mInputManager.getCursorPosition().y > mScreenHeight - M_MOVEMENT_PIXEL_DISPLACEMENT)
            moveCamera(M_MOVE_FORWARD, deltaTime);
    }
    
    private void moveCamera(int direction, float deltaTime)
    {
        float m = deltaTime * mMovementSpeed;
        Vector3f camPos = mCamera.getLocation();
        
        switch (direction)
        {
            case M_MOVE_LEFT:
                mCamera.setLocation(camPos.add(new Vector3f(-1.0f * m, 0.0f, 0.0f)));
                break;                
            case M_MOVE_RIGHT:
                mCamera.setLocation(camPos.add(new Vector3f(1.0f * m, 0.0f, 0.0f)));                
                break;                
            case M_MOVE_FORWARD:
                mCamera.setLocation(camPos.add(new Vector3f(0.0f, 0.0f, -1.0f * m)));
                break;
            case M_MOVE_BACK:
                mCamera.setLocation(camPos.add(new Vector3f(0.0f, 0.0f, 1.0f * m)));
                break;
            default:
                System.out.println("A");
                break;
        }
        
        mCamera.update();
    }
    
    private void updateCamera()
    {
        mFieldOfView = FastMath.clamp(mFieldOfView, 0.25f, FastMath.PI / 2.0f);
        mCamera.setFrustumPerspective((float)Math.toDegrees(mFieldOfView), (float)mScreenWidth / (float)mScreenHeight, 0.1f, 2000.0f);
        mCamera.update();
    }
}