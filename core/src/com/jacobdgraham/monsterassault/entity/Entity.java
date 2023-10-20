package com.jacobdgraham.monsterassault.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity {

    private final Rectangle boundingBox;
    private Sprite entitySprite;

    public Entity(final Texture entityTexture, final float x, final float y, final float width, final float height) {
        this.entitySprite = new Sprite(entityTexture);
        this.entitySprite.setSize(width, height);
        this.entitySprite.setPosition(x, y);
        this.boundingBox = new Rectangle(x, y, width, height);
    }

    public void setColor(float r, float g, float b, float a) {
        entitySprite.setColor(r, g, b, a);
    }

    public void setColor(Color color) {
        entitySprite.setColor(color);
    }

    public void setTexture(final Texture entityTexture) {
        this.entitySprite = new Sprite(entityTexture);
    }

    public float getX() {
        return boundingBox.x;
    }

    public float getY() {
        return boundingBox.y;
    }

    public void setX(final float x) {
        entitySprite.setX(x);
        boundingBox.setX(x);
    }

    public void setY(final float y) {
        entitySprite.setY(y);
        boundingBox.setY(y);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public float getBoundingBoxWidth() {
        return boundingBox.width;
    }

    public float getBoundingBoxHeight() {
        return boundingBox.height;
    }

    public void setRotation(final float angle_in_degrees) {
        entitySprite.setRotation(angle_in_degrees);
    }

    public float getRotation() {
        return entitySprite.getRotation();
    }

    public void render(SpriteBatch batch) {
        entitySprite.draw(batch);
    }
}
