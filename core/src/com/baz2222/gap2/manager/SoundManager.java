package com.baz2222.gap2.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.baz2222.gap2.GapGame2;

public class SoundManager {

    public float volume;
    public Sound sound;
    public Music music;

    private GapGame2 game;
    private AssetManager manager;

    public SoundManager(GapGame2 game) {
        this.game = game;
        volume = 0.1f;
        manager = new AssetManager();
        loadSounds();
    }

    public void loadSounds() {
        manager.load("sounds/world1.mp3", Music.class);
        manager.load("sounds/world2.mp3", Music.class);
        manager.load("sounds/world3.mp3", Music.class);
        manager.load("sounds/menu.mp3", Music.class);

        manager.load("sounds/jump.mp3", Sound.class);
        manager.load("sounds/exit.mp3", Sound.class);
        manager.load("sounds/warp.mp3", Sound.class);
        manager.load("sounds/step.mp3", Sound.class);
        manager.load("sounds/fall.mp3", Sound.class);
        manager.load("sounds/die.mp3", Sound.class);
        manager.load("sounds/break.mp3", Sound.class);
        manager.load("sounds/bomb.mp3", Sound.class);

        manager.finishLoading();
    }

    public void playSound(String soundName, boolean looping) {
        sound = manager.get("sounds/" + soundName + ".mp3", Sound.class);
        sound.setLooping(sound.play(volume), looping);
    }

    public void playMusic(String musicName, boolean looping) {
        music = manager.get("sounds/" + musicName + ".mp3", Music.class);
        music.setLooping(looping);
        music.setVolume(volume);
        music.play();
    }

    public void stopPlayingMusic() {
        if (music.isPlaying())
            music.stop();
    }
}
