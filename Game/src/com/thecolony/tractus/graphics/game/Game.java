package com.thecolony.tractus.graphics.game;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
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
import com.thecolony.tractus.graphics.GUI.ScrollText;
import com.thecolony.tractus.graphics.drawableobjects.GameGraphics;
import com.thecolony.tractus.graphics.drawableobjects.spatialentities.Planet;
import com.thecolony.tractus.graphics.drawableobjects.spatialentities.Star;
import com.thecolony.tractus.networking.ClientMain;
import com.thecolony.tractus.player.Player;
import com.thecolony.tractus.player.ai.battle.BattleObject;
import com.thecolony.tractus.player.ai.battle.FlotillaBattler;
import com.thecolony.tractus.player.ai.battle.ships.Flotilla;
import com.thecolony.tractus.player.ai.battle.ships.Ship;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Joe Pagliuco
 */
public class Game extends SimpleApplication
{

    public static int M_WIDTH, M_HEIGHT;
    private final float M_INFO_HUB_WIDTH_PERCENTAGE = 10.0f / 1920.0f;
    private final float M_INFO_HUB_HEIGHT_PERCENTAGE = 1.0f - 612.0f / 1080.0f;
    private final float M_COMPRESS_SPEED = 1.0f;
    private final float M_ATTACK_DISTANCE = 50.0f;

    public static enum Selection_Mode
    {

        Ship_Selection, Flotilla_Selection
    };
    private Selection_Mode selectionMode;
    private Planet[] mPlanets;
    private Star[] mSuns;
    private ArrayList<Ship> loneShips;
    private ArrayList<Flotilla> flotillas;
    private Node planetsNode;
    private Node starsNode;
    private Node loneShipsNode;
    private Node flotillasNode;
    private Node mSelectedShipsNode;
    private Node mSelectedFlotillasNode;
    private Vector3f mSelectedNodeCenterPos;
    private Plane mMovementPlane;
    private boolean mIsShiftPressed;
    private boolean isMoveToggleOn;
    private boolean isRotateToggleOn;
    private boolean isBoxSelectToggleOn;
    private boolean isAttackToggleOn;
    private JmeCursor mCursorSmiley;
    private Picture mPictureBoxSelect;
    private Picture mOverlay;
    private ScrollText mInfoHubText;
    private BitmapText mSelectionModeText;
    private boolean isRunning;
    private ClientMain client;
    private ArrayList<FlotillaBattler> flotillaBattles;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
// START INITIALIZATION METHODS /////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Game()
    {
        super(new FlyCamAppState());

        Logger.getLogger("").setLevel(Level.SEVERE);
    }

    @Override
    public void destroy()
    {
        if (client != null)
        {
            client.stop();
        }
        super.destroy();
    }

    public void addClient(ClientMain c)
    {
        client = c;
    }

    @Override
    public void simpleInitApp()
    {
        M_WIDTH = settings.getWidth();
        M_HEIGHT = settings.getHeight();

        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        setDisplayFps(false);
        setDisplayStatView(false);
        isRunning = true;

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
        cam.setLocation(new Vector3f(75.0f, 75.0f, -75.0f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setFrustumPerspective((float) Math.toDegrees(FastMath.PI / 4.0f), (float) M_WIDTH / (float) M_HEIGHT, 0.1f, 2000.0f);

        flyCam.setDragToRotate(true);
        flyCam.setZoomSpeed(15.0f);
        flyCam.setMoveSpeed(50.0f);
    }

    private Planet generatePlanet(int index)
    {
        float radius = (float) (1 + (Math.random() * 5));
        int posNeg = (Math.random() < 0.5) ? -1 : 1;
        int orbitRadius = 15 + (25 * (index + 1));
        float xPos = posNeg * (int) (Math.random() * orbitRadius);
        posNeg = (Math.random() < 0.5) ? -1 : 1;
        float zPos = posNeg * (float) Math.sqrt(orbitRadius * orbitRadius - xPos * xPos);

        return new Planet("Planet " + Integer.toString(index), planetsNode, new Vector3f(xPos, 0.0f, zPos), assetManager, radius, ColorRGBA.randomColor());
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
        planetsNode = new Node("Planets Node");
        mPlanets = new Planet[10];

        for (int i = 0; i < mPlanets.length; i++)
        {
            mPlanets[i] = generatePlanet(i);
        }
        rootNode.attachChild(planetsNode);
    }

    private void loadSun()
    {
        starsNode = new Node("Stars Node");

        mSuns = new Star[1];
        mSuns[0] = new Star("StarX", starsNode, Vector3f.ZERO, assetManager, 20.0f);
        rootNode.addLight(mSuns[0].getPointLight());

        rootNode.attachChild(starsNode);
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
        mSelectedNodeCenterPos = new Vector3f();

        loneShipsNode = new Node("Lone Ships");

        double[] stats = new double[19];
        stats[BattleObject.BATTLE_STAT_MOVEMENT_SPEED] = 5.0;

        loneShips = new ArrayList<Ship>();

        for (int i = 0; i < 5; i++)
        {
            loneShips.add(new Ship(new Player(3), Ship.SHIP_TYPE.Fighter, "Fighter " + i, loneShipsNode, new Vector3f(0.0f, 0.0f, -(30 + i * 3)),
                    stats, 0, "Fighter " + i, 0, 0, 0.0));
            loneShips.get(i).getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(loneShips.get(i).getPlayer().getColor()));
        }

        rootNode.attachChild(loneShipsNode);

        ///////////////////////////////
        // Not Related /\ \/ //////////
        ///////////////////////////////

        flotillasNode = new Node("Flotillas Node");

        Ship[] ships1 = new Ship[50];
        for (int i = 0; i < ships1.length; i++)
        {
            ships1[i] = new Ship(new Player(0), Ship.SHIP_TYPE.Fighter, "Fighter " + i, flotillasNode, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            ships1[i].getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(ships1[i].getPlayer().getColor()));
        }

        Ship[] ships2 = new Ship[25];
        for (int i = 0; i < ships2.length; i++)
        {
            ships2[i] = new Ship(new Player(1), Ship.SHIP_TYPE.CapitalShip, "Capital Ship " + i, flotillasNode, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            ships2[i].getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(ships2[i].getPlayer().getColor()));
        }

        Ship[] ships3 = new Ship[25];
        for (int i = 0; i < ships3.length; i++)
        {
            ships3[i] = new Ship(new Player(2), Ship.SHIP_TYPE.Fighter, "Fighter " + i, flotillasNode, Vector3f.ZERO, stats, 0, "H", 0, 0, 0);
            ships3[i].getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(ships3[i].getPlayer().getColor()));
        }

        flotillas = new ArrayList<Flotilla>();
        flotillas.add(new Flotilla(ships1, false, new Vector3f(-50.0f, 0.0f, 50.0f), "Flotilla 1"));
        flotillas.add(new Flotilla(ships2, false, new Vector3f(50.0f, 0.0f, 0.0f), "Flotilla 2"));
        flotillas.add(new Flotilla(ships3, false, new Vector3f(100.0f, 0.0f, 25.0f), "Flotilla 3"));

        rootNode.attachChild(flotillasNode);

        flotillaBattles = new ArrayList<FlotillaBattler>();
    }

    private void initializeListeners()
    {
        mIsShiftPressed = false;
        isMoveToggleOn = false;
        isRotateToggleOn = false;
        isBoxSelectToggleOn = false;
        isAttackToggleOn = false;

        inputManager.addMapping("Right Click", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Left Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(mMouseActionListener, new String[]
        {
            "Right Click", "Left Click"
        });

        inputManager.addMapping("Shift", new KeyTrigger(KeyInput.KEY_LSHIFT), new KeyTrigger(KeyInput.KEY_RSHIFT));
        inputManager.addMapping("Move", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("Box Select", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("Attack", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Compress", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("Decompress", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("Switch Selection Mode", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_BACK));
        inputManager.addMapping("More Ships", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addMapping("Scroll Up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Scroll Down", new KeyTrigger(KeyInput.KEY_DOWN));

        inputManager.addListener(mKeyboardActionListener, new String[]
        {
            "Shift", "Move", "Rotate", "Box Select",
            "Attack", "Switch Selection Mode", "Pause",
            "Exit", "Scroll Up", "Scroll Down", "More Ships"
        });
        inputManager.addListener(mKeyboardAnalogListener, new String[]
        {
            "Compress", "Decompress"
        });
    }
    private AudioNode[] battles;
    private AudioNode[] creates;
    private void loadAudio()
    {
        AudioNode audio_battle1 = new AudioNode(assetManager, "assets/Sounds/Battle/35683__jobro__laser6.wav", false);
        audio_battle1.setLooping(false);
        audio_battle1.setVolume(3);
        audio_battle1.setPositional(false);
        rootNode.attachChild(audio_battle1);
        AudioNode audio_battle2 = new AudioNode(assetManager, "assets/Sounds/Battle/42106__marcuslee__laser-wrath-4.wav", false);
        audio_battle2.setLooping(false);
        audio_battle2.setVolume(3);
        audio_battle2.setPositional(false);
        rootNode.attachChild(audio_battle2);
        AudioNode audio_battle3 = new AudioNode(assetManager, "assets/Sounds/Battle/151022__bubaproducer__laser-shot-silenced.wav", false);
        audio_battle3.setLooping(false);
        audio_battle3.setVolume(3);
        audio_battle3.setPositional(false);
        rootNode.attachChild(audio_battle3);
        AudioNode audio_battle4 = new AudioNode(assetManager, "assets/Sounds/Battle/196914__dpoggioli__laser-gun.wav", false);
        audio_battle4.setLooping(false);
        audio_battle4.setVolume(3);
        audio_battle4.setPositional(false);
        rootNode.attachChild(audio_battle4);
        
        battles = new AudioNode[4];
        battles[0]=audio_battle1;
        battles[1]=audio_battle2;
        battles[2]=audio_battle3;
        battles[3]=audio_battle4;
        
       
        
        AudioNode audio_onCreate1 = new AudioNode(assetManager, "assets/Sounds/Unit_Creation/71079__aharri6__drill-2.wav", false);
        audio_onCreate1.setLooping(false);
        audio_onCreate1.setVolume(3);
        audio_onCreate1.setPositional(false);
        rootNode.attachChild(audio_onCreate1);
        AudioNode audio_onCreate2 = new AudioNode(assetManager, "assets/Sounds/Unit_Creation/135846__joelaudio__gear-multiple-bursts-001.wav", false);
        audio_onCreate2.setLooping(false);
        audio_onCreate2.setVolume(3);
        audio_onCreate2.setPositional(false);
        rootNode.attachChild(audio_onCreate2);
        AudioNode audio_onCreate3 = new AudioNode(assetManager, "assets/Sounds/Unit_Creation/146232__ferdinger__dc-welding.wav", false);
        audio_onCreate3.setLooping(false);
        audio_onCreate3.setVolume(3);
        audio_onCreate3.setPositional(false);
        rootNode.attachChild(audio_onCreate3);
        AudioNode audio_onCreate4 = new AudioNode(assetManager, "assets/Sounds/Unit_Creation/207782__dvideoguy__hammering.wav", false);
        audio_onCreate4.setLooping(false);
        audio_onCreate4.setVolume(3);
        audio_onCreate4.setPositional(false);
        rootNode.attachChild(audio_onCreate4);
        creates = new AudioNode[4];
        creates[0]=audio_onCreate1;
        creates[1]=audio_onCreate2;
        creates[2]=audio_onCreate3;
        creates[3]=audio_onCreate4;
       
    }
                
        
    

    private void addNodes()
    {
        rootNode.attachChild(mSelectedShipsNode);
        rootNode.attachChild(planetsNode);
        rootNode.attachChild(starsNode);
        rootNode.attachChild(loneShipsNode);
        rootNode.attachChild(flotillasNode);
    }

    private void loadCursors()
    {
        mCursorSmiley = (JmeCursor) assetManager.loadAsset("Textures/cursor_smiley.cur");
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
        float fontSize = (guiFont.getCharSet().getRenderedSize() * ((float) M_WIDTH / (float) M_HEIGHT)) / (1920.0f / 1080.0f);

//        mInfoHubText = new BitmapText(guiFont);
//        mInfoHubText.setSize(fontSize);
//        mInfoHubText.setColor(ColorRGBA.White);
//        mInfoHubText.setText("");
//        mInfoHubText.setLocalTranslation(M_INFO_HUB_WIDTH_PERCENTAGE * M_WIDTH, M_INFO_HUB_HEIGHT_PERCENTAGE * M_HEIGHT, 0.0f);
//        guiNode.attachChild(mInfoHubText);

        mInfoHubText = new ScrollText(M_HEIGHT, fontSize, M_INFO_HUB_WIDTH_PERCENTAGE * M_WIDTH,
                M_INFO_HUB_HEIGHT_PERCENTAGE * M_HEIGHT, guiFont, guiNode);

        mSelectionModeText = new BitmapText(guiFont);
        mSelectionModeText.setSize(fontSize);
        mSelectionModeText.setColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 0.75f));
        mSelectionModeText.setText("Selection Mode: Ship (Press 'Tab' to switch)");
        mSelectionModeText.setLocalTranslation(M_WIDTH - mSelectionModeText.getLineWidth(), mSelectionModeText.getLineHeight(), 0.0f);
        guiNode.attachChild(mSelectionModeText);
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
		{
		    clearSelectedObjects();
		}

		Ray r = getMouseRay();

		boolean selectedSomething = false;
		for (int i = 0; i < loneShips.size(); i++)
		{
		    Ship s = loneShips.get(i);
		    selectedSomething = ((BoundingBox) s.getDrawableObject3d().getModel().getWorldBound()).intersects(r);
		    if (selectedSomething)
		    {
		        addSelectedObject(s);
		        break;
		    }
		}
		if (!selectedSomething)
		{
		    clearSelectedObjects();
		}
	      }

	      if (name.equals("Left Click") && isPressed)
	      {
		if (isMoveToggleOn || isRotateToggleOn)
		{
		    Vector3f directionalVector = getMouseRayIntersectionPoint().subtract(mSelectedNodeCenterPos);

		    for (int i = 0; i < loneShips.size(); i++)
		    {
		        Ship s = loneShips.get(i);
		        if (s.isSelected())
		        {
			  s.setTargetPoint(s.getPosition().add(directionalVector), isMoveToggleOn);
		        }
		    }

		    isMoveToggleOn = isRotateToggleOn = false;
		    inputManager.setMouseCursor(null);
		} else
		{
		    if (isBoxSelectToggleOn)
		    {
		        Vector2f cursorPos = inputManager.getCursorPosition();
		        mPictureBoxSelect.setUserData("Initial Position", cursorPos.clone());
		        guiNode.attachChild(mPictureBoxSelect);
		    }
		}
	      } else
	      {
		if (name.equals("Left Click") && !isPressed)
		{
		    if (isBoxSelectToggleOn)
		    {
		        clearSelectedObjects();

		        // Create big ass selector cube...
		        Vector3f min = getMouseRayIntersectionPoint();
		        Vector3f max = Vector3f.ZERO;
		        Ray r = createRayFromPoint((Vector2f) mPictureBoxSelect.getUserData("Initial Position"));
		        r.intersectsWherePlane(mMovementPlane, max);
		        Box bigAssSelectorCube = new Box(min, max);

		        // Loop through ships to see if they're in the selected area...
		        for (int i = 0; i < loneShips.size(); i++)
		        {
			  BoundingVolume shipBound = loneShips.get(i).getDrawableObject3d().getModel().getWorldBound();
			  if (bigAssSelectorCube.getBound().intersects(shipBound))
			  {
			      addSelectedObject(loneShips.get(i));
			  }
		        }

		        guiNode.detachChild(mPictureBoxSelect);
		        isBoxSelectToggleOn = false;
		        inputManager.setMouseCursor(null);
		        flyCam.setEnabled(true);
		    }
		}
	      }
	  } else
	  {
	      if (selectionMode == Selection_Mode.Flotilla_Selection)
	      {
		if (name.equals("Right Click") && isPressed)
		{
		    if (!mIsShiftPressed)
		    {
		        clearSelectedObjects();
		    }

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
		    {
		        clearSelectedObjects();
		    }
		}

		if (name.equals("Left Click") && isPressed)
		{
		    if (isMoveToggleOn || isRotateToggleOn)
		    {
		        Vector3f directionalVector = getMouseRayIntersectionPoint().subtract(mSelectedNodeCenterPos);
		        for (int i = 0; i < flotillas.size(); i++)
		        {
			  Flotilla f = flotillas.get(i);
			  if (f.isSelected())
			  {
			      f.setTargetPoint(f.getCenterPosition().add(directionalVector), isMoveToggleOn);
			  }
		        }

		        isMoveToggleOn = isRotateToggleOn = false;
		        inputManager.setMouseCursor(null);
		    } else
		    {
		        if (isAttackToggleOn)
		        {
			  Ray r = getMouseRay();
			  for (int i = 0; i < flotillas.size(); i++)
			  {
			      Flotilla f = flotillas.get(i);
			      if (f.isSelected())
			      {
				break;
			      }

			      boolean hover = f.getBoundingBox().intersects(r);
			      if (hover)
			      {
				Vector3f targetDirection = f.getCenterPosition().subtract(mSelectedNodeCenterPos);
				float change = M_ATTACK_DISTANCE / targetDirection.length();
				Vector3f targetPoint = f.getCenterPosition().clone().interpolate(mSelectedNodeCenterPos, change);

                                f.setTargetPoint(targetPoint, false);

				// Add attacker
				Flotilla attacker = null;
				for (int j = 0; j < flotillas.size(); j++)
				{
				    Flotilla f2 = flotillas.get(j);
				    if (f2.isSelected())
				    {
				        attacker = f2;
				        f2.setTargetPoint(targetPoint, true);
				        break;
				    }
				}

				flotillaBattles.add(new FlotillaBattler(attacker, f));
			      }
			  }

			  isAttackToggleOn = false;
			  inputManager.setMouseCursor(null);
		        }
		    }
		}
	      }
	  }
}
    };
    private ActionListener mKeyboardActionListener = new ActionListener()
    {
        public void onAction(String name, boolean isPressed, float tpf)
        {
	  if (name.equals("Pause") && isPressed)
	  {
	      isRunning = !isRunning;

	      flyCam.setEnabled(isRunning);
	  }

	  if (isRunning)
	  {

	      if (name.equals("More Ships"))
	      
                  
    loadAudio();
                  creates[1].playInstance();
		double[] stats = new double[19];
		stats[BattleObject.BATTLE_STAT_MOVEMENT_SPEED] = 5.0;

		loneShips.add(new Ship(new Player(4), Ship.SHIP_TYPE.Fighter, "Fighter " + loneShips.size(), loneShipsNode, new Vector3f(0.0f, 0.0f, -(30 + loneShips.size() * 3)),
		        stats, 0, "Fighter " + loneShips.size(), 0, 0, 0.0));
		loneShips.get(loneShips.size() - 1).getDrawableObject3d().getModel().setMaterial(GameGraphics.generateMaterial(loneShips.get(loneShips.size() - 1).getPlayer().getColor()));
	      }

	      if (name.equals("Shift"))
	      {
		mIsShiftPressed = isPressed;
	      }


	      if (name.equals("Move") && isPressed)
	      {
		isMoveToggleOn = !isMoveToggleOn;
		isRotateToggleOn = false;
		isBoxSelectToggleOn = false;
		isAttackToggleOn = false;
	      } else
	      {
		if (name.equals("Rotate") && isPressed)
		{
		    isMoveToggleOn = false;
		    isRotateToggleOn = !isRotateToggleOn;
		    isBoxSelectToggleOn = false;
		    isAttackToggleOn = false;
		} else
		{
		    if (name.equals("Box Select") && isPressed)
		    {
		        isMoveToggleOn = false;
		        isRotateToggleOn = false;
		        isBoxSelectToggleOn = !isBoxSelectToggleOn;
		        isAttackToggleOn = false;

		        flyCam.setEnabled(!flyCam.isEnabled());
		    } else
		    {
		        if (name.equals("Attack") && mSelectedFlotillasNode.getQuantity() > 0 && isPressed)
		        {
			  isMoveToggleOn = false;
			  isRotateToggleOn = false;
			  isBoxSelectToggleOn = false;
			  isAttackToggleOn = !isAttackToggleOn;
		        }
		    }
		}
	      }


	      if (isMoveToggleOn || isRotateToggleOn || isBoxSelectToggleOn || isAttackToggleOn)
	      {
		inputManager.setMouseCursor(mCursorSmiley);
	      } else
	      {
		inputManager.setMouseCursor(null);
	      }


	      if (name.equals("Switch Selection Mode") && isPressed)
	      {
		if (selectionMode == Selection_Mode.Ship_Selection)
		{
		    selectionMode = Selection_Mode.Flotilla_Selection;
		    rootNode.detachChild(mSelectedShipsNode);
		    rootNode.attachChild(mSelectedFlotillasNode);
		    mSelectionModeText.setText("Selection Mode: Flotilla (Press 'Tab' to switch)");
		} else
		{
		    if (selectionMode == Selection_Mode.Flotilla_Selection)
		    {
		        selectionMode = Selection_Mode.Ship_Selection;
		        rootNode.detachChild(mSelectedFlotillasNode);
		        rootNode.attachChild(mSelectedShipsNode);
		        mSelectionModeText.setText("Selection Mode: Ship (Press 'Tab' to switch)");
		    }
		}

		mSelectionModeText.setLocalTranslation(M_WIDTH - mSelectionModeText.getLineWidth(), mSelectionModeText.getLineHeight(), 0.0f);

		calculateCenterPoint();

		mIsShiftPressed = false;
		isMoveToggleOn = false;
		isRotateToggleOn = false;
		isBoxSelectToggleOn = false;
		isAttackToggleOn = false;
		inputManager.setMouseCursor(null);
	      }

	      if (name.contains("Scroll") && isPressed)
	      {
		mInfoHubText.scroll(name.contains("Up"));
	      }
	  }

	  if (name.equals("Exit"))
	  {
	      stop();
	  }
       }
    };
    private AnalogListener mKeyboardAnalogListener = new AnalogListener()
    {
        public void onAnalog(String name, float value, float tpf)
        {
            if (selectionMode == Selection_Mode.Ship_Selection)
            {
                if (name.equals("Compress") || name.equals("Decompress"))
                {
                    if (mSelectedShipsNode.getQuantity() > 1)
                    {
                        float speed = M_COMPRESS_SPEED;
                        if (name.equals("Decompress"))
                        {
                            speed *= -1;
                        }

                        for (int i = 0; i < loneShips.size(); i++)
                        {
                            Ship s = loneShips.get(i);
                            if (s.isSelected() && !s.isTransforming())
                            {
                                Vector3f direction = mSelectedNodeCenterPos.subtract(s.getPosition()).normalize();
                                s.move(direction.mult(speed * tpf));
                            }
                        }
                    }
                }
            }
            else
            {
                if (selectionMode == Selection_Mode.Flotilla_Selection)
                {
                    if (name.equals("Compress") || name.equals("Decompress"))
                    {
                        if (mSelectedFlotillasNode.getQuantity() > 1)
                        {
                            float speed = M_COMPRESS_SPEED;
                            if (name.equals("Decompress"))
                            {
                                speed *= -1;
                            }

                            for (int i = 0; i < flotillas.size(); i++)
                            {
                                Flotilla f = flotillas.get(i);
                                if (f.isSelected() && !f.isTransforming())
                                {
                                    Vector3f direction = mSelectedNodeCenterPos.subtract(f.getCenterPosition()).normalize();
                                    f.move(direction.mult(speed * tpf));
                                }
                            }
                        }
                    }
                }
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
        if (isRunning)
        {
            // Update box select picture as needed...
            if (isBoxSelectToggleOn)
            {
                Vector2f initPos = mPictureBoxSelect.getUserData("Initial Position");
                Vector2f cursorPos = inputManager.getCursorPosition();
                if (initPos.x < cursorPos.x && initPos.y > cursorPos.y)
                {
                    mPictureBoxSelect.setPosition(initPos.x, cursorPos.y);
                }
                else
                {
                    if (initPos.x > cursorPos.x && initPos.y < cursorPos.y)
                    {
                        mPictureBoxSelect.setPosition(cursorPos.x, initPos.y);
                    }
                    else
                    {
                        if (initPos.x > cursorPos.x && initPos.y > cursorPos.y)
                        {
                            mPictureBoxSelect.setPosition(cursorPos.x, cursorPos.y);
                        }
                        else
                        {
                            mPictureBoxSelect.setPosition(initPos.x, initPos.y);
                        }
                    }
                }

                mPictureBoxSelect.setWidth(Math.abs(cursorPos.x - initPos.x));
                mPictureBoxSelect.setHeight(Math.abs(initPos.y - cursorPos.y));
            }

            // Update ships...
            for (int i = 0; i < loneShips.size(); i++)
            {
                loneShips.get(i).update(tpf);
            }
            // Update flotillas...
            for (int i = 0; i < flotillas.size(); i++)
            {
                flotillas.get(i).update(tpf);
            }

            // Update center position of selected ships...
            calculateCenterPoint();


            Ray r = getMouseRay();
            boolean somethingSelected = false;
            if (selectionMode == Selection_Mode.Ship_Selection)
            {
                // Check if mouse hovering over anything to update info hub...
                for (int i = 0; i < loneShips.size(); i++)
                {
                    somethingSelected = loneShips.get(i).getDrawableObject3d().getModel().getWorldBound().intersects(r);
                    if (somethingSelected)
                    {
                        mInfoHubText.clearText();
                        mInfoHubText.addText(loneShips.get(i).getDisplayInfo());
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
                                mInfoHubText.clearText();
                                mInfoHubText.addText(f.getFlotilla()[j].getDisplayInfo());
                                break;
                            }
                        }
                        if (somethingSelected)
                        {
                            break;
                        }
                    }
                }
            }
            else
            {
                if (selectionMode == Selection_Mode.Flotilla_Selection)
                {
                    // Check if mouse hovering over anything to update info hub...
                    for (int i = 0; i < flotillas.size(); i++)
                    {
                        somethingSelected = flotillas.get(i).getBoundingBox().intersects(r);
                        if (somethingSelected)
                        {
                            mInfoHubText.clearText();
                            mInfoHubText.addText(flotillas.get(i).getDisplayInfo());
                            break;
                        }
                    }
                }
            }
            if (!somethingSelected) // Check Planets and Stars...
            {
                for (int i = 0; i < mPlanets.length; i++)
                {
                    Planet p = mPlanets[i];
                    somethingSelected = p.getBoundingSphere().intersects(r);
                    if (somethingSelected)
                    {
                        mInfoHubText.clearText();
                        mInfoHubText.addText(p.getDisplayInfo());
                        break;
                    }
                }

                if (!somethingSelected)
                {
                    for (int i = 0; i < mSuns.length; i++)
                    {
                        Star s = mSuns[i];
                        somethingSelected = s.getBoundingSphere().intersects(r);
                        if (somethingSelected)
                        {
                            mInfoHubText.clearText();
                            mInfoHubText.addText(s.getDisplayInfo());
                            break;
                        }
                    }
                }
            }
            if (!somethingSelected)
            {
                mInfoHubText.clearText();
            }

            // Update battles...
            for (int i = 0; i < flotillaBattles.size(); i++)
            {
                int battle = flotillaBattles.get(i).update(tpf);
                if (battle != 0)
                {
                    flotillaBattles.remove(i--);
                }
            }
        }
    }

    /**
     * Creates a ray based off the 2d coordinate.
     *
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
     *
     * @param ship The reference to the ship that has been selected.
     */
    private void addSelectedObject(Ship ship)
    {
        mSelectedShipsNode.attachChild(ship.getWireBoxGeometry());

        ship.setIsSelected(true);

        calculateCenterPoint();
    }

    public void addSelectedObject(Flotilla flotilla)
    {
        mSelectedFlotillasNode.attachChild(flotilla.getWireBoxGeometry());

        flotilla.setIsSelected(true);

        calculateCenterPoint();
    }

    /**
     * Clears all of the data in the selected objects node.
     */
    private void clearSelectedObjects()
    {
        if (selectionMode == Selection_Mode.Ship_Selection)
        {
            for (int i = 0; i < loneShips.size(); i++)
            {
                loneShips.get(i).setIsSelected(false);
            }

            mSelectedShipsNode.detachAllChildren();
        }
        else
        {
            if (selectionMode == Selection_Mode.Flotilla_Selection)
            {
                for (int i = 0; i < flotillas.size(); i++)
                {
                    flotillas.get(i).setIsSelected(false);
                }

                mSelectedFlotillasNode.detachAllChildren();
            }
        }
    }

    private void calculateCenterPoint()
    {
        ArrayList<Vector3f> points = new ArrayList<Vector3f>();
        if (selectionMode == Selection_Mode.Ship_Selection)
        {
            for (int i = 0; i < loneShips.size(); i++)
            {
                Ship s = loneShips.get(i);
                if (s.isSelected())
                {
                    points.add(s.getPosition());
                }
            }
        }
        else
        {
            if (selectionMode == Selection_Mode.Flotilla_Selection)
            {
                for (int i = 0; i < flotillas.size(); i++)
                {
                    Flotilla f = flotillas.get(i);
                    if (f.isSelected())
                    {
                        points.add(f.getCenterPosition());
                    }
                }
            }
        }

        Vector3f[] p = new Vector3f[points.size()];
        for (int i = 0; i < points.size(); i++)
        {
            p[i] = points.get(i);
        }
        mSelectedNodeCenterPos = getCenterPoint(p);
    }

    private Vector3f getCenterPoint(Vector3f[] points)
    {
        float x = 0.0f;
        float z = 0.0f;

        int size = points.length;
        for (int i = 0; i < size; i++)
        {
            x += points[i].x;
            z += points[i].z;
        }

        x /= size;
        z /= size;

        return new Vector3f(x, 0.0f, z);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////
// END UPDATE METHODS ///////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
}