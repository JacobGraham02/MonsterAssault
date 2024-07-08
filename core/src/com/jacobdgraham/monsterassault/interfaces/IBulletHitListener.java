package com.jacobdgraham.monsterassault.interfaces;

import com.jacobdgraham.monsterassault.entity.Bullet;
import com.jacobdgraham.monsterassault.entity.Enemy;

public interface IBulletHitListener {
    public void onBulletHit(Enemy enemy, Bullet bullet);
}
