package com.baz2222.pathfinder.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.character.Enemy;
import com.baz2222.pathfinder.character.Enemy2;
import com.baz2222.pathfinder.character.Player;

public class CharacterManager {

    public enum State {fall, jump, idle, right, left, die}

    private Pathfinder game;

    public int enemiesCount = 0;
    public int enemies2Count = 0;

    public Player player;
    public Array<com.baz2222.pathfinder.character.Enemy> enemies;
    public Array<com.baz2222.pathfinder.character.Enemy2> enemies2;

    public TextureAtlas playerAtlas, enemyAtlas, enemy2Atlas;

    public CharacterManager(Pathfinder game) {
        this.game = game;
        enemies = new Array<>();
        enemies2 = new Array<>();
        loadAtlases();
    }//constructor

    public void onPlayerHitEnemy(com.baz2222.pathfinder.character.Enemy enemy){
        if(game.characterManager.player.currState == State.fall || game.characterManager.player.ability == "shield"){
            game.soundManager.playSound("die", false);
            enemy.filter.categoryBits = game.box2DManager.removedBit;
            enemy.fixture.setFilterData(enemy.filter);
            Pathfinder.log("player hit enemy");
        } else {
            game.soundManager.playSound("die", false);
            game.characterManager.player.filter.categoryBits = game.box2DManager.removedBit;
            game.characterManager.player.fixture.setFilterData(game.characterManager.player.filter);
            Pathfinder.log("enemy hit player");
        }
    }

    public void onPlayerHitEnemy2(com.baz2222.pathfinder.character.Enemy2 enemy){
        if(game.characterManager.player.ability == "shield"){
            game.soundManager.playSound("die", false);
            enemy.filter.categoryBits = game.box2DManager.removedBit;
            enemy.fixture.setFilterData(enemy.filter);
            Pathfinder.log("player hit enemy2");
        } else {
            game.soundManager.playSound("die", false);
            game.characterManager.player.filter.categoryBits = game.box2DManager.removedBit;
            game.characterManager.player.fixture.setFilterData(game.characterManager.player.filter);
            Pathfinder.log("enemy hit player");
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
