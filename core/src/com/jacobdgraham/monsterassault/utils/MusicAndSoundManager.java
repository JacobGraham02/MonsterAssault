package com.jacobdgraham.monsterassault.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class MusicAndSoundManager {
    private Music music;
    private static MusicAndSoundManager instance;

    public MusicAndSoundManager() {
        loadLoopingGameAmbientMusic("CreepyAmbientMusic.wav");
    }

    public static MusicAndSoundManager getInstance() {
        if (instance == null) {
            instance = new MusicAndSoundManager();
        }
        return instance;
    }

    public void loadLoopingGameAmbientMusic(String musicFilePath) {
        FileHandle fileHandle = Gdx.files.internal(musicFilePath);
        music = Gdx.audio.newMusic(fileHandle);
        music.setLooping(true);
    }

    public void setLooping(final boolean looping) {
        if (music != null) {
            music.setLooping(looping);
        }
    }

    public void play() {
        if (music != null) {
            music.play();
        }
    }

    public void pause() {
        if (music != null && music.isPlaying()) {
            music.pause();
        }
    }

    public void resume() {
        if (music != null && !music.isPlaying()) {
            music.play();
        }
    }

    public void stop() {
        if (music != null) {
            music.stop();
        }
    }

    public void dispose() {
        if (music != null) {
            music.dispose();
        }
    }
}
