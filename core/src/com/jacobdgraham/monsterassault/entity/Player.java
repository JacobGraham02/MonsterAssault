package com.jacobdgraham.monsterassault.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {

    private Texture texture;
    private float health = 100.0f;

    public Player(final Texture playerTexture, final float x, final float y, final float width, final float height) {
        super(playerTexture, x, y, width, height);
    }

    public int getHealth() {
        return (int) health;
    }

    public void updatePlayerHealth(final float enemyDamage) {
        health -= enemyDamage;
    }

    public Color changeHealthLabelColour() {
        Color characterHealthColour = Color.WHITE;
        if (health > 50.0f && health <= 100.0f) {
            characterHealthColour = Color.GREEN;
        } else if (health < 50.0f && health >= 0.0f) {
            characterHealthColour = Color.RED;
        }
        return characterHealthColour;
    }

    public void setTexture(final Texture playerTexture) {
        super.setTexture(playerTexture);
    }
}
