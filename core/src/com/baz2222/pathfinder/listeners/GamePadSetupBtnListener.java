package com.baz2222.pathfinder.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.tools.GameScreen;

public class GamePadSetupBtnListener extends InputListener {
    private Pathfinder game;
    public GamePadSetupBtnListener(Pathfinder game) {
        this.game = game;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Pathfinder.log("button pressed");
        ((com.baz2222.pathfinder.tools.GameScreen)game.getScreen()).onClose();
        ((GameScreen)game.getScreen()).onOpen();
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return super.mouseMoved(event, x, y);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        return super.keyUp(event, keycode);
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        return super.keyTyped(event, character);
    }
}
