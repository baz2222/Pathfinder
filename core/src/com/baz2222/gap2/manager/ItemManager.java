package com.baz2222.gap2.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.item.Buff;
import com.baz2222.gap2.item.Exit;
import com.baz2222.gap2.tools.GameScreen;

import static com.baz2222.gap2.GapGame2.log;

public class ItemManager {
    private GapGame2 game;

    public int switchesCount;
    public int bumpsCount;
    public int buffBombsCount;
    public int buffJumpsCount;
    public int buffShieldsCount;

    public Exit exit;

    public Array<Buff> buffs;

    public TextureAtlas switchAtlas, bumpAtlas, exitAtlas;

    public ItemManager(GapGame2 game) {
        this.game = game;
        loadAtlases();
        buffs = new Array<>();
    }

    public void loadAtlases() {
        switchAtlas = new TextureAtlas("switch.pack");
        exitAtlas = new TextureAtlas("exit.pack");
        bumpAtlas = new TextureAtlas("bump.pack");
    }

    public void resetItemsCount() {
        switchesCount = 0;
        buffBombsCount = 0;
        buffJumpsCount = 0;
        buffShieldsCount = 0;
        buffs.clear();
        bumpsCount = 0;
    }

    public void onPlayerHitExit() {
        log("player hit the exit");
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
                ((GameScreen) game.getScreen()).onOpen();
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
    }
}
