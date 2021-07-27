package com.baz2222.pathfinder.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.tools.GameScreen;

import static com.baz2222.pathfinder.Pathfinder.log;

public class LevelScreen extends GameScreen {
    private Pathfinder game;
    private boolean isPlayerRunRight = false;
    private boolean isPlayerRunLeft = false;

    public LevelScreen(Pathfinder game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        handleRunning();
        if (game.box2DManager.stopWorldStep == true) {
            game.levelManager.removeLevelActors();
        } else {
            game.uiManager.camera.update();
            game.uiManager.box2DCamera.update();
            game.uiManager.batch.setProjectionMatrix(game.uiManager.box2DCamera.combined);
            game.box2DManager.renderer.setView(game.uiManager.box2DCamera);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.box2DManager.renderer.render();
            game.box2DManager.debugRenderer.render(game.box2DManager.world, game.uiManager.box2DCamera.combined);
            game.box2DManager.world.step(1 / 60f, 6, 4);
            game.uiManager.stage.draw();
        }
    }//render

    private void handleRunning() {
        if (isPlayerRunRight)
            game.characterManager.player.runRight();
        if (isPlayerRunLeft)
            game.characterManager.player.runLeft();
    }//handle running

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
        game.uiManager.hideLevelScreenTable();
    }

    @Override
    public void resume() {
        super.resume();
        game.uiManager.showLevelScreenTable();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void onOpen() {
        Controllers.addListener(this);
        game.inputManager.mux.addProcessor(this);
        Gdx.input.setInputProcessor(game.inputManager.mux);
        //=======================
        game.box2DManager.stopWorldStep = false;
        //=======================
        game.soundManager.playMusic("world" + game.currWorld, true);
        //======================
        game.mapManager.loadLevelMap(game.currWorld, game.currLevel);
        //======================
        game.levelManager.loadLevelActors();
        //======================
        game.uiManager.createLevelScreenTable();
        //======================
        game.stateManager.saveState();
        //======================
        if (game.levelManager.tutorialMessage != "")
            game.uiManager.showMessage(game.levelManager.tutorialMessage);
    }

    @Override
    public void onClose() {
        Controllers.removeListener(this);
        game.inputManager.mux.removeProcessor(this);
        //======================
        game.box2DManager.stopWorldStep = true;
        //======================
        game.soundManager.stopPlayingMusic();
        //======================
        game.uiManager.removeLevelScreenTable();
        //======================
        game.mapManager.unloadLevelMap();
        //======================
        game.uiManager.stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        //jump
        if (keycode == Input.Keys.UP) {
            if (game.characterManager.player.ability == "jump") {
                game.characterManager.player.jump(1.5f);
            } else {
                game.characterManager.player.jump(1f);
            }//else
            return true;
        }
        //move right
        if (keycode == Input.Keys.RIGHT) {
            isPlayerRunLeft = false;
            isPlayerRunRight = true;
            return true;
        }//if
        //move left
        if (keycode == Input.Keys.LEFT) {
            isPlayerRunLeft = true;
            isPlayerRunRight = false;
            return true;
        }//if
        //pause
        if (keycode == Input.Keys.ESCAPE) {
            game.inputManager.inputEvent.setType(InputEvent.Type.touchDown);
            game.uiManager.pauseBtn.fire(game.inputManager.inputEvent);
            game.soundManager.playSound("bomb", false);
            return true;
        }
        return false;
    }//key down

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) {
            isPlayerRunLeft = false;
            isPlayerRunRight = false;
            return true;
        }//if
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
        //jump
        if (Gdx.input.getDeltaY() < -20) {
            if (game.characterManager.player.ability == "jump") {
                game.characterManager.player.jump(1.5f);
            } else {
                game.characterManager.player.jump(1f);
            }//else
        }//if
        //runRight
        if (Gdx.input.getDeltaX() > 10) {
            game.characterManager.player.runRight();
        }//if
        //runLeft
        if (Gdx.input.getDeltaX() < -10) {
            game.characterManager.player.runLeft();
        }//if
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
        if (buttonCode == game.inputManager.cancelKeyCode) {
            game.inputManager.inputEvent.setType(InputEvent.Type.touchDown);
            game.uiManager.pauseBtn.fire(game.inputManager.inputEvent);
            game.soundManager.playSound("bomb", false);
            return true;
        }//if cancel
        if (buttonCode == game.inputManager.confirmKeyCode) {
            if (game.characterManager.player.ability == "jump")
                game.characterManager.player.jump(1.5f);
            else
                game.characterManager.player.jump(1f);
        }//if confirm
        return false;
    }//button down

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
//        //jump
//        if (axisCode == game.inputManager.vAxisKeyCode && value == -1) {
//            if (game.characterManager.player.ability == "jump")
//                game.characterManager.player.jump(1.5f);
//            else
//                game.characterManager.player.jump(1f);
//        }//if
        //move right
        if (axisCode == game.inputManager.hAxisKeyCode && value == 1) {
            isPlayerRunLeft = false;
            isPlayerRunRight = true;
        }//if
        //move left
        if (axisCode == game.inputManager.hAxisKeyCode && value == -1) {
            isPlayerRunLeft = true;
            isPlayerRunRight = false;
        }//if
        if (Math.abs(value) != 1) {
            isPlayerRunLeft = false;
            isPlayerRunRight = false;
        }//if axis button is released
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
