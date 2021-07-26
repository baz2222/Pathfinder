package com.baz2222.pathfinder.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.tools.GameScreen;

import java.util.concurrent.TimeUnit;

import static com.baz2222.pathfinder.Pathfinder.log;

public class GamePadSetupScreen extends GameScreen {
    private Pathfinder game;
    private String state;

    public GamePadSetupScreen(Pathfinder game) {
        this.game = game;
        state = "";
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.box2DManager.renderer.render();
        game.uiManager.box2DCamera.update();
        game.uiManager.batch.setProjectionMatrix(game.uiManager.box2DCamera.combined);
        game.box2DManager.renderer.setView(game.uiManager.box2DCamera);
        game.uiManager.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        game.uiManager.viewport.update(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void onOpen() {
        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);
        game.soundManager.playMusic("menu", true);
        game.mapManager.loadLevelMap(0, 0);
        game.uiManager.createGamePadSetupTable();
        game.uiManager.infoSetupLabel.setText("Press LEFT or RIGHT ARROW button on your GamePad.");
        state = "wait-for-hAxis";
    }

    @Override
    public void onClose() {
        Controllers.removeListener(this);
        game.soundManager.stopPlayingMusic();
        game.mapManager.unloadLevelMap();
        game.uiManager.removeGamePadSetupTable();
        game.setScreen(game.screenManager.menuScreen);
        ((GameScreen) game.getScreen()).onOpen();
    }

    @Override
    public boolean keyDown(int keycode) {
        log("keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        log("keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        switch (state) {
            case "wait-for-confirm-button":
                game.inputManager.confirmKeyCode = buttonCode;
                game.uiManager.confirmSetupLabel.setText("Key code for CONFIRM BUTTON is : " + buttonCode);
                game.uiManager.infoSetupLabel.setText("Press CANCEL (BACK) BUTTON on your GamePad.");
                state = "wait-for-cancel-button";
                break;
            case "wait-for-cancel-button":
                game.inputManager.cancelKeyCode = buttonCode;
                game.uiManager.cancelSetupLabel.setText("Key code for CANCEL BUTTON is : " + buttonCode);
                game.uiManager.infoSetupLabel.setText("CONFIRM to continue or CANCEL to setup again.");
                state = "wait-for-exit";
                break;
            default:
                break;
        }//switch state
        if (buttonCode == game.inputManager.confirmKeyCode && state == "wait-for-exit")
            onClose();
        if (buttonCode == game.inputManager.cancelKeyCode && state == "wait-for-exit") {
            game.uiManager.infoSetupLabel.setText("Press LEFT or RIGHT ARROW button on your GamePad.");
            state = "wait-for-hAxis";
        }//if cancel
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        switch (state) {
            case "wait-for-vAxis":
                if (Math.abs(value) == 1.0f) {
                    game.inputManager.vAxisKeyCode = axisCode;
                    game.uiManager.vAxisSetupLabel.setText("Key code for VERTICAL AXIS is : " + axisCode);
                    game.uiManager.infoSetupLabel.setText("Press CONFIRM (OK) BUTTON on your GamePad.");
                    state = "wait-for-confirm-button";
                }//if
                break;
            case "wait-for-hAxis":
                if (Math.abs(value) == 1.0f) {
                    game.inputManager.hAxisKeyCode = axisCode;
                    game.uiManager.hAxisSetupLabel.setText("Key code for HORIZONTAL AXIS is : " + axisCode);
                    game.uiManager.infoSetupLabel.setText("Press UP or DOWN ARROW button on your GamePad.");
                    state = "wait-for-vAxis";
                }//if
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
