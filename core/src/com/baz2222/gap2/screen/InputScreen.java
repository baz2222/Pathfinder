package com.baz2222.gap2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.tools.GameScreen;
import com.baz2222.gap2.tools.GPadKeyMap;

import static com.baz2222.gap2.GapGame2.log;

public class InputScreen extends GameScreen {
    private GapGame2 game;
    public boolean deviceListInFocus;
    public boolean keyListInFocus;
    public boolean isWaitForLeftKey, isWaitForRightKey, isWaitForUpKey, isWaitForDownKey, isWaitForConfirmKey, isWaitForCancelKey;

    public InputScreen(GapGame2 game) {
        this.game = game;
        deviceListInFocus = false;
        keyListInFocus = false;
        resetSequence();
    }//constructor

    public void resetSequence() {
        isWaitForLeftKey = true;
        isWaitForRightKey = false;
        isWaitForUpKey = false;
        isWaitForDownKey = false;
        isWaitForConfirmKey = false;
        isWaitForCancelKey = false;
    }

    @Override
    public void render(float delta) {
        handleInput();
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
        deviceListInFocus = false;
        game.soundManager.playMusic("menu", true);
        game.mapManager.loadLevelMap(0, 0);
        game.uiManager.createInputTable();
        log("input screen opened");
    }//on open

    public void onDeviceSelected() {
        game.uiManager.inputInfoLabel.setText("CURRENT DEVICE: " + game.uiManager.list.getSelected());
        for (GPadKeyMap map : game.inputManager.GPadKeyMaps) {
            if (map.name == game.uiManager.list.getSelected()) {
                game.inputManager.currentGPadKeyMap = map;
            }//if
        }//for
        for (Controller c : game.inputManager.GPads) {
            if (c.getName() == game.inputManager.currentGPadKeyMap.name) {
                game.inputManager.currentGPad = c;
            }//if
        }//for
        if (game.inputManager.currentGPadKeyMap != null) {
            game.uiManager.leftKeyLabel.setText("Left Key : " + game.inputManager.currentGPadKeyMap.left);
            game.uiManager.rightKeyLabel.setText("Right Key : " + game.inputManager.currentGPadKeyMap.right);
            game.uiManager.upKeyLabel.setText("Up Key : " + game.inputManager.currentGPadKeyMap.up);
            game.uiManager.downKeyLabel.setText("Down Key : " + game.inputManager.currentGPadKeyMap.down);
            game.uiManager.confirmKeyLabel.setText("OK Key : " + game.inputManager.currentGPadKeyMap.confirm);
            game.uiManager.cancelKeyLabel.setText("Back Key : " + game.inputManager.currentGPadKeyMap.cancel);
        }//current GPadKeyMap is not null
    }// on device selected

    public void handleInput() {
        if (deviceListInFocus == true) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                log("selected index" + game.uiManager.list.getSelectedIndex());
                if (game.uiManager.list.getSelectedIndex() < game.uiManager.list.getItems().size - 1)
                    game.uiManager.list.setSelectedIndex(game.uiManager.list.getSelectedIndex() + 1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                log("selected index" + game.uiManager.list.getSelectedIndex());
                if (game.uiManager.list.getSelectedIndex() > 0)
                    game.uiManager.list.setSelectedIndex(game.uiManager.list.getSelectedIndex() - 1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                deviceListInFocus = false;
                game.inputManager.setCurrentISA(1);
                game.soundManager.playSound("bomb", false);
                onDeviceSelected();
            }//enter
        } else if (keyListInFocus) {
            if (isWaitForLeftKey) {
                game.uiManager.inputInfoLabel.setText("Press Game Pad button for LEFT");
            }//if is
        } else {
            //move right
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                game.inputManager.nextISA();
            //move left
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
                game.inputManager.previousISA();
            //press current button
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                game.inputManager.inputEvent.setType(InputEvent.Type.touchDown);
                game.inputManager.getCurrentISA().fire(game.inputManager.inputEvent);
                game.inputManager.inputEvent.setType(InputEvent.Type.touchUp);
                game.inputManager.getCurrentISA().fire(game.inputManager.inputEvent);
                game.soundManager.playSound("bomb", false);
            }//enter
        }//if device list and key list is not in focus
    }

    @Override
    public void onClose() {
        game.soundManager.stopPlayingMusic();
        game.uiManager.removeInputTable();
        game.mapManager.unloadLevelMap();
        log("input screen closed");
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
        if (controller == game.inputManager.currentGPad) {
            log("current gamepad button is pressed " + buttonCode);
        }// if is current gamepad
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if (controller == game.inputManager.currentGPad) {
            log("current gamepad button is pressed " + axisCode + " " + value);
        }// if is current gamepad
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
