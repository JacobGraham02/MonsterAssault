package com.jacobdgraham.monsterassault.utils;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;

public class MusicAndSoundManager {
    private Music music;
    private Sound bulletShootSound;
    private Sound bulletHittingMonsterSound;
    private Sound characterGettingHitSound;
    private Sound roundChangeSound;
    private Sound gameOverSound;
    private Sound gameSuccessSound;
    private Sound characterDyingSound;
    private Sound[] zombieGroans;
    private static MusicAndSoundManager instance;

    public MusicAndSoundManager() {
        loadLoopingGameAmbientMusic();
        loadBulletSound();
        loadBulletHitMonsterSound();
        loadRoundChangeSound();
        loadGuyGettingHitSound();
        loadGameOverSound();
        loadGuyDeathSound();
        loadGameSuccessSound();
        loadZombieGroans();
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

    private void loadGameSuccessSound() {
        FileHandle fileHandleGameSuccess = Gdx.files.internal("GameSuccess.wav");
        gameSuccessSound = Gdx.audio.newSound(fileHandleGameSuccess);
    }

    public void loadZombieGroans() {
        zombieGroans = new Sound[]{
                Gdx.audio.newSound(Gdx.files.internal("ZombieGrowl1.wav")),
                Gdx.audio.newSound(Gdx.files.internal("ZombieGrowl2.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("ZombieGrowl3.wav")),
                Gdx.audio.newSound(Gdx.files.internal("ZombieGrowl4.wav")),
                Gdx.audio.newSound(Gdx.files.internal("ZombieGrowl5.wav")),
                Gdx.audio.newSound(Gdx.files.internal("ZombieGrowl6.wav"))
        };
        scheduleRandomZombieGroanSound();
    }

    private void scheduleRandomZombieGroanSound() {
        int minimumZombieGroanInterval = 5;
        int maximumZombieGroanInterval = 10;
        int zombieGroanInterval = random.nextInt(maximumZombieGroanInterval - minimumZombieGroanInterval) + minimumZombieGroanInterval;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                playRandomGroanSound();
                scheduleRandomZombieGroanSound();
            }
        }, zombieGroanInterval);
    }

    private void playRandomGroanSound() {
        if (zombieGroans.length > 0) {
            // Choose a random groan sound from the array
            int randomIndex = random.nextInt(zombieGroans.length);
            zombieGroans[randomIndex].play(1.0f);
        }
    }

    public void play() {
        if (music != null) {
            music.setVolume(1.0f);
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
            bulletHittingMonsterSound.play(0.25f);
        }
    }

    public void playRoundChangeRound() {
        if (roundChangeSound != null) {
            roundChangeSound.play();
        }
    }

    public void playGuyGettingHitSound() {
        if (characterGettingHitSound != null) {
            characterGettingHitSound.play(0.25f);
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

    public void playGameSuccessSound() {
        if (gameSuccessSound != null) {
            gameSuccessSound.play();
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
        if (characterGettingHitSound != null) {
            characterGettingHitSound.dispose();
        }
        if (roundChangeSound != null) {
            roundChangeSound.dispose();
        }
        if (gameOverSound != null) {
            gameOverSound.dispose();
        }
        if (gameSuccessSound != null) {
            gameSuccessSound.dispose();
        }
        if (characterDyingSound != null) {
            characterDyingSound.dispose();
        }
        zombieGroans = null;
    }
}
