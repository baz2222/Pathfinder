package com.baz2222.pathfinder.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.tools.GameScreen;

public class PauseScreen extends GameScreen {
    private Pathfinder game;
    public PauseScreen(Pathfinder game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.box2DManager.renderer.render();
        game.uiManager.box2DCamera.update();
        game.uiManager.batch.setProjectionMatrix(game.uiManager.box2DCamera.combined);
        game.box2DManager.renderer.setView(game.uiManager.box2DCamera);
        game.uiManager.stage.draw();
    }

    public void handleInput(){
        //move right
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            game.inputManager.nextISA();
        //move left
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            game.inputManager.previousISA();
        //press current button
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.inputManager.inputEvent.setType(InputEvent.Type.touchDown);
            game.inputManager.getCurrentISA().fire(game.inputManager.inputEvent);
            game.soundManager.playSound("bomb", false);
        }//enter
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
        game.gamePaused = true;
        game.uiManager.createPauseTable();
    }

    @Override
    public void onClose() {
        game.gamePaused = false;
        game.uiManager.removePauseTable();
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
