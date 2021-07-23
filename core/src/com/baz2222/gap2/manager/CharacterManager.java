package com.baz2222.gap2.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.character.Enemy;
import com.baz2222.gap2.character.Enemy2;
import com.baz2222.gap2.character.Player;
import com.baz2222.gap2.item.Exit;

import java.util.Iterator;

import static com.baz2222.gap2.GapGame2.log;

public class CharacterManager {

    public enum State {fall, jump, idle, right, left, die}

    private GapGame2 game;

    public int enemiesCount = 0;
    public int enemies2Count = 0;

    public Player player;
    public Array<Enemy> enemies;
    public Array<Enemy2> enemies2;

    public TextureAtlas playerAtlas, enemyAtlas, enemy2Atlas;

    public CharacterManager(GapGame2 game) {
        this.game = game;
        enemies = new Array<>();
        enemies2 = new Array<>();
        loadAtlases();
    }//constructor

    public void onPlayerHitEnemy(Enemy enemy){
        if(game.characterManager.player.currState == State.fall || game.characterManager.player.ability == "shield"){
            game.soundManager.playSound("die", false);
            enemy.filter.categoryBits = game.box2DManager.removedBit;
            enemy.fixture.setFilterData(enemy.filter);
            log("player hit enemy");
        } else {
            game.soundManager.playSound("die", false);
            game.characterManager.player.filter.categoryBits = game.box2DManager.removedBit;
            game.characterManager.player.fixture.setFilterData(game.characterManager.player.filter);
            log("enemy hit player");
        }
    }

    public void onPlayerHitEnemy2(Enemy2 enemy){
        if(game.characterManager.player.ability == "shield"){
            game.soundManager.playSound("die", false);
            enemy.filter.categoryBits = game.box2DManager.removedBit;
            enemy.fixture.setFilterData(enemy.filter);
            log("player hit enemy2");
        } else {
            game.soundManager.playSound("die", false);
            game.characterManager.player.filter.categoryBits = game.box2DManager.removedBit;
            game.characterManager.player.fixture.setFilterData(game.characterManager.player.filter);
            log("enemy hit player");
        }
    }

    public void loadAtlases() {
        playerAtlas = new TextureAtlas("player.pack");
        enemyAtlas = new TextureAtlas("enemy.pack");
        enemy2Atlas = new TextureAtlas("enemy2.pack");
    }

    public void resetCharactersCount() {
        enemiesCount = 0;
        enemies.clear();
        enemies2Count = 0;
        enemies2.clear();
    }

    public void onPlayerWarp() {
        if (player.body.getPosition().x * game.scale < 0) {
            player.body.setTransform((player.body.getPosition().x * game.scale + game.width) / game.scale, player.body.getPosition().y, 0);
            game.stateManager.wrapped++;
            game.soundManager.playSound("warp",false);
            game.taskManager.checkForCompleted();
        }//-x
        if (player.body.getPosition().x * game.scale > game.width) {
            player.body.setTransform((player.body.getPosition().x * game.scale - game.width) / game.scale, player.body.getPosition().y, 0);
            game.stateManager.wrapped++;
            game.soundManager.playSound("warp",false);
            game.taskManager.checkForCompleted();
        }//+x
        if (player.body.getPosition().y * game.scale < 0) {
            player.body.setTransform(player.body.getPosition().x, (player.body.getPosition().y * game.scale + game.height) / game.scale, 0);
            game.stateManager.wrapped++;
            game.soundManager.playSound("warp",false);
            game.taskManager.checkForCompleted();
        }//-y
        if (player.body.getPosition().y * game.scale > game.height) {
            player.body.setTransform(player.body.getPosition().x, (player.body.getPosition().y * game.scale - game.height) / game.scale, 0);
            game.stateManager.wrapped++;
            game.soundManager.playSound("warp",false);
            game.taskManager.checkForCompleted();
        }//+y
    }

    public void onEnemyWarp(Enemy enemy) {
        if (enemy.body.getPosition().x * game.scale < 0) {
            enemy.body.setTransform((enemy.body.getPosition().x * game.scale + game.width) / game.scale, enemy.body.getPosition().y, 0);
            game.soundManager.playSound("warp",false);
        }//-x
        if (enemy.body.getPosition().x * game.scale > game.width) {
            enemy.body.setTransform((enemy.body.getPosition().x * game.scale - game.width) / game.scale, enemy.body.getPosition().y, 0);
            game.soundManager.playSound("warp",false);
        }//+x
        if (enemy.body.getPosition().y * game.scale < 0) {
            enemy.body.setTransform(enemy.body.getPosition().x, (enemy.body.getPosition().y * game.scale + game.height) / game.scale, 0);
            game.soundManager.playSound("warp",false);
        }//-y
        if (enemy.body.getPosition().y * game.scale > game.height) {
            enemy.body.setTransform(enemy.body.getPosition().x, (enemy.body.getPosition().y * game.scale - game.height) / game.scale, 0);
            game.soundManager.playSound("warp",false);
        }//+y
    }//enemy warp

    public void onEnemy2Warp(Enemy2 enemy) {
        if (enemy.body.getPosition().x * game.scale < 0) {
            enemy.body.setTransform((enemy.body.getPosition().x * game.scale + game.width) / game.scale, enemy.body.getPosition().y, 0);
            game.soundManager.playSound("warp",false);
        }//-x
        if (enemy.body.getPosition().x * game.scale > game.width) {
            enemy.body.setTransform((enemy.body.getPosition().x * game.scale - game.width) / game.scale, enemy.body.getPosition().y, 0);
            game.soundManager.playSound("warp",false);
        }//+x
        if (enemy.body.getPosition().y * game.scale < 0) {
            enemy.body.setTransform(enemy.body.getPosition().x, (enemy.body.getPosition().y * game.scale + game.height) / game.scale, 0);
            game.soundManager.playSound("warp",false);
        }//-y
        if (enemy.body.getPosition().y * game.scale > game.height) {
            enemy.body.setTransform(enemy.body.getPosition().x, (enemy.body.getPosition().y * game.scale - game.height) / game.scale, 0);
            game.soundManager.playSound("warp",false);
        }//+y
    }//enemy2 warp
}
