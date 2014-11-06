
import SpaceColonization.Game;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main
{
    public static void main(String[] args)
    {
        Game game = new Game();
        
        game.setShowSettings(false);
        
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        if (false)
        {
            settings.setFullscreen(true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int)screenSize.getWidth();
            int height = (int)screenSize.getHeight();
            settings.setResolution(width, height);
        }
        else
        {
            settings.setFullscreen(false);
            settings.setResolution(800, 600);
        }
        settings.setVSync(true);
        settings.setBitsPerPixel(24);
        settings.setDepthBits(24);
        settings.setTitle("Space Colonization");
        settings.setSamples(1);
        
        game.setSettings(settings);
        
        game.start();
    }
}