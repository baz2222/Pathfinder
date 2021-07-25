package com.baz2222.pathfinder.manager;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.character.Enemy;
import com.baz2222.pathfinder.character.Enemy2;
import com.baz2222.pathfinder.listeners.WorldContactListener;
import com.baz2222.pathfinder.map.Crumble;
import com.baz2222.pathfinder.map.Direction;
import com.baz2222.pathfinder.map.Ground;
import com.baz2222.pathfinder.map.Spike;

public class MapManager {
    private Pathfinder game;

    private TmxMapLoader loader;
    public TiledMap map;
    public Rectangle mapActorBounds;
    public Array<com.baz2222.pathfinder.map.Ground> grounds;
    public Array<com.baz2222.pathfinder.map.Direction> directions;
    public Array<com.baz2222.pathfinder.map.Crumble> crumbles;
    public Array<com.baz2222.pathfinder.map.Spike> spikes;

    private Filter filter;

    public MapManager(Pathfinder game) {
        this.game = game;
        mapActorBounds = new Rectangle();
        map = new TiledMap();
        loader = new TmxMapLoader();
        grounds = new Array<>();
        crumbles = new Array<>();
        directions = new Array<>();
        spikes = new Array<>();
        filter = new Filter();
    }

    public void loadLevelMap(int world, int level){
        map = loader.load("level" + world + "-" + level + ".tmx");
        game.box2DManager.world = new World(new Vector2(0, -20), true);
        game.box2DManager.world.setContactListener(new WorldContactListener(game));

        game.box2DManager.renderer = new OrthogonalTiledMapRenderer(map, 1/game.scale);
        for (MapObject object : map.getLayers().get("GroundObjectsLayer").getObjects().getByType(RectangleMapObject.class)) {
            mapActorBounds = ((RectangleMapObject) object).getRectangle();
            game.mapManager.grounds.add(new Ground(game, "ground"));
        }
        for (MapObject object : map.getLayers().get("CrumbleObjectsLayer").getObjects().getByType(RectangleMapObject.class)) {
            mapActorBounds = ((RectangleMapObject) object).getRectangle();
            game.mapManager.crumbles.add(new com.baz2222.pathfinder.map.Crumble(game, "crumble"));
        }
        for (MapObject object : map.getLayers().get("SpikesObjectsLayer").getObjects().getByType(RectangleMapObject.class)) {
            mapActorBounds = ((RectangleMapObject) object).getRectangle();
            game.mapManager.spikes.add(new Spike(game, "spike"));
        }
        for (MapObject object : map.getLayers().get("ChangeEnemyDirectionObjectsLayer").getObjects().getByType(RectangleMapObject.class)) {
            mapActorBounds = ((RectangleMapObject) object).getRectangle();
            game.mapManager.directions.add(new Direction(game, "direction"));
        }
    }

    public void unloadLevelMap(){
        game.mapManager.map = null;
    }

    public void onPlayerHitCrumble(Crumble crumble){
        if(game.characterManager.player.ability == "bomb") {
            filter.categoryBits = game.box2DManager.removedBit;
            crumble.fixture.setFilterData(filter);
            crumble.getCell().setTile(null);
            game.soundManager.playSound("bomb",false);
        }//if bomb ability
    }

    public void onPlayerHitSpike(){
        game.soundManager.playSound("die", false);
        game.characterManager.player.filter.categoryBits = game.box2DManager.removedBit;
        game.characterManager.player.fixture.setFilterData(game.characterManager.player.filter);
        Pathfinder.log("player hit spike");
    }

    public void onEnemyHitDirection(Enemy enemy){
        enemy.runRight = !enemy.runRight;
        Pathfinder.log("enemy hit direction");
    }

    public void onEnemy2HitDirection(Enemy2 enemy2){
        enemy2.runRight = !enemy2.runRight;
        Pathfinder.log("enemy2 hit direction");
    }
}
