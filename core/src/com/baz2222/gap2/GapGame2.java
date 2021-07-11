package com.baz2222.gap2;

import com.badlogic.gdx.Game;
import com.baz2222.gap2.manager.*;
import com.baz2222.gap2.tools.GameScreen;

public class GapGame2 extends Game {

    public int width;
    public int height;
    public float scale;
    public boolean gamePaused;
    public static boolean debugMode;
    public int currWorld;
    public int currLevel;

    public UIManager uiManager;
    public Box2DManager box2DManager;
    public GraphicsManager graphicsManager;
    public CharacterManager characterManager;
    public MapManager mapManager;
    public InputManager inputManager;
    public ItemManager itemManager;
    public LevelManager levelManager;
    public ScreenManager screenManager;
    public SoundManager soundManager;
    public StateManager stateManager;
    public TaskManager taskManager;
    public ActionManager actionManager;

    public GapGame2() {
        width = 960;
        height = 544;
        scale = 100f;
        gamePaused = false;
        debugMode = true;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void create() {
        createManagers();
        startGame();
    }

    private void startGame(){
        setScreen(screenManager.menuScreen);
        ((GameScreen)getScreen()).onOpen();
    }

    private void createManagers() {
        actionManager = new ActionManager(this);
        graphicsManager = new GraphicsManager(this);
        box2DManager = new Box2DManager(this);
        characterManager = new CharacterManager(this);
        inputManager = new InputManager(this);
        itemManager = new ItemManager(this);
        levelManager = new LevelManager(this);
        screenManager = new ScreenManager(this);
        soundManager = new SoundManager(this);
        stateManager = new StateManager(this);
        taskManager = new TaskManager(this);
        uiManager = new UIManager(this);
        mapManager = new MapManager(this);
    }

    public static void log(String message){
        if (debugMode){
            System.out.println(message);
        }
    }
}
