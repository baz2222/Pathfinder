package com.baz2222.gap2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.tools.GameScreen;

import static com.baz2222.gap2.GapGame2.log;

public class PlayMenuScreen extends GameScreen {
    private GapGame2 game;
    public PlayMenuScreen(GapGame2 game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        handleInput();
        super.render(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.uiManager.box2DCamera.update();
        game.uiManager.batch.setProjectionMatrix(game.uiManager.box2DCamera.combined);
        game.box2DManager.renderer.setView(game.uiManager.box2DCamera);
        game.box2DManager.renderer.render();
        game.box2DManager.debugRenderer.render(game.box2DManager.world, game.uiManager.box2DCamera.combined);
        game.uiManager.stage.draw();
    }

    public void handleInput(){
        //move right
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            game.inputManager.nextISA();
        //move left
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
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
        game.soundManager.playMusic("menu", true);
        game.mapManager.loadLevelMap(0,0);
        game.uiManager.createPlayMenuTable();
        log("play menu screen opened");
    }

    @Override
    public void onClose() {
        game.soundManager.stopPlayingMusic();
        game.uiManager.removePlayMenuTable();
        game.mapManager.unloadLevelMap();
        log("play menu screen closed");
    }
}
