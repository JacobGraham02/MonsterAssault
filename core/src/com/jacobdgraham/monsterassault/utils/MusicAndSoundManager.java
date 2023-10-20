package com.jacobdgraham.monsterassault.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class MusicAndSoundManager {
    private Music music;
    private Sound bulletShootSound;
    private Sound bulletHittingMonsterSound;
    private Sound characterGettingHitSound;
    private Sound roundChangeSound;
    private Sound gameOverSound;

    private Sound characterDyingSound;
    private static MusicAndSoundManager instance;

    public MusicAndSoundManager() {
        loadLoopingGameAmbientMusic();
        loadBulletSound();
        loadBulletHitMonsterSound();
        loadRoundChangeSound();
        loadGuyGettingHitSound();
        loadGameOverSound();
        loadGuyDeathSound();
    }

    public static MusicAndSoundManager getInstance() {
        if (instance == null) {
            instance = new MusicAndSoundManager();
        }
        return instance;
    }

    private void loadLoopingGameAmbientMusic() {
        FileHandle fileHandleAmbientMusic = Gdx.files.internal("CreepyAmbientMusic.wav");
        music = Gdx.audio.newMusic(fileHandleAmbientMusic);
        music.setLooping(true);
    }

    private void loadBulletSound() {
        FileHandle fileHandleShootSound = Gdx.files.internal("PistolShootSound.wav");
        bulletShootSound = Gdx.audio.newSound(fileHandleShootSound);
    }

    private void loadBulletHitMonsterSound() {
        FileHandle fileHandleBulletHittingFlesh = Gdx.files.internal("ZombieHit.wav");
        bulletHittingMonsterSound = Gdx.audio.newSound(fileHandleBulletHittingFlesh);
    }

    private void loadRoundChangeSound() {
        FileHandle fileHandleRoundChange = Gdx.files.internal("RoundChangeSound.wav");
        roundChangeSound = Gdx.audio.newSound(fileHandleRoundChange);
    }

    private void loadGuyGettingHitSound() {
        FileHandle fileHandleCharacterIsHit = Gdx.files.internal("CharacterHurt.wav");
        characterGettingHitSound = Gdx.audio.newSound(fileHandleCharacterIsHit);
    }

    private void loadGameOverSound() {
        FileHandle fileHandleGameOver = Gdx.files.internal("GameOver.wav");
        gameOverSound = Gdx.audio.newSound(fileHandleGameOver);
    }

    private void loadGuyDeathSound() {
        FileHandle fileHandleGuyDeath = Gdx.files.internal("CharacterDeathCry.wav");
        characterDyingSound = Gdx.audio.newSound(fileHandleGuyDeath);
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

    public void playGuyGettingHitSound() {
        if (characterGettingHitSound != null) {
            characterGettingHitSound.play();
        }
    }

    public void playGameOverSound() {
        if (gameOverSound != null) {
            gameOverSound.play();
        }
    }

    public void playCharacterDeathSound() {
        if (characterDyingSound != null) {
            characterDyingSound.play();
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
