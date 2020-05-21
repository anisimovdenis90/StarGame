package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletsPool;
import ru.geekbrains.pool.ExplosionsPool;

public class EnemyShip extends Ship {

    public EnemyShip(BulletsPool bulletsPool, ExplosionsPool explosionsPool, Rect worldBounds, Sound shootSound) {
        super(bulletsPool, explosionsPool, worldBounds, shootSound);
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, pos.y - halfHeight);
        super.update(delta);
        if (getBottom() <= worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float shootInterval,
            int hp,
            float height
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.bulletDamage = bulletDamage;
        this.shootInterval = shootInterval;
        this.shootTimer = shootInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(v0);
    }
}
