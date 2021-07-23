package com.baz2222.gap2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.tools.GameScreen;

public class PauseScreen extends GameScreen {
    private GapGame2 game;
    public PauseScreen(GapGame2 game) {
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
}
