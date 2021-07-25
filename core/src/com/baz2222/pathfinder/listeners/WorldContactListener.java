package com.baz2222.pathfinder.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.item.Buff;
import com.baz2222.pathfinder.item.Bump;
import com.baz2222.pathfinder.item.Exit;
import com.baz2222.pathfinder.item.Switch;
import com.baz2222.pathfinder.map.Crumble;
import com.baz2222.pathfinder.map.Direction;
import com.baz2222.pathfinder.character.Enemy;
import com.baz2222.pathfinder.character.Enemy2;
import com.baz2222.pathfinder.character.Player;
import com.baz2222.pathfinder.map.Spike;

import static com.baz2222.pathfinder.Pathfinder.log;

public class WorldContactListener implements ContactListener {
    private Fixture fixA;
    private Fixture fixB;
    private short firstBit;
    private short secondBit;
    private Pathfinder game;

    public WorldContactListener(Pathfinder game) {
        this.game = game;
    }//constructor

    @Override
    public void beginContact(Contact contact) {
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();
        //player with exit
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == Exit.class) {
                game.itemManager.onPlayerHitExit();
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixA.getUserData().getClass() == Exit.class) {
                game.itemManager.onPlayerHitExit();
            }
        }//player with exit

        //player with switch
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == Switch.class) {
                game.itemManager.onPlayerHitSwitch((Switch) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixA.getUserData().getClass() == Switch.class) {
                game.itemManager.onPlayerHitSwitch((Switch) fixA.getUserData());
            }
        }//player with switch

        //player with bump
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == Bump.class) {
                game.itemManager.onPlayerHitBump((Bump) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixA.getUserData().getClass() == Bump.class) {
                game.itemManager.onPlayerHitBump((Bump) fixA.getUserData());
            }
        }//player with bump

        //player with buff
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == Buff.class) {
                game.itemManager.onPlayerHitBuff((Buff) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixA.getUserData().getClass() == Buff.class) {
                game.itemManager.onPlayerHitBuff((Buff) fixA.getUserData());
            }
        }//player with buff

        //player with crumble
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == Crumble.class) {
                game.mapManager.onPlayerHitCrumble((Crumble) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixA.getUserData().getClass() == Crumble.class) {
                game.mapManager.onPlayerHitCrumble((Crumble) fixA.getUserData());
            }
        }//player with crumble

        //player with spike
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == com.baz2222.pathfinder.map.Spike.class) {
                game.mapManager.onPlayerHitSpike();
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixA.getUserData().getClass() == Spike.class) {
                game.mapManager.onPlayerHitSpike();
            }
        }//player with spike

        //enemy with direction
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy.class && fixB.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemyHitDirection((com.baz2222.pathfinder.character.Enemy) fixA.getUserData());
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy.class && fixA.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemyHitDirection((com.baz2222.pathfinder.character.Enemy) fixB.getUserData());
            }
        }//enemy with direction

        //enemy2 with direction
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy2.class && fixB.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemy2HitDirection((com.baz2222.pathfinder.character.Enemy2) fixA.getUserData());
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy2.class && fixA.getUserData().getClass() == Direction.class) {
                game.mapManager.onEnemy2HitDirection((com.baz2222.pathfinder.character.Enemy2) fixB.getUserData());
            }
        }//enemy2 with direction

        //player with enemy
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy.class) {
                game.characterManager.onPlayerHitEnemy((com.baz2222.pathfinder.character.Enemy) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy.class) {
                game.characterManager.onPlayerHitEnemy((Enemy) fixA.getUserData());
            }
        }//player with enemy

        //player with enemy2
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if (fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Player.class && fixB.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy2.class) {
                game.characterManager.onPlayerHitEnemy2((com.baz2222.pathfinder.character.Enemy2) fixB.getUserData());
            }
            if (fixB.getUserData().getClass() == Player.class && fixA.getUserData().getClass() == com.baz2222.pathfinder.character.Enemy2.class) {
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
