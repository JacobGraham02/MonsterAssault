package com.jacobdgraham.monsterassault.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Queue;
import com.jacobdgraham.monsterassault.entity.Enemy;

import java.util.Random;
public class RoundData {
    private final float[] enemySpawnXLocations;
    private final float[] enemySpawnYLocations;
    private final int[] enemySpeedsRoundsOneThroughFour;

    private final int[] enemySpeedsRoundFiveThroughNine;

    private final int[] enemySpeedsRoundTen;
    private final float enemySpriteWidth;
    private final float enemySpriteHeight;
    private Queue<Enemy> enemies;
    private final Random random;
    private final int currentRound;

    public RoundData(final int numEnemiesToSpawn, final int currentGameRound) {
        enemySpawnXLocations = new float[]{900.0f, 330.0f, 100.0f, 100.0f, 250.0f, 800.0f, 1050.0f, 900.0f, 550.0f};
        enemySpawnYLocations = new float[]{600.0f, 611.0f, 480.0f, 180.0f, 50.0f, 150.0f, 250.0f, 650.0f, 800.0f};
        enemySpeedsRoundsOneThroughFour = new int[]{10, 15, 20};
        enemySpeedsRoundFiveThroughNine = new int[]{30, 35, 40};
        enemySpeedsRoundTen = new int[]{30, 50};
        enemySpriteHeight = 32;
        enemySpriteWidth = 32;
        currentRound = currentGameRound;
        enemies = new Queue<>();
        random = new Random();
        populateEnemiesSpawnList(numEnemiesToSpawn);
    }

    private float getRandomXLocation(final int indexSpawnLocation) {
        return this.enemySpawnXLocations[indexSpawnLocation];
    }

    public int getCurrentRound() {
        return currentRound;
    }

    private float getRandomYLocation(final int indexSpawnLocation) {
        return this.enemySpawnYLocations[indexSpawnLocation];
    }

    private int getRandomEnemySpeed(final int roundEnemies) {
        int randomEnemySpeed = 0;
        if (roundEnemies >= 0 && roundEnemies <= 10) {
            randomEnemySpeed = random.nextInt(enemySpeedsRoundsOneThroughFour.length);
        } else if (roundEnemies > 10 && roundEnemies <= 20) {
            randomEnemySpeed = random.nextInt(enemySpeedsRoundFiveThroughNine.length);
        } else if (roundEnemies >= 22) {
            randomEnemySpeed = random.nextInt(enemySpeedsRoundTen.length);
        }
        return randomEnemySpeed;
    }

    private Texture getRandomEnemyTexture() {
        final int randomInteger = random.nextInt(2);
        if (randomInteger == 0) {
            return new Texture("Zombie.png");
        }
        return new Texture("Skeleton.png");
    }

    private void populateEnemiesSpawnList(final int enemiesForRound) {
        for (int i = 0; i < enemiesForRound; i++) {
            final int randomXYLocation = random.nextInt(enemySpawnXLocations.length);
            final float enemyXLocation = getRandomXLocation(randomXYLocation);
            final float enemyYLocation = getRandomYLocation(randomXYLocation);
            final int enemySpeed = getRandomEnemySpeed(enemiesForRound);
            final Texture enemyTexture = getRandomEnemyTexture();
            Enemy enemy = new Enemy(enemyTexture, enemyXLocation, enemyYLocation, enemySpriteWidth, enemySpriteHeight, enemySpeed);
            enemies.addFirst(enemy);
        }
    }

    public int getNumEnemiesToSpawn() {
        return this.enemies.size;
    }

    public Queue<Enemy> getRoundEnemies() {
        return enemies;
    }
}
