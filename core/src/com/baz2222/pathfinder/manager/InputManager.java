package com.baz2222.pathfinder.manager;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.tools.GPadKeyMap;

public class InputManager {
    private Pathfinder game;
    public int inputSensitiveActorsCount;
    public int currentISActor;
    public InputEvent inputEvent;
    public Array<TextButton> inputSensitiveActors;

    public Controller currentGPad;
    public Array<Controller> GPads;
    public com.baz2222.pathfinder.tools.GPadKeyMap currentGPadKeyMap;
    public Array<com.baz2222.pathfinder.tools.GPadKeyMap> GPadKeyMaps;

    public int confirmKeyCode;
    public int cancelKeyCode;

    public InputManager(Pathfinder game) {
        this.game = game;
        inputSensitiveActorsCount = 0;
        currentISActor = 0;
        inputSensitiveActors = new Array<>();
        inputEvent = new InputEvent();
        GPads = new Array<>();
        if (!Controllers.getControllers().isEmpty())
            GPads = Controllers.getControllers();
        GPadKeyMaps = new Array<>();
        if(!GPads.isEmpty()) {
            for (int i = 0; i < GPads.size; i++) {
                GPadKeyMaps.add(new GPadKeyMap());
                GPadKeyMaps.get(i).name = GPads.get(i).getName();
            }//for
        }//if GPads is not empty
    }//constructor

    public void addISA(TextButton actor) {
        inputSensitiveActorsCount++;
        inputSensitiveActors.add(actor);
    }//add

    public void removeAllISA() {
        inputSensitiveActorsCount = 0;
        inputSensitiveActors.get(currentISActor).setChecked(false);
        currentISActor = 0;
        inputSensitiveActors.clear();
    }//removeAll

    public TextButton getCurrentISA() {
        return inputSensitiveActors.get(currentISActor);
    }

    public void setCurrentISA(int currentActor) {
        if (currentActor < inputSensitiveActorsCount) {
            currentISActor = currentActor;
            inputSensitiveActors.get(currentISActor).setChecked(true);
        }
        Pathfinder.log("current isa actor = " + currentISActor);
    }//setCurrent

    public void nextISA() {
        if (currentISActor < inputSensitiveActorsCount - 1) {
            inputSensitiveActors.get(currentISActor).setChecked(false);
            currentISActor++;
            inputSensitiveActors.get(currentISActor).setChecked(true);
            game.soundManager.playSound("fall", false);
        }//if
        Pathfinder.log("current isa actor = " + currentISActor);
    }//next

    public void previousISA() {
        if (currentISActor > 0) {
            inputSensitiveActors.get(currentISActor).setChecked(false);
            currentISActor--;
            inputSensitiveActors.get(currentISActor).setChecked(true);
            game.soundManager.playSound("fall", false);
        }
        Pathfinder.log("current isa actor = " + currentISActor);
    }//setPrevious

}//input manager class