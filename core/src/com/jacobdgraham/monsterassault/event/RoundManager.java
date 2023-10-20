package com.jacobdgraham.monsterassault.event;

import com.badlogic.gdx.utils.Array;
import com.jacobdgraham.monsterassault.entity.Enemy;

public class RoundManager {
    private int currentRound;
    private final RoundData[] rounds;
    private final int numberOfRounds;
    private  boolean isGameOver;

    public RoundManager() {
        currentRound = 0;
        rounds = new RoundData[10];
        rounds[0] = new RoundData(4, 1);
        rounds[1] = new RoundData(6, 2);
        rounds[2] = new RoundData( 8, 3);
        rounds[3] = new RoundData(10, 4);
        rounds[4] = new RoundData(11, 5);
        rounds[5] = new RoundData(12, 6);
        rounds[6] = new RoundData(13, 7);
        rounds[7] = new RoundData( 14, 8);
        rounds[8] = new RoundData( 15, 9);
        rounds[9] = new RoundData( 16, 10);
        Array<Enemy> activeEnemies = new Array<>();
        numberOfRounds = rounds.length-1;
        isGameOver = false;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public RoundData changeRound() {
        if (currentRound >= numberOfRounds) {
            isGameOver = true;
            return rounds[currentRound];
        }
        if (currentRound <= rounds.length - 1) {
            currentRound++;
        }
        return rounds[currentRound];
    }

    public RoundData getRoundData() {
        return rounds[currentRound];
    }
}
