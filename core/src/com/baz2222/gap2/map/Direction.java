package com.baz2222.gap2.map;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.baz2222.gap2.GapGame2;

public class Direction extends Actor {
    private GapGame2 game;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    public Body body;

    public Direction(GapGame2 game, String name) {
        this.game = game;
        setName(name);
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        shape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((game.mapManager.mapActorBounds.getX() + game.mapManager.mapActorBounds.getWidth() / 2) / game.scale, (game.mapManager.mapActorBounds.getY() + game.mapManager.mapActorBounds.getHeight() / 2) / game.scale);
        body = game.box2DManager.world.createBody(bodyDef);
        fixtureDef.filter.maskBits = (short) (game.box2DManager.enemyBit | game.box2DManager.enemy2Bit);
        fixtureDef.filter.categoryBits = game.box2DManager.directionBit;
        shape.setAsBox((game.mapManager.mapActorBounds.getWidth() / 2) / game.scale, (game.mapManager.mapActorBounds.getHeight() / 2) / game.scale);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.8f;
        fixtureDef.density = 0f;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }
}
