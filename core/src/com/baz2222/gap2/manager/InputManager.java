package com.baz2222.gap2.manager;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.tools.GPadKeyMap;

import static com.baz2222.gap2.GapGame2.log;

public class InputManager {
    private GapGame2 game;
    public int inputSensitiveActorsCount;
    public int currentISActor;
    public InputEvent inputEvent;
    public Array<TextButton> inputSensitiveActors;

    public Controller currentGPad;
    public Array<Controller> GPads;
    public GPadKeyMap currentGPadKeyMap;
    public Array<GPadKeyMap> GPadKeyMaps;

    public InputManager(GapGame2 game) {
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
            log("" + inputSensitiveActorsCount);
        }
        log("current isa actor = " + currentISActor);
    }//setCurrent

    public void nextISA() {
        if (currentISActor < inputSensitiveActorsCount - 1) {
            inputSensitiveActors.get(currentISActor).setChecked(false);
            currentISActor++;
            inputSensitiveActors.get(currentISActor).setChecked(true);
            game.soundManager.playSound("fall", false);
        }//if
        log("current isa actor = " + currentISActor);
    }//next

    public void previousISA() {
        if (currentISActor > 0) {
            inputSensitiveActors.get(currentISActor).setChecked(false);
            currentISActor--;
            inputSensitiveActors.get(currentISActor).setChecked(true);
            game.soundManager.playSound("fall", false);
        }
        log("current isa actor = " + currentISActor);
    }//setPrevious

}//input manager class