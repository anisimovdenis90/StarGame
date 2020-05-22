package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;

public class EnemysPool extends SpritesPool<EnemyShip> {

    private BulletsPool bulletsPool;
    private ExplosionsPool explosionsPool;
    private Rect worldBounds;
    private Sound shootSound;

    public EnemysPool(BulletsPool bulletsPool, ExplosionsPool explosionsPool, Rect worldBounds) {
        this.bulletsPool = bulletsPool;
        this.explosionsPool = explosionsPool;
        this.worldBounds = worldBounds;
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletsPool, explosionsPool, worldBounds, shootSound);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }

    public void reset() {
        super.dispose();
    }
}
