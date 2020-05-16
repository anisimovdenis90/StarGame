package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletsPool;

public class PlayerShip extends Sprite {

    private static final float SIZE = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;

    private final Vector2 v;
    private final Vector2 v0;

    private int leftPointer;
    private int rightPointer;

    private boolean pressedLeft;
    private boolean pressedRight;

    private Rect worldBounds;

    private BulletsPool bulletsPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Vector2 bulletPos;

    public PlayerShip(TextureAtlas atlas, BulletsPool bulletsPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletsPool = bulletsPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        v0 = new Vector2(0.5f, 0);
        v = new Vector2();
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
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

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        bulletPos.set(pos.x, pos.y + halfHeight);
        checkBounds();
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
            case Input.Keys.UP:
                shoot();
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

    private void shoot() {
        Bullet bullet = bulletsPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, 0.01f, worldBounds, 1);
    }
}
