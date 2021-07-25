package com.baz2222.pathfinder.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.baz2222.pathfinder.Pathfinder;

public class StateManager {

    public int wrapped;
    public int died;
    public int killed;
    public int completed;

    public Preferences prefs;

    private Pathfinder game;

    public StateManager(Pathfinder game) {
        this.game = game;
        prefs = Gdx.app.getPreferences("Gap2");
        reset();
    }

    public void reset(){
        game.currWorld = 1;
        game.currLevel = 1;
        wrapped = 0;
        died = 0;
        killed = 0;
        completed = 0;
    }

    public void saveState(){
        prefs.putInteger("world",game.currWorld);
        prefs.putInteger("level",game.currLevel);
        prefs.putInteger("wrapped",wrapped);
        prefs.putInteger("died",died);
        prefs.putInteger("killed",killed);
        prefs.putInteger("completed",completed);
        prefs.flush();
    }

    public void loadState(){
        game.currWorld = prefs.getInteger("world",1);
        game.currLevel = prefs.getInteger("level",1);
        wrapped = prefs.getInteger("wrapped",0);
        died = prefs.getInteger("died",0);
        killed = prefs.getInteger("killed",0);
        completed = prefs.getInteger("completed",0);
    }
}
