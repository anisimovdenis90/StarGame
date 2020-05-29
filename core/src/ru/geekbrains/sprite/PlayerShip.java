package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletsPool;
import ru.geekbrains.pool.ExplosionsPool;

public class PlayerShip extends Ship {

    private static final float SIZE = 0.2f;
    private static final float MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;
    private static final int HP = 100;
    private static final int ADD_HP = 30;
    private static final float SUPER_SHOOT_INTERVAL_MAX = 10f;

    private int leftPointer;
    private int rightPointer;
    private boolean pressedLeft;
    private boolean pressedRight;

    private float superShootTimer;
    private float superShootInterval;

    private Sound superShootSound;

    public PlayerShip(TextureAtlas atlas, BulletsPool bulletsPool, ExplosionsPool explosionsPool) {
        super(atlas.findRegion("playerShip"), 1, 3, 3);
        this.bulletsPool = bulletsPool;
        this.explosionsPool = explosionsPool;
        bulletRegion = atlas.findRegion("bulletPlayerShip");
        bulletV = new Vector2(0, 0.5f);
        bulletHeight = 0.01f;
        bulletDamage = 1;
        v0.set(0.5f, 0);
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        shootInterval = 0.25f;
        shootTimer = shootInterval;
        hp = HP;
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        superShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/superLaser.mp3"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        if (superShootInterval > 0) {
            autoSuperShoot(delta);
        } else {
            autoShoot(delta);
        }
        checkBounds();
    }

    public void activeSuperShoot() {
        if (superShootInterval > 0) {
            superShootInterval += SUPER_SHOOT_INTERVAL_MAX;
        } else {
            superShootInterval = SUPER_SHOOT_INTERVAL_MAX;
        }
    }

    public void addHp() {
        int newHp = hp + ADD_HP;
        hp = Math.min(newHp, HP);
    }

    public void dispose() {
        shootSound.dispose();
        superShootSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public boolean isCollision(Sprite sprite) {
        return !(sprite.getRight() < getLeft()
                || sprite.getLeft() > getRight()
                || sprite.getBottom() > pos.y
                || sprite.getTop() < getBottom()
        );
    }

    public void reset() {
        hp = HP;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        pressedLeft = false;
        pressedRight = false;
        stop();
        pos.x = 0f;
        flushDestroy();
    }

    public void activateBonus(Bonus bonus) {
        if (bonus.getBonusType() == 0) {
            addHp();
        } else if (bonus.getBonusType() == 1) {
            activeSuperShoot();
        }
    }

    private void checkBounds() {
        if (getRight() > worldBounds.getRight()) {
            stop();
            setRight(worldBounds.getRight());
        }
        if (getLeft() < worldBounds.getLeft()) {
            stop();
            setLeft(worldBounds.getLeft());
        }
    }

    private void autoSuperShoot(float delta) {
        if ((superShootTimer += delta) < superShootInterval) {
            superShoot(delta);
        } else {
            superShootTimer = 0f;
            superShootInterval = 0f;
        }
    }

    private void superShoot(float delta) {
        shootTimer += delta;
        if (shootTimer > shootInterval) {
            Bullet bullet1 = bulletsPool.obtain();
            Bullet bullet2 = bulletsPool.obtain();
            Bullet bullet3 = bulletsPool.obtain();
            Vector2 bulletV2 = bulletV.cpy().set(0.1f, bulletV.y);
            Vector2 bulletV3 = bulletV.cpy().set(-0.1f, bulletV.y);
            bullet1.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
            bullet2.set(this, bulletRegion, bulletPos, bulletV2, bulletHeight, worldBounds, bulletDamage);
            bullet3.set(this, bulletRegion, bulletPos, bulletV3, bulletHeight, worldBounds, bulletDamage);
            superShootSound.play(0.5f);
            shootTimer = 0f;
        }
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }
}
