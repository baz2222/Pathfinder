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

public class Bump extends Actor {
    private Pathfinder game;
    private Texture texture;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> checkedBumpAnimation, uncheckedBumpAnimation;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private PolygonShape shape;
    private float timer;
    public float hitTimer;
    public boolean isHitted;

    public Bump(Pathfinder game, float bumpX, float bumpY, String name) {
        this.game = game;
        setName(name);
        timer = 0;
        hitTimer = 0;
        texture = game.itemManager.bumpAtlas.findRegion("bump").getTexture();
        bodyDef = new BodyDef();
        bodyDef.position.set(bumpX / game.scale, bumpY / game.scale);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = game.box2DManager.world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(24 / game.scale, 2 / game.scale);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = game.box2DManager.playerBit;//collides with ---
        fixtureDef.filter.categoryBits = game.box2DManager.bumpBit;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        frames = new Array<>();
        for (int i = 0; i < 7; i++) {
            frames.add(new TextureRegion(texture, i * 64, 0, 64, 64));
        }//for
        checkedBumpAnimation = new Animation(0.08f, frames);//0.08
        frames.clear();
        frames.add(new TextureRegion(texture,0, 0, 64, 64));
        uncheckedBumpAnimation = new Animation(0.08f, frames);//0.08
        frames.clear();
        setBounds(0, 0, 64 / game.scale, 64 / game.scale);
        setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.03f) * game.scale);
    }//constructor

    public TextureRegion getFrame() {
        timer = timer + Gdx.graphics.getDeltaTime();
        if (isHitted) {
            hitTimer = hitTimer + Gdx.graphics.getDeltaTime();
            //hit animation duration handling
            if(hitTimer > 0.5f){
                isHitted = false;
                hitTimer = 0;
            }//timer for stopping animation
            return checkedBumpAnimation.getKeyFrame(timer, true);
        } else {
            return uncheckedBumpAnimation.getKeyFrame(timer, false);
        }//else
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (game.gamePaused == false) {
            setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.03f) * game.scale);
            batch.draw(getFrame(), getX(), getY());
        }//if game not paused
    }//draw

    @Override
    public boolean remove() {
        return super.remove();
    }
}
