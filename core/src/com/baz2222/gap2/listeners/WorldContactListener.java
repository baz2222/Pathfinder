package com.baz2222.gap2.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.character.Enemy;
import com.baz2222.gap2.character.Enemy2;
import com.baz2222.gap2.character.Player;
import com.baz2222.gap2.item.Buff;
import com.baz2222.gap2.item.Bump;
import com.baz2222.gap2.item.Exit;
import com.baz2222.gap2.item.Switch;
import com.baz2222.gap2.map.Crumble;
import com.baz2222.gap2.map.Direction;
import com.baz2222.gap2.map.Spike;

import static com.baz2222.gap2.GapGame2.log;

public class WorldContactListener implements ContactListener {
    private Fixture fixA;
    private Fixture fixB;
    private short firstBit;
    private short secondBit;
    private GapGame2 game;

    public WorldContactListener(GapGame2 game) {
        this.game = game;
    }//constructor

    @Override
    public void beginContact(Contact contact) {
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();
        //player with exit
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Exit.class) {
                game.itemManager.onPlayerHitExit();
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Exit.class) {
                game.itemManager.onPlayerHitExit();
            }
        }//player with exit

        //player with switch
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Switch.class) {
                game.itemManager.onPlayerHitSwitch((Switch) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Switch.class) {
                game.itemManager.onPlayerHitSwitch((Switch) fixA.getUserData());
            }
        }//player with switch

        //player with bump
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Bump.class) {
                game.itemManager.onPlayerHitBump((Bump) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Bump.class) {
                game.itemManager.onPlayerHitBump((Bump) fixA.getUserData());
            }
        }//player with bump

        //player with buff
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Buff.class) {
                game.itemManager.onPlayerHitBuff((Buff) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Buff.class) {
                game.itemManager.onPlayerHitBuff((Buff) fixA.getUserData());
            }
        }//player with buff

        //player with crumble
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Crumble.class) {
                game.mapManager.onPlayerHitCrumble((Crumble) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Crumble.class) {
                game.mapManager.onPlayerHitCrumble((Crumble) fixA.getUserData());
            }
        }//player with crumble

        //player with spike
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Spike.class) {
                game.mapManager.onPlayerHitSpike();
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Spike.class) {
                game.mapManager.onPlayerHitSpike();
            }
        }//player with spike

        //enemy with direction
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Enemy.class && fixB.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemyHitDirection((Enemy) fixA.getUserData());
            }
            if (fixB.getUserData().getClass() == Enemy.class && fixA.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemyHitDirection((Enemy) fixB.getUserData());
            }
        }//enemy with direction

        //enemy2 with direction
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Enemy2.class && fixB.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemy2HitDirection((Enemy2) fixA.getUserData());
            }
            if (fixB.getUserData().getClass() == Enemy2.class && fixA.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemy2HitDirection((Enemy2) fixB.getUserData());
            }
        }//enemy2 with direction

        //player with enemy
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Enemy.class) {
                game.characterManager.onPlayerHitEnemy((Enemy) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Enemy.class) {
                game.characterManager.onPlayerHitEnemy((Enemy) fixA.getUserData());
            }
        }//player with enemy

        //player with enemy2
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == Player.class && fixB.getUserData().getClass() == Enemy2.class) {
                game.characterManager.onPlayerHitEnemy2((Enemy2) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == Enemy2.class) {
                game.characterManager.onPlayerHitEnemy2((Enemy2) fixA.getUserData());
            }
        }//player with enemy2

    }//begin contact

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        firstBit = contact.getFixtureA().getFilterData().categoryBits;
        secondBit = contact.getFixtureB().getFilterData().categoryBits;
        //prevent collision between direction and player
        if ((firstBit | secondBit) == (game.box2DManager.playerBit | game.box2DManager.directionBit)) {
            contact.setEnabled(false);
        }//if
        //prevent collision ...
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
