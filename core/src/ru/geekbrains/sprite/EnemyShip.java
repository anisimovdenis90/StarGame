package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletsPool;
import ru.geekbrains.pool.ExplosionsPool;

public class EnemyShip extends Ship {

    public enum EnemyType {
        SMALL,
        MEDIUM,
        BIG
    }

    private static final float SHOOT_SOUND_VOLUME = 0.6f;
    private static final float MARGIN = 0.04f;
    private static final float V_Y = -0.3f;

    private int scoresForKill;

    private EnemyType enemyType;

    public EnemyShip(BulletsPool bulletsPool, ExplosionsPool explosionsPool, Rect worldBounds, Sound shootSound) {
        super(bulletsPool, explosionsPool, worldBounds, shootSound);
    }

    public int getScoresForKill() {
        return scoresForKill;
    }

    public EnemyType getType() {
        return enemyType;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() <= worldBounds.getTop() - MARGIN) {
            v.set(v0);
            bulletPos.set(pos.x, pos.y - getHalfHeight());
            autoShoot(delta, SHOOT_SOUND_VOLUME);
        }
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
            float height,
            int scores,
            EnemyType enemyType
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
        this.v.set(0, V_Y);
        this.scoresForKill = scores;
        this.enemyType = enemyType;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }
}
