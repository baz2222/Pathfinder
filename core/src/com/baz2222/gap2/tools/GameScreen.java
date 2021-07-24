package com.baz2222.gap2.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;

public abstract class GameScreen extends ScreenAdapter implements InputProcessor, ControllerListener {
    public GameScreen() {
        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);
    }

    public abstract void onOpen();
    public abstract void onClose();

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
