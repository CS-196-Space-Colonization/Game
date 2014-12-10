package com.thecolony.tractus.graphics.GUI;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Joe Pagliuco
 */
public class PauseMenu extends AbstractAppState implements ScreenController
{
    private SimpleApplication app;
    
    private NiftyJmeDisplay niftyDisplay;
    private ViewPort viewport;
    
    public PauseMenu(SimpleApplication app)
    {
        this.app = app;
        
        niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        
        Camera guiCam = app.getGuiViewPort().getCamera().clone();
        guiCam.setViewPort(.05f, .95f, 0.1f, 0.9f);
        viewport = app.getRenderManager().createPostView("Nifty Gui", guiCam);
        viewport.setClearFlags(false, false, false);
    }

    public void bind(Nifty nifty, Screen screen)
    {
        
    }

    public void onStartScreen()
    {
        
    }

    public void onEndScreen()
    {
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {        
        this.app = (SimpleApplication)app;
        
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/pause_menu.xml", "pause_menu", this);
    }

    @Override
    public void stateAttached(AppStateManager stateManager)
    {
        viewport.addProcessor(niftyDisplay);
        
        super.stateAttached(stateManager);
    }

    @Override
    public void stateDetached(AppStateManager stateManager)
    {
        viewport.removeProcessor(niftyDisplay);
        
        super.stateDetached(stateManager);
    }
}