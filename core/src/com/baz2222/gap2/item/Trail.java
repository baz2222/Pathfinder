package com.baz2222.gap2.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;

public class Trail extends Actor {
    private GapGame2 game;
    private Texture texture;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> animation;
    private float timer;

    public Trail(GapGame2 game, String name) {
        this.game = game;
        setName(name);
        timer = 0;
        if (name == "bomb") {
            texture = game.graphicsManager.bombTrailTex;
        }
        if (name == "shield") {
            texture = game.graphicsManager.shieldTrailTex;
        }
        if (name == "jump") {
            texture = game.graphicsManager.jumpTrailTex;
        }
        frames = new Array<>();
        for (int i = 0; i < 15; i++) {
            frames.add(new TextureRegion(texture, i * 64, 0, 64, 64));
        }//for
        animation = new Animation(0.2f, frames);//0.08
        frames.clear();
        setBounds(0, 0, 64 / game.scale, 64 / game.scale);
        setPosition(game.characterManager.player.getX() - getWidth() / 2, game.characterManager.player.getY() - getHeight() * 0.45f);
        game.uiManager.stage.addActor(this);//add trail to stage
    }//constructor

    public TextureRegion getFrame() {
        timer = timer + Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(timer, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (game.gamePaused == false) {
            //setPosition(game.characterManager.player.getX() - getWidth() / 2, game.characterManager.player.getY() - getHeight() * 0.45f);
            batch.draw(getFrame(), getX(), getY());
        }//if game not paused
    }//draw

    @Override
    public boolean remove() {
        return super.remove();
    }
}
