package SpaceColonization.Graphics.HUD;

import SpaceColonization.Graphics.DrawableObject3d;
import com.jme3.math.Vector3f;

/**
 * A class that represents a 3d text box.
 * @author Joe Pagliuco
 */
public class TextBox3d extends DrawableObject3d 
{
    private static final int M_QUAD_SIZE = 100;
    
    public TextBox3d(Vector3f planetPosition, String name)
    {
        super(null, null, name);
    }
    
    private void loadModel()
    {
        
    }
}