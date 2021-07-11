package com.baz2222.gap2.manager;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.listeners.WorldContactListener;

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
    public short lampBit = 1024;
    public short buffBit = 2048;
    public short bumpBit = 4096;

    private GapGame2 game;

    public World world;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer debugRenderer;
    public boolean stopWorldStep = false;

    public Box2DManager(GapGame2 game) {
        this.game = game;
        debugRenderer = new Box2DDebugRenderer();
        //world = new World(new Vector2(0, -20), true);
        //world.setContactListener(new WorldContactListener(game));
    }
}
