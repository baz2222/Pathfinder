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

public class Player extends Actor {
    private GapGame2 game;
    private float stepTimer, trailTimer, timer;
    private Texture texture;
    public CharacterManager.State currState, prevState;
    private boolean runRight;
    public float jumpPower;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> playerIdle, playerRun, playerJump, playerFall, playerDie;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    public Filter filter;
    private PolygonShape shape;
    private TextureRegion region;
    public String ability;

    public Player(GapGame2 game, String name) {
        this.game = game;
        setName(name);
        stepTimer = 0;
        trailTimer = 0;
        timer = 0;
        filter = new Filter();
        texture = game.characterManager.playerAtlas.findRegion("player").getTexture();
        currState = CharacterManager.State.idle;
        prevState = CharacterManager.State.idle;
        runRight = true;
        jumpPower = 1f;
        ability = "simple";
        frames = new Array<>();
        //run animation
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(texture, i * 64, 0, 64, 64));
        }//for
        playerRun = new Animation<>(0.05f, frames);
        frames.clear();
        //jump animation
        frames.add(new TextureRegion(texture, 14 * 64, 0, 64, 64));
        playerJump = new Animation<>(0.1f, frames);
        frames.clear();
        //fall animation
        frames.add(new TextureRegion(texture, 13 * 64, 0, 64, 64));
        playerFall = new Animation<>(0.1f, frames);
        frames.clear();
        //idle animation
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(texture, i * 64, 64, 64, 64));
        }//for
        playerIdle = new Animation<>(0.1f, frames);
        frames.clear();
        //die animation
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(texture, i * 64, 128, 64, 64));
        }//for
        playerDie = new Animation<>(0.1f, frames);
        frames.clear();
        bodyDef = new BodyDef();
        bodyDef.position.set(game.levelManager.startCoords.x / game.scale, game.levelManager.startCoords.y / game.scale);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = game.box2DManager.world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(14 / game.scale, 24 / game.scale);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        fixtureDef.filter.maskBits = (short) (game.box2DManager.exitBit | game.box2DManager.groundBit | game.box2DManager.spikeBit | game.box2DManager.enemyBit | game.box2DManager.enemy2Bit);//collides with ---
        fixtureDef.filter.categoryBits = game.box2DManager.playerBit;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.density = 0f;
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
            body.applyLinearImpulse(new Vector2(1f, 0f), body.getWorldCenter(), true);
    }//run right

    public void runLeft() {
        if (body.getLinearVelocity().x >= -2)
            body.applyLinearImpulse(new Vector2(-1f, 0f), body.getWorldCenter(), true);
    }//run right


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
                region = playerJump.getKeyFrame(timer);
                break;
            case left:
                region = playerRun.getKeyFrame(timer, true);
                break;
            case right:
                region = playerRun.getKeyFrame(timer, true);
                break;
            case fall:
                region = playerFall.getKeyFrame(timer);
                break;
            case die:
                region = playerDie.getKeyFrame(timer, true);
                break;
            case idle:
            default:
                region = playerIdle.getKeyFrame(timer, true);
                break;
        }//switch
        updateDirection();
        timer = currState == prevState ? timer + Gdx.graphics.getDeltaTime() : 0;
        prevState = currState;
        return region;
    }//get frame

    public void die() {
        game.stateManager.died++;
        game.taskManager.checkForCompleted();
        game.screenManager.levelScreen.onClose();
        game.screenManager.levelOverScreen.type = "fail";
        game.setScreen(game.screenManager.levelOverScreen);
        ((GameScreen)game.getScreen()).onOpen();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(game.gamePaused == false) {
            if (fixture.getFilterData().categoryBits == game.box2DManager.removedBit) {
                body.setActive(false);
                if (body.getPosition().y * game.scale < game.height + 64)  // +player height
                {
                    body.setTransform(body.getPosition().x, body.getPosition().y + Gdx.graphics.getDeltaTime() * 2, 0);
                    setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.43f) * game.scale);
                    batch.draw(getFrame(), getX(), getY());
                } else {//fly to the top
                    die();
                }//die animation end - next restart level
            } else {
                setPosition((body.getPosition().x - getWidth() / 2) * game.scale, (body.getPosition().y - getHeight() * 0.43f) * game.scale);
                game.characterManager.onPalyerWarp();
                batch.draw(getFrame(), getX(), getY());
            }//if not dead
            stepTimer += Gdx.graphics.getDeltaTime();
            if(stepTimer >= 0.3f && (currState == CharacterManager.State.left || currState == CharacterManager.State.right)){
                game.soundManager.playSound("step", false);
                stepTimer = 0;
            }//stepping sound effect
        }//if game not paused
    }//draw

    @Override
    public boolean remove() {
        return super.remove();
    }
}
