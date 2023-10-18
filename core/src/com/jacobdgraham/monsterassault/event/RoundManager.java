package com.jacobdgraham.monsterassault.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.jacobdgraham.monsterassault.entity.Enemy;

public class RoundManager {
    private int currentRound;
    private Array<Enemy> activeEnemies;
    private RoundData[] rounds;
    private boolean isRoundInProgress;

    public RoundManager() {
        currentRound = -1;
        rounds = new RoundData[10];
        rounds[0] = new RoundData(4, 1);
        rounds[1] = new RoundData(6, 2);
        rounds[2] = new RoundData( 8, 3);
        rounds[3] = new RoundData(10, 4);
        rounds[4] = new RoundData(12, 5);
        rounds[5] = new RoundData(14, 6);
        rounds[6] = new RoundData(16, 7);
        rounds[7] = new RoundData( 18, 8);
        rounds[8] = new RoundData( 20, 9);
        rounds[9] = new RoundData( 22, 10);
        isRoundInProgress = false;
        activeEnemies = new Array<>();
    }

    public RoundData changeRound() {
        if (currentRound <= rounds.length - 1) {
            currentRound++;
        }
        return rounds[currentRound];
    }

    public RoundData getRoundData() {
        return rounds[currentRound];
    }
}
