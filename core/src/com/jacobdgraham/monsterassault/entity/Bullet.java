package com.jacobdgraham.monsterassault.entity;

import com.badlogic.gdx.graphics.Texture;

public class Bullet extends Entity {
    private final int damage;
    private final float angle;
    private final int speed;

    public Bullet(final Texture bulletTexture, final float x, final float y, final float width, final float height,
                  final int bulletDamage, final int bulletSpeed, float bulletAngle) {
        super(bulletTexture, x, y, width, height);
        this.damage = bulletDamage;
        this.speed = bulletSpeed;
        this.angle = bulletAngle;
    }

    public final float getDamage() {
        return damage;
    }

    public void move() {
        float radianAngle = (float) Math.toRadians(this.angle);

        float deltaX = this.speed * (float) Math.cos(radianAngle);
        float deltaY = this.speed * (float) Math.sin(radianAngle);

        super.setX(super.getX() + deltaX);
        super.setY(super.getY() + deltaY);
    }
}
