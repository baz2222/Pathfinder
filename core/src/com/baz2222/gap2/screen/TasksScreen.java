package com.baz2222.gap2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.tools.GameScreen;

import static com.baz2222.gap2.GapGame2.log;

public class TasksScreen extends GameScreen {
    private GapGame2 game;
    public TasksScreen(GapGame2 game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0,0,0,1);
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
        game.soundManager.playMusic("menu", true);
        game.mapManager.loadLevelMap(0,0);
        game.uiManager.createTasksTable();
        log("tasks screen opened");
    }

    @Override
    public void onClose() {
        game.soundManager.stopPlayingMusic();
        game.uiManager.removeTasksTable();
        game.mapManager.unloadLevelMap();
        log("tasks screen closed");
    }
}
