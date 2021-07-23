package com.baz2222.gap2.manager;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;

import static com.baz2222.gap2.GapGame2.log;

public class InputManager {
    private GapGame2 game;
    public int inputSensitiveActorsCount;
    public int currentISActor;
    public InputEvent inputEvent;
    public Array<TextButton> inputSensetiveActors;

    public Controller controller;

    public InputManager(GapGame2 game) {
        this.game = game;
        inputSensitiveActorsCount = 0;
        currentISActor = 0;
        inputSensetiveActors = new Array<>();
        inputEvent = new InputEvent();
    }//constructor

    public String getCurrControllerName(){
        if(!Controllers.getControllers().isEmpty()){
            controller = Controllers.getControllers().first();
            return "GPAD NAME: " + controller.getName();
        }else{
            return "NO CONTROLLERS CONNECTED";
        }//if
    }

    public void addISA(TextButton actor) {
        inputSensitiveActorsCount++;
        inputSensetiveActors.add(actor);
    }//add

    public void removeAllISA() {
        inputSensitiveActorsCount = 0;
        inputSensetiveActors.get(currentISActor).setChecked(false);
        currentISActor = 0;
        inputSensetiveActors.clear();
    }//removeAll

    public TextButton getCurrentISA(){
        return inputSensetiveActors.get(currentISActor);
    }
    public void setCurrentISA(int currentActor) {
        if (currentActor < inputSensitiveActorsCount) {
            currentISActor = currentActor;
            inputSensetiveActors.get(currentISActor).setChecked(true);
            log("" + inputSensitiveActorsCount);
        }
    }//setCurrent

    public void nextISA() {
        if (currentISActor < inputSensitiveActorsCount - 1) {
            inputSensetiveActors.get(currentISActor).setChecked(false);
            currentISActor++;
            inputSensetiveActors.get(currentISActor).setChecked(true);
            game.soundManager.playSound("fall", false);
        }//if
    }//next

    public void previousISA() {
        if (currentISActor > 0) {
            inputSensetiveActors.get(currentISActor).setChecked(false);
            currentISActor--;
            inputSensetiveActors.get(currentISActor).setChecked(true);
            game.soundManager.playSound("fall", false);
        }
    }//setCurrent

}
