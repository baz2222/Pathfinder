package com.baz2222.pathfinder.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.baz2222.pathfinder.Pathfinder;

public class Switch extends Actor {
    private Pathfinder game;
    private Texture texture;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> checkedSwitchAnimation, uncheckedSwitchAnimation;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private PolygonShape shape;
    private float timer;
    public boolean isChecked;

    public Switch(Pathfinder game, float swX, float swY,String name, Actor target) {
        this.game = game;
        setName(name);
        timer = 0;
        isChecked = false;
        texture = game.itemManager.switchAtlas.findRegion("switch").getTexture();
        bodyDef = new BodyDef();
        bodyDef.position.set(swX / game.scale, swY / game.scale);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = game.box2DManager.world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(24 / game.scale, 36 / game.scale);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = game.box2DManager.playerBit;//collides with ---
        fixtureDef.filter.categoryBits = game.box2DManager.switchBit;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        frames = new Array<>();
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(texture, i * 64, 0, 64, 76));
        }//for
        uncheckedSwitchAnimation = new Animation(0.08f, frames);//0.08
        frames.clear();
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(texture, i * 64, 76, 64, 76));
        }//for
        checkedSwitchAnimation = new Animation(0.08f, frames);//0.08
        frames.clear();
        setBounds(0, 0, 64 / game.scale, 76 / game.scale);
        setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() / 2) * game.scale);
    }//constructor

    public TextureRegion getFrame() {
        timer = timer + Gdx.graphics.getDeltaTime();
        if(isChecked){
            return checkedSwitchAnimation.getKeyFrame(timer, true);
        }else{
            return uncheckedSwitchAnimation.getKeyFrame(timer, true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (game.gamePaused == false) {
            setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() / 2) * game.scale);
            batch.draw(getFrame(), getX(), getY());
        }//if game not paused
    }//draw

    @Override
    public boolean remove() {
        return super.remove();
    }
}
