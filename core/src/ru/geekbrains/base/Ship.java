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

    protected final Vector2 v;
    protected final Vector2 v0;

    protected Rect worldBounds;

    protected ExplosionsPool explosionsPool;
    protected BulletsPool bulletsPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int bulletDamage;

    protected float shootInterval;
    protected float shootTimer;

    protected Sound shootSound;

    protected int hp;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        bulletPos = new Vector2();
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
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        shootTimer += delta;
        if (shootTimer > shootInterval) {
            shoot();
            shootTimer = 0f;
        }
    }

    protected void shoot() {
        Bullet bullet = bulletsPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        shootSound.play(0.3f);
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void boom() {
        Explosion explosion = explosionsPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
