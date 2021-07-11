package com.baz2222.gap2.manager;

import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.screen.*;

public class ScreenManager {
    private GapGame2 game;

    public MenuScreen menuScreen;
    public LevelOverScreen levelOverScreen;
    public InputScreen inputScreen;
    public LevelScreen levelScreen;
    public PauseScreen pauseScreen;
    public PlayMenuScreen playMenuScreen;
    public SelectLevelScreen selectLevelScreen;
    public SelectWorldScreen selectWorldScreen;
    public TasksScreen tasksScreen;
    public GamePadSetupScreen gamePadSetupScreen;

    public ScreenManager(GapGame2 game) {
        this.game = game;
        menuScreen = new MenuScreen(game);
        levelOverScreen = new LevelOverScreen(game);
        inputScreen = new InputScreen(game);
        levelScreen = new LevelScreen(game);
        pauseScreen = new PauseScreen(game);
        playMenuScreen = new PlayMenuScreen(game);
        selectLevelScreen = new SelectLevelScreen(game);
        selectWorldScreen = new SelectWorldScreen(game);
        tasksScreen = new TasksScreen(game);
        gamePadSetupScreen = new GamePadSetupScreen(game);
    }
}
