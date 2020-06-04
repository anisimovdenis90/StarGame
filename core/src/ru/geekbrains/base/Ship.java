package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletsPool;
import ru.geekbrains.pool.ExplosionsPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;

public class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.12f;

    protected final Vector2 v;
    protected final Vector2 v0;
    protected float shootInterval;
    protected float shootTimer;
    protected Rect worldBounds;
    protected ExplosionsPool explosionsPool;
    protected BulletsPool bulletsPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int bulletDamage;
    protected Sound shootSound;
    protected int hp;
    private float damageAnimateTimer;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    }

    public Ship(BulletsPool bulletsPool, ExplosionsPool explosionsPool, Rect worldBounds, Sound shootSound) {
        this.bulletsPool = bulletsPool;
        this.explosionsPool = explosionsPool;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    }

    public void setAnimateTimer(float damageAnimateTimer) {
        this.damageAnimateTimer = damageAnimateTimer;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void shoot(float shootVolume) {
        Bullet bullet = bulletsPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        shootSound.play(shootVolume);
    }

    public void damage(int damage) {
        damageAnimateTimer = 0f;
        frame = 1;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    protected void autoShoot(float delta, float shootVolume) {
        shootTimer += delta;
        if (shootTimer > shootInterval) {
            shoot(shootVolume);
            shootTimer = 0f;
        }
    }

    private void boom() {
        Explosion explosion = explosionsPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
