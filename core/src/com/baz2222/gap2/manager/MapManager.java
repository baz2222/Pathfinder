package com.baz2222.gap2.manager;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.character.Enemy;
import com.baz2222.gap2.character.Enemy2;
import com.baz2222.gap2.listeners.WorldContactListener;
import com.baz2222.gap2.map.Crumble;
import com.baz2222.gap2.map.Direction;
import com.baz2222.gap2.map.Ground;
import com.baz2222.gap2.map.Spike;

import static com.baz2222.gap2.GapGame2.log;

public class MapManager {
    private GapGame2 game;

    private TmxMapLoader loader;
    public TiledMap map;
    public Rectangle mapActorBounds;
    public Array<Ground> grounds;
    public Array<Direction> directions;
    public Array<Crumble> crumbles;
    public Array<Spike> spikes;

    public MapManager(GapGame2 game) {
        this.game = game;
        mapActorBounds = new Rectangle();
        map = new TiledMap();
        loader = new TmxMapLoader();
        grounds = new Array<>();
        crumbles = new Array<>();
        directions = new Array<>();
        spikes = new Array<>();
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
            game.mapManager.crumbles.add(new Crumble(game, "crumble"));
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

    public void onPlayerHitSpike(){
        game.soundManager.playSound("die", false);
        game.characterManager.player.filter.categoryBits = game.box2DManager.removedBit;
        game.characterManager.player.fixture.setFilterData(game.characterManager.player.filter);
        log("player hit spike");
    }

    public void onEnemyHitDirection(Enemy enemy){
        enemy.runRight = !enemy.runRight;
        log("enemy hit direction");
    }

    public void onEnemy2HitDirection(Enemy2 enemy2){
        enemy2.runRight = !enemy2.runRight;
        log("enemy2 hit direction");
    }
}
