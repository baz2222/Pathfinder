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

public class LevelScreen extends GameScreen {
    private GapGame2 game;

    public LevelScreen(GapGame2 game) {
        this.game = game;
    }

    public void handleInput() {
        //jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isTouched() && Gdx.input.getDeltaY() < -10)) {
            if (game.characterManager.player.ability == "jump")
                game.characterManager.player.jump(1.5f);
            else
                game.characterManager.player.jump(1f);
        }//move right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isTouched() && Gdx.input.getDeltaX() > 10))
            game.characterManager.player.runRight();
        //move left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched() && Gdx.input.getDeltaX() < -10))
            game.characterManager.player.runLeft();
        //pause
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.inputManager.inputEvent.setType(InputEvent.Type.touchDown);
            game.uiManager.pauseBtn.fire(game.inputManager.inputEvent);
            game.soundManager.playSound("bomb", false);
        }
    }

    @Override
    public void render(float delta) {
        if (game.box2DManager.stopWorldStep == true) {
            game.levelManager.removeLevelActors();
        } else {
            handleInput();
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
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
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
