package com.jacobdgraham.monsterassault.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {

    private float health = 100.0f;
    private boolean isHit = false;
    private float hitTimer = 0.0f;

    public Player(final Texture playerTexture, final float x, final float y, final float width, final float height) {
        super(playerTexture, x, y, width, height);
    }

    public int getHealth() {
        return (int) health;
    }

    public void updatePlayerHealth(final float difference) {
        health += difference;
    }

    public void fillPlayerHealth() {
        health = 100;
    }

    public void setHit(boolean isHit) {
        this.isHit = isHit;
        if (isHit) {
            hitTimer = 0.0f; // Reset the hit timer when the player is hit
        }
    }

    public boolean isHit() {
        return isHit;
    }

    public void updateHitTimer(float delta) {
        hitTimer += delta;
    }

    public float getHitTimer() {
        return hitTimer;
    }

    public float getHitDuration() {
        return 1.0f;
    }

    public Color changeHealthLabelColour() {
        Color characterHealthColour = Color.WHITE;
        if (health > 50.0f && health <= 100.0f) {
            characterHealthColour = Color.GREEN;
        } else if (health < 50.0f && health >= 0.0f) {
            characterHealthColour = Color.ORANGE;
        }
        return characterHealthColour;
    }

    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
    }

    public void setColor(Color color) {
        super.setColor(color);
    }

    public void setTexture(final Texture playerTexture) {
        super.setTexture(playerTexture);
    }
}
