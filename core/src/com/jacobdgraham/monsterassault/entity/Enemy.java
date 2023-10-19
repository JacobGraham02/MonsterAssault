package com.jacobdgraham.monsterassault.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.jacobdgraham.monsterassault.pathfinding.AStarNode;

import java.util.LinkedList;

public class Enemy extends Entity {
    private LinkedList<AStarNode> path;
    private AStarNode nextNode;
    private float health = 100.0f;
    private boolean isDead = false;
    private float damage = 2.5f;
    private final float moveSpeed;
    private float lastTimeAppliedPlayerDamage = 0.0f;
    private float updatePlayerHealthInterval = 1.0f;

    public Enemy(final Texture enemyTexture, float x, float y, float width, float height, float speed) {
        super(enemyTexture, x, y, width, height);
        path = new LinkedList<>();
        moveSpeed = speed;
    }
    public void setPath(LinkedList<AStarNode> newPath) {
        if (isDead) {
            return;
        }
        path = newPath;
        if (path != null && !(path.isEmpty())) {
            nextNode = path.removeFirst();
        }
    }

    public void takeDamage(final float damage) {
        health -= damage;
        if (health <= 0) {
            isDead = true;
        }
    }

    public float getHealth() {
        return this.health;
    }

    public float dealDamageToPlayer() {
        if (isPathFindingComplete()) {
            float currentTime = TimeUtils.nanoTime() / 1000000000.0f;

            if (currentTime - lastTimeAppliedPlayerDamage >= updatePlayerHealthInterval) {
                lastTimeAppliedPlayerDamage = currentTime;
                return damage;
            }
        }
        return 0.0f;
    }

    public boolean isHittingPlayer() {
        return isPathFindingComplete();
    }

    private boolean isPathFindingComplete() {
        return !isDead && path.isEmpty();
    }


    public void move() {
        if (isDead) {
            return;
        }

        if (nextNode == null) {
            return;
        }

        if (path != null && !path.isEmpty()) {
            AStarNode nextNode = path.getFirst();
            float targetX = nextNode.getX() * 32;
            float targetY = nextNode.getY() * 32;

            float directionX = targetX - getX();
            float directionY = targetY - getY();
            float distance = (float) Math.sqrt(directionX * directionX + directionY * directionY);

            float enemyAngleInDegrees = (float)Math.toDegrees(Math.atan2(directionY, directionX));
            super.setRotation(enemyAngleInDegrees+90);

            if (super.getX() < targetX) {
                super.setX((float) Math.min(super.getX()+0.5, targetX));
            } else if (super.getX() > targetX) {
                super.setX((float) Math.max(super.getX()-0.5, targetX));
            }

            if (super.getY() < targetY) {
                super.setY((float) Math.min(super.getY()+0.5, targetY));
            } else if (super.getY() > targetY) {
                super.setY((float) Math.max(super.getY()-0.5, targetY));
            }

            if (distance > 0.0f) {
                float moveX = (directionX / distance) * moveSpeed * Gdx.graphics.getDeltaTime();
                float moveY = (directionY / distance) * moveSpeed * Gdx.graphics.getDeltaTime();
                setX(getX() + moveX);
                setY(getY() + moveY);
            }

            // Check if the enemy has reached the next node in the path
            float nodeDistance = 32.0f; // You can adjust this value based on your tile size
            if (Math.abs(targetX - getX()) < nodeDistance && Math.abs(targetY - getY()) < nodeDistance) {
                // Remove the current node from the path
                path.removeFirst();
            }
        }
    }
}
