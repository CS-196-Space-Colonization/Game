
import com.jme3.system.AppSettings;
import com.spacecolonization.game.Game;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author Joe Pagliuco
 */
public class Main
{
    public static void main(String[] args)
    {
        final boolean fullscreen = false;
        
        Game game = new Game();
        
        game.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        
        settings.setTitle("Space Colonization");
        settings.setFrameRate(60);
        settings.setVSync(true);
        settings.setFrequency(60);
        
        settings.setFullscreen(fullscreen);
        if (fullscreen)
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            settings.setResolution(screenSize.width, screenSize.height);
        }
        else
        {
            settings.setResolution(800, 600);
        }
        
        game.setSettings(settings);
        game.start();
    }
}