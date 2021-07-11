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
import com.baz2222.gap2.tools.GameScreen;

import static com.baz2222.gap2.GapGame2.log;

public class Exit extends Actor {
    private GapGame2 game;
    private Texture texture;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> animation;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    private float timer;

    public Exit(GapGame2 game, String name) {
        this.game = game;
        setName(name);
        timer = 0;
        texture = game.itemManager.exitAtlas.findRegion("exit").getTexture();
        bodyDef = new BodyDef();
        bodyDef.position.set(game.levelManager.exitCoords.x / game.scale, game.levelManager.exitCoords.y / game.scale);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = game.box2DManager.world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(20 / game.scale, 45 / game.scale);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = (short) (game.box2DManager.playerBit);//collides with ---
        fixtureDef.filter.categoryBits = game.box2DManager.exitBit;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        frames = new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(texture, i * 64, 0, 64, 96));
        }//for
        animation = new Animation(0.08f, frames);//0.08
        frames.clear();
        setBounds(0, 0, 64 / game.scale, 96 / game.scale);
        setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() / 2) * game.scale);
    }//constructor

    public TextureRegion getFrame() {
        timer = timer + Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(timer, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(game.gamePaused == false) {
            setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() / 2) * game.scale);
            batch.draw(getFrame(), getX(), getY());
        }//if game not paused
    }//draw

    @Override
    public boolean remove() {
        return super.remove();
    }
}
