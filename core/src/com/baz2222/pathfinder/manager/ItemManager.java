package com.baz2222.pathfinder.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.item.Bump;
import com.baz2222.pathfinder.tools.GameScreen;

public class ItemManager {
    private Pathfinder game;

    public int switchesCount;
    public int bumpsCount;
    public int buffBombsCount;
    public int buffJumpsCount;
    public int buffShieldsCount;

    public com.baz2222.pathfinder.item.Exit exit;

    public Array<com.baz2222.pathfinder.item.Buff> buffs;
    public Array<com.baz2222.pathfinder.item.Switch> switches;
    public Array<com.baz2222.pathfinder.item.Bump> bumps;

    public TextureAtlas switchAtlas, bumpAtlas, exitAtlas;

    public ItemManager(Pathfinder game) {
        this.game = game;
        loadAtlases();
        buffs = new Array<>();
        switches = new Array<>();
        bumps = new Array<>();
    }

    public void loadAtlases() {
        switchAtlas = new TextureAtlas("switch.pack");
        exitAtlas = new TextureAtlas("exit.pack");
        bumpAtlas = new TextureAtlas("bump.pack");
    }

    public void resetItemsCount() {
        switchesCount = 0;
        switches.clear();
        buffBombsCount = 0;
        buffJumpsCount = 0;
        buffShieldsCount = 0;
        buffs.clear();
        bumpsCount = 0;
        bumps.clear();
    }

    public void onPlayerHitBuff(com.baz2222.pathfinder.item.Buff buff){
        buff.setVisible(false);
        buff.filter.categoryBits = game.box2DManager.removedBit;
        buff.fixture.setFilterData(buff.filter);
        game.characterManager.player.ability = buff.getName();
        if(game.characterManager.player.trail != null){
            game.characterManager.player.trail.remove();
            game.characterManager.player.trail = null;
        }
        game.characterManager.player.trail = new com.baz2222.pathfinder.item.Trail(game, game.characterManager.player.ability);
        Pathfinder.log("player hit buff" + buff.getName());
    }

    public void onPlayerHitExit() {
        Pathfinder.log("player hit the exit");
        if(game.itemManager.exit.isVisible()) {
            game.soundManager.playSound("exit", false);
            game.stateManager.completed++;
            game.taskManager.checkForCompleted();
            game.stateManager.saveState();
            if (game.currLevel == 10) {
                if (game.currWorld == 3) {
                    game.screenManager.levelScreen.onClose();
                    game.stateManager.reset();
                    game.screenManager.levelOverScreen.type = "over";
                    game.setScreen(game.screenManager.levelOverScreen);
                    ((com.baz2222.pathfinder.tools.GameScreen) game.getScreen()).onOpen();
                    return;
                } else {
                    game.currWorld++;
                }//else
                game.currLevel = 1;
            } else {
                game.currLevel++;
            }//else
            game.screenManager.levelScreen.onClose();
            game.screenManager.levelOverScreen.type = "next";
            game.setScreen(game.screenManager.levelOverScreen);
            ((GameScreen) game.getScreen()).onOpen();
        }//if isVisible == true
    }//on player hit exit

    public void onPlayerHitSwitch(com.baz2222.pathfinder.item.Switch sw){
        sw.isChecked = true;
        exit.setVisible(true);
    }

    public void onPlayerHitBump(Bump bump){
        Pathfinder.log("player hit bump");
        game.characterManager.player.forceJump(1.5f);
        bump.isHitted = true;
    }
}
