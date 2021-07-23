package com.baz2222.gap2.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;

public class Buff extends Actor {
    private GapGame2 game;
    private Texture texture;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> animation;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private PolygonShape shape;
    private float timer;
    public Filter filter;

    public Buff(GapGame2 game, float buffX, float buffY, String name) {
        this.game = game;
        setName(name);
        timer = 0;
        filter = new Filter();
        if(name == "bomb"){
            texture = game.graphicsManager.bombBuffTex;
        }
        if(name == "shield"){
            texture = game.graphicsManager.shieldBuffTex;
        }
        if(name == "jump"){
            texture = game.graphicsManager.jumpBuffTex;
        }
        bodyDef = new BodyDef();
        bodyDef.position.set(buffX / game.scale, buffY / game.scale);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = game.box2DManager.world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(24 / game.scale, 30 / game.scale);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = game.box2DManager.playerBit;//collides with ---
        fixtureDef.filter.categoryBits = game.box2DManager.buffBit;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        filter = fixtureDef.filter;
        frames = new Array<>();
        for (int i = 0; i < 7; i++) {
            frames.add(new TextureRegion(texture, i * 64, 0, 64, 64));
        }//for
        animation = new Animation(0.08f, frames);//0.08
        frames.clear();
        setBounds(0, 0, 64 / game.scale, 64 / game.scale);
        setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.35f) * game.scale);
    }//constructor

    public TextureRegion getFrame() {
        timer = timer + Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(timer, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(game.gamePaused == false) {
            setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.35f) * game.scale);
            batch.draw(getFrame(), getX(), getY());
        }//if game not paused
    }//draw

    @Override
    public boolean remove() {
        return super.remove();
    }
}
