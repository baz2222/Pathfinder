package com.baz2222.gap2.manager;

import com.badlogic.gdx.graphics.Texture;
import com.baz2222.gap2.GapGame2;
import com.badlogic.gdx.assets.AssetManager;

public class GraphicsManager {
    private GapGame2 game;
    private AssetManager manager;

    public Texture menuBtnTex, playMenuBtnTex, backBtnTex, selectWorldBtnTex, selectLevelBtnTex, leftArrowBtnTex, rightArrowTex;
    public Texture jumpBuffTex, shieldBuffTex, bombBuffTex;

    public GraphicsManager(GapGame2 game) {
        this.game = game;
        manager = new AssetManager();
        loadGraphics();
        createBtnTextures();
    }

    public void loadGraphics(){
        manager.load("menu-btn.png", Texture.class);
        manager.load("play-menu-btn.png", Texture.class);
        manager.load("select-world-btn.png", Texture.class);
        manager.load("select-level-btn.png", Texture.class);
        manager.load("back-btn.png", Texture.class);
        manager.load("left-arrow-btn.png", Texture.class);
        manager.load("right-arrow-btn.png", Texture.class);

        manager.load("jump-buff.png", Texture.class);
        manager.load("bomb-buff.png", Texture.class);
        manager.load("shield-buff.png", Texture.class);

        manager.finishLoading();
    }

    public void createBtnTextures(){
        menuBtnTex = manager.get("menu-btn.png", Texture.class);
        playMenuBtnTex = manager.get("play-menu-btn.png", Texture.class);
        backBtnTex = manager.get("back-btn.png", Texture.class);
        selectWorldBtnTex = manager.get("select-world-btn.png", Texture.class);
        selectLevelBtnTex = manager.get("select-level-btn.png", Texture.class);
        leftArrowBtnTex = manager.get("left-arrow-btn.png", Texture.class);
        rightArrowTex = manager.get("right-arrow-btn.png", Texture.class);

        jumpBuffTex = manager.get("jump-buff.png", Texture.class);
        bombBuffTex = manager.get("bomb-buff.png", Texture.class);
        shieldBuffTex = manager.get("shield-buff.png", Texture.class);
    }
}
