package com.thecolony.tractus.graphics.GUI;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
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
    
    public PauseMenu(NiftyJmeDisplay niftyDisplay)
    {
        this.niftyDisplay = niftyDisplay;
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
        
        stateAttached(stateManager);
    }

    @Override
    public void stateAttached(AppStateManager stateManager)
    {
        try {
            app.getGuiViewPort().addProcessor(niftyDisplay);
        } catch (NullPointerException e) {  }
        
        super.stateAttached(stateManager);
    }

    @Override
    public void stateDetached(AppStateManager stateManager)
    {
        System.out.println("Herro?");
        app.getGuiViewPort().removeProcessor(niftyDisplay);
        
        super.stateDetached(stateManager);
    }
}