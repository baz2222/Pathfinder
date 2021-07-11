package com.baz2222.gap2.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.character.Enemy;
import com.baz2222.gap2.character.Enemy2;
import com.baz2222.gap2.character.Player;
import com.baz2222.gap2.item.Buff;
import com.baz2222.gap2.item.Exit;

import java.util.Iterator;

import static com.baz2222.gap2.GapGame2.log;

public class LevelManager {
    private GapGame2 game;

    public Vector2 startCoords;
    public Vector2 exitCoords;
    public Array<Vector2> switchesCoords;
    public Array<Vector2> enemiesCoords;
    public Array<Vector2> enemies2Coords;
    public Array<Vector2> bumpsCoords;
    public Array<Vector2> buffBombsCoords;
    public Array<Vector2> buffJumpsCoords;
    public Array<Vector2> buffShieldsCoords;
    public String tutorialMessage;

    private JsonReader reader;
    private JsonValue value;
    private JsonValue json;

    public LevelManager(GapGame2 game) {
        this.game = game;
        startCoords = new Vector2();
        exitCoords = new Vector2();
        switchesCoords = new Array<>();
        enemiesCoords = new Array<>();
        enemies2Coords = new Array<>();
        bumpsCoords = new Array<>();
        buffBombsCoords = new Array<>();
        buffJumpsCoords = new Array<>();
        buffShieldsCoords = new Array<>();
        tutorialMessage = "";

        reader = new JsonReader();
        value = new JsonValue("");
        json = new JsonValue("");
    }

    public void resetCoordsArrays(){
        switchesCoords.clear();
        enemiesCoords.clear();
        enemies2Coords.clear();
        bumpsCoords.clear();
        buffBombsCoords.clear();
        buffJumpsCoords.clear();
        buffShieldsCoords.clear();
    }

    public void removeLevelActors() {
        Array<Body> bodies = new Array<>();
        game.box2DManager.world.getBodies(bodies);
        for (Iterator<Body> iter = bodies.iterator(); iter.hasNext(); ) {
            Body body = iter.next();
            if (body != null) {
                game.box2DManager.world.destroyBody(body);
            }
        }
    }//remove level actors

    public void loadLevelActors() {
        game.characterManager.resetCharactersCount();
        game.itemManager.resetItemsCount();
        game.levelManager.loadLevelActorsCoords(game.currWorld, game.currLevel);
        log("level " + game.currWorld + " - " + game.currLevel + " actors coords loaded");
        game.characterManager.player = new Player(game, "player");
        game.itemManager.exit = new Exit(game, "exit");

        for(Vector2 coords : game.levelManager.enemiesCoords){
            game.characterManager.enemies.add(new Enemy(game, "enemy", coords.x, coords.y, true));
        }
        for(Vector2 coords : game.levelManager.enemies2Coords){
            game.characterManager.enemies2.add(new Enemy2(game, "enemy2", coords.x, coords.y, true));
        }
        for(Vector2 coords : game.levelManager.buffBombsCoords){
            game.itemManager.buffs.add(new Buff(game, coords.x, coords.y, "bomb"));
        }
        for(Vector2 coords : game.levelManager.buffJumpsCoords){
            game.itemManager.buffs.add(new Buff(game, coords.x, coords.y, "jump"));
        }
        for(Vector2 coords : game.levelManager.buffShieldsCoords){
            game.itemManager.buffs.add(new Buff(game, coords.x, coords.y, "shield"));
        }
        //===============================add actors to stage===================================
        game.uiManager.stage.addActor(game.itemManager.exit);
        game.uiManager.stage.addActor(game.characterManager.player);
        for(Enemy enemy : game.characterManager.enemies){
            game.uiManager.stage.addActor(enemy);
        }
        for(Enemy2 enemy2 : game.characterManager.enemies2){
            game.uiManager.stage.addActor(enemy2);
        }
        for(Buff buff : game.itemManager.buffs){
            game.uiManager.stage.addActor(buff);
        }
    }//load level actors

    public void loadLevelActorsCoords(int world, int level){
        resetCoordsArrays();
        json = reader.parse(Gdx.files.internal("level" + world + "-" + level + ".json"));
        value = json.getChild("start");
        startCoords.x = value.asFloat();
        startCoords.y = value.next.asFloat();
        value = json.getChild("exit");
        exitCoords.x = value.asFloat();
        exitCoords.y = value.next.asFloat();
        tutorialMessage = json.getString("tutorial");

        game.itemManager.resetItemsCount();
        game.characterManager.resetCharactersCount();

        value = json.getChild("buffShields");
        while(value!=null){
            buffShieldsCoords.add(new Vector2(value.get("x").asFloat(),value.get("y").asFloat()));
            value = value.next;
            game.itemManager.buffShieldsCount++;
        }
        value = json.getChild("switches");
        while(value!=null){
            switchesCoords.add(new Vector2(value.get("x").asFloat(),value.get("y").asFloat()));
            value = value.next;
            game.itemManager.switchesCount++;
        }
        value = json.getChild("enemies");
        while(value!=null){
            enemiesCoords.add(new Vector2(value.get("x").asFloat(),value.get("y").asFloat()));
            value = value.next;
            game.characterManager.enemiesCount++;
        }
        value = json.getChild("enemies2");
        while(value!=null){
            enemies2Coords.add(new Vector2(value.get("x").asFloat(),value.get("y").asFloat()));
            value = value.next;
            game.characterManager.enemies2Count++;
        }
        value = json.getChild("bumps");
        while(value!=null){
            bumpsCoords.add(new Vector2(value.get("x").asFloat(),value.get("y").asFloat()));
            value = value.next;
            game.itemManager.bumpsCount++;
        }
        value = json.getChild("buffBombs");
        while(value!=null){
            buffBombsCoords.add(new Vector2(value.get("x").asFloat(),value.get("y").asFloat()));
            value = value.next;
            game.itemManager.buffBombsCount++;
        }
        value = json.getChild("buffJumps");
        while(value!=null){
            buffJumpsCoords.add(new Vector2(value.get("x").asFloat(),value.get("y").asFloat()));
            value = value.next;
            game.itemManager.buffJumpsCount++;
        }
    }
}