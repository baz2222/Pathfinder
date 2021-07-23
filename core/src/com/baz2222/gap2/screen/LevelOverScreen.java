package com.baz2222.gap2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.character.Enemy;
import com.baz2222.gap2.tools.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

import static com.baz2222.gap2.GapGame2.log;

public class LevelOverScreen extends GameScreen {
    private GapGame2 game;
    public String type;

    public LevelOverScreen(GapGame2 game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.box2DManager.renderer.render();
        game.uiManager.box2DCamera.update();
        game.uiManager.batch.setProjectionMatrix(game.uiManager.box2DCamera.combined);
        game.box2DManager.renderer.setView(game.uiManager.box2DCamera);
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
        game.mapManager.loadLevelMap(0, 0);
        game.uiManager.createLevelOverTable(type);
        log("level over screen opened");
    }

    @Override
    public void onClose() {
        game.soundManager.stopPlayingMusic();
        game.uiManager.removeLevelOverTable();
        game.mapManager.unloadLevelMap();
        log("level over screen closed");
    }
}
