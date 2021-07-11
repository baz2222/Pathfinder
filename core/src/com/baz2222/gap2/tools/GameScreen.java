package com.baz2222.gap2.tools;

import com.badlogic.gdx.ScreenAdapter;

public abstract class GameScreen extends ScreenAdapter {
    public abstract void onOpen();
    public abstract void onClose();

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
