package com.jacobdgraham.monsterassault.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class MusicAndSoundManager {
    private Music music;
    private Sound bulletShootSound;
    private Sound bulletHittingMonsterSound;

    private Sound roundChangeSound;
    private static MusicAndSoundManager instance;

    public MusicAndSoundManager() {
        loadLoopingGameAmbientMusic();
        loadBulletSound();
        loadBulletHitMonsterSound();
        loadRoundChangeSound();
    }

    public static MusicAndSoundManager getInstance() {
        if (instance == null) {
            instance = new MusicAndSoundManager();
        }
        return instance;
    }

    private void loadLoopingGameAmbientMusic() {
        FileHandle fileHandle = Gdx.files.internal("CreepyAmbientMusic.wav");
        music = Gdx.audio.newMusic(fileHandle);
        music.setLooping(true);
    }

    private void loadBulletSound() {
        FileHandle fileHandle = Gdx.files.internal("PistolShootSound.wav");
        bulletShootSound = Gdx.audio.newSound(fileHandle);
    }

    private void loadBulletHitMonsterSound() {
        FileHandle fileHandle = Gdx.files.internal("PistolBulletHittingFlesh.wav");
        bulletHittingMonsterSound = Gdx.audio.newSound(fileHandle);
    }

    private void loadRoundChangeSound() {
        FileHandle fileHandle = Gdx.files.internal("RoundChangeSound.wav");
        roundChangeSound = Gdx.audio.newSound(fileHandle);
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

    public void playBulletShootSound() {
        if (bulletShootSound != null) {
            bulletShootSound.play(0.1f);
        }
    }

    public void playBulletHitSound() {
        if (bulletHittingMonsterSound != null) {
            bulletHittingMonsterSound.play(1.0f);
        }
    }

    public void playRoundChangeRound() {
        if (roundChangeSound != null) {
            roundChangeSound.play();
        }
    }

    public void dispose() {
        if (music != null) {
            music.dispose();
        }
        if (bulletShootSound != null) {
            bulletShootSound.dispose();
        }
        if (bulletHittingMonsterSound != null) {
            bulletHittingMonsterSound.dispose();
        }
    }
}
