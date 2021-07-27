package com.baz2222.pathfinder.manager;

import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.screen.GamePadSetupScreen;

public class ScreenManager {
    private Pathfinder game;

    public com.baz2222.pathfinder.screen.MenuScreen menuScreen;
    public com.baz2222.pathfinder.screen.LevelOverScreen levelOverScreen;
    public com.baz2222.pathfinder.screen.LevelScreen levelScreen;
    public com.baz2222.pathfinder.screen.PauseScreen pauseScreen;
    public com.baz2222.pathfinder.screen.PlayMenuScreen playMenuScreen;
    public com.baz2222.pathfinder.screen.SelectLevelScreen selectLevelScreen;
    public com.baz2222.pathfinder.screen.SelectWorldScreen selectWorldScreen;
    public com.baz2222.pathfinder.screen.TasksScreen tasksScreen;
    public com.baz2222.pathfinder.screen.GamePadSetupScreen gamePadSetupScreen;

    public ScreenManager(Pathfinder game) {
        this.game = game;
        menuScreen = new com.baz2222.pathfinder.screen.MenuScreen(game);
        levelOverScreen = new com.baz2222.pathfinder.screen.LevelOverScreen(game);
        levelScreen = new com.baz2222.pathfinder.screen.LevelScreen(game);
        pauseScreen = new com.baz2222.pathfinder.screen.PauseScreen(game);
        playMenuScreen = new com.baz2222.pathfinder.screen.PlayMenuScreen(game);
        selectLevelScreen = new com.baz2222.pathfinder.screen.SelectLevelScreen(game);
        selectWorldScreen = new com.baz2222.pathfinder.screen.SelectWorldScreen(game);
        tasksScreen = new com.baz2222.pathfinder.screen.TasksScreen(game);
        gamePadSetupScreen = new GamePadSetupScreen(game);
    }
}
