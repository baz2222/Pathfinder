package com.baz2222.gap2.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.manager.CharacterManager;
import com.baz2222.gap2.tools.GameScreen;

import java.util.HashMap;

import static com.baz2222.gap2.GapGame2.log;

public class Enemy extends Actor {
    private GapGame2 game;
    private float stepTimer, trailTimer, timer;
    private Texture texture;
    private CharacterManager.State currState, prevState;
    public boolean runRight;
    public float jumpPower;
    private Array<TextureRegion> frames;
    private HashMap<String, Animation<TextureRegion>> animations;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    public Filter filter;
    private PolygonShape shape;
    private TextureRegion region;

    public Enemy(GapGame2 game, String name, float enemyX, float enemyY, boolean runRight) {
        this.game = game;
        setName(name);
        stepTimer = 0;
        trailTimer = 0;
        timer = 0;
        filter = new Filter();
        texture = game.characterManager.enemyAtlas.findRegion("enemy").getTexture();
        animations = new HashMap<>();
        currState = CharacterManager.State.idle;
        prevState = CharacterManager.State.idle;
        this.runRight = runRight;
        jumpPower = 1f;

        frames = new Array<>();
        //run animation
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(texture, i * 64, 0, 64, 64));
        }//for
        animations.put("enemyRun", new Animation<>(0.05f, frames));
        frames.clear();
        //jump animation
        frames.add(new TextureRegion(texture, 14 * 64, 0, 64, 64));
        animations.put("enemyJump", new Animation<>(0.1f, frames));
        frames.clear();
        //fall animation
        frames.add(new TextureRegion(texture, 13 * 64, 0, 64, 64));
        animations.put("enemyFall", new Animation<>(0.1f, frames));
        frames.clear();
        //idle animation
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(texture, i * 64, 64, 64, 64));
        }//for
        animations.put("enemyIdle", new Animation<>(0.1f, frames));
        frames.clear();
        //die animation
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(texture, i * 64, 128, 64, 64));
        }//for
        animations.put("enemyDie", new Animation<>(0.1f, frames));
        frames.clear();
        bodyDef = new BodyDef();
        bodyDef.position.set(enemyX / game.scale, enemyY / game.scale);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = game.box2DManager.world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(14 / game.scale, 24 / game.scale);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        fixtureDef.filter.maskBits = (short) (game.box2DManager.playerBit | game.box2DManager.groundBit | game.box2DManager.crumbleBit | game.box2DManager.directionBit);//collides with ---
        fixtureDef.filter.categoryBits = game.box2DManager.enemyBit;
        //fixtureDef.restitution = 0f;
        //fixtureDef.friction = 0.5f;
        //fixtureDef.density = 0f;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        setBounds(0, 0, 64 / game.scale, 64 / game.scale);
    }//constructor

    public void jump() {
        if (currState != CharacterManager.State.jump && currState != CharacterManager.State.fall) {
            body.applyLinearImpulse(new Vector2(0f, 6.5f * jumpPower), body.getWorldCenter(), true);
            game.soundManager.playSound("jump", false);
        }//if not jumping
    }//jump

    public void runRight() {
        if (body.getLinearVelocity().x <= 2)
            body.applyLinearImpulse(new Vector2(0.2f, 0f), body.getWorldCenter(), true);
    }//run right

    public void runLeft() {
        if (body.getLinearVelocity().x >= -2)
            body.applyLinearImpulse(new Vector2(-0.2f, 0f), body.getWorldCenter(), true);
    }//run right

    public void run(){
        if(runRight)
            runRight();
        else
            runLeft();
    }

    public void updateDirection() {
        if ((body.getLinearVelocity().x < 0 || !runRight) && !region.isFlipX()) {
            region.flip(true, false);
            runRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runRight) && region.isFlipX()) {
            region.flip(true, false);
            runRight = true;
        }//if
    }

    public CharacterManager.State getState() {
        if (fixture.getFilterData().categoryBits == game.box2DManager.removedBit)
            return CharacterManager.State.die;
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y > 0 && prevState == CharacterManager.State.jump))
            return CharacterManager.State.jump;
        else if (body.getLinearVelocity().y < 0)
            return CharacterManager.State.fall;
        else if (body.getLinearVelocity().x > 0)
            return CharacterManager.State.right;
        else if (body.getLinearVelocity().x < 0)
            return CharacterManager.State.left;
        else return CharacterManager.State.idle;
    }//get state

    public TextureRegion getFrame() {
        currState = getState();
        switch (currState) {
            case jump:
                region = animations.get("enemyJump").getKeyFrame(timer);
                break;
            case left:
                region = animations.get("enemyRun").getKeyFrame(timer, true);
                break;
            case right:
                region = animations.get("enemyRun").getKeyFrame(timer, true);
                break;
            case fall:
                region = animations.get("enemyFall").getKeyFrame(timer, true);
                break;
            case die:
                region = animations.get("enemyDie").getKeyFrame(timer, true);
                break;
            case idle:
            default:
                region = animations.get("enemyIdle").getKeyFrame(timer, true);
                break;
        }//switch
        updateDirection();
        timer = currState == prevState ? timer + Gdx.graphics.getDeltaTime() : 0;
        prevState = currState;
        return region;
    }//get frame

    public void die() {
        game.stateManager.killed++;
        game.taskManager.checkForCompleted();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(game.gamePaused == false) {
            if (fixture.getFilterData().categoryBits == game.box2DManager.removedBit && body.isActive()) {
                if (body.getPosition().y * game.scale < game.height + 64)  // +player height
                {
                    body.setTransform(body.getPosition().x, body.getPosition().y + Gdx.graphics.getDeltaTime() * 2, 0);
                    setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.43f) * game.scale);
                    batch.draw(getFrame(), getX(), getY());
                } else {//fly to the top
                    body.setActive(false);
                    die();
                }//die animation end - next restart level
            } else {
                run();//enemy always running
                setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.43f) * game.scale);
                game.characterManager.onEnemyWarp(this);
                batch.draw(getFrame(), getX(), getY());
            }//if not dead
        }//if not paused
    }//draw

    @Override
    public boolean remove() {
        return super.remove();
    }
}
