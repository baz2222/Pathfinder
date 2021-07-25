package com.baz2222.pathfinder.manager;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.baz2222.pathfinder.Pathfinder;

public class Box2DManager {

    public short emptyBit = 1;
    public short playerBit = 2;
    public short exitBit = 4;
    public short removedBit = 8;
    public short groundBit = 16;
    public short crumbleBit = 32;
    public short spikeBit = 64;
    public short enemyBit = 128;
    public short enemy2Bit = 256;
    public short directionBit = 512;
    public short switchBit = 1024;
    public short buffBit = 2048;
    public short bumpBit = 4096;

    private Pathfinder game;

    public World world;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer debugRenderer;
    public boolean stopWorldStep = false;

    public Box2DManager(Pathfinder game) {
        this.game = game;
        debugRenderer = new Box2DDebugRenderer();
        //world = new World(new Vector2(0, -20), true);
        //world.setContactListener(new WorldContactListener(game));
    }
}
