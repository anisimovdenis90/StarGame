package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class PlayerShip extends Sprite {

    private static final float V_LEN = 0.005f;

    private Vector2 touch;
    private Vector2 common;
    private Vector2 v;
    private Rect worldBounds;

    private boolean leftDirection = false;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;

    public PlayerShip(TextureRegion region) {
        super(new TextureRegion(region, 0, 0, 195, 287));
        touch = new Vector2();
        common = new Vector2();
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.3f);
        pos.set(worldBounds.pos);
    }

    @Override
    public void update(float delta) {
        common.set(touch);
        pos.add(v);
        checkBounds();
        if (common.sub(pos).len() < V_LEN) {
            pos.set(touch);
            v.setZero();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(this.touch).sub(pos).setLength(V_LEN);
        return false;
    }

    public boolean keyDown(int keycode) {
        touch.set(-1, -1);
        if (keycode == 19) {
            upDirection = true;
            v.set(v.x, V_LEN);
        }
        if (keycode == 20) {
            downDirection = true;
            v.set(v.x, -V_LEN);
        }
        if (keycode == 21) {
            leftDirection = true;
            v.set(-V_LEN, v.y);
        }
        if (keycode == 22) {
            rightDirection = true;
            v.set(V_LEN, v.y);
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        if (keycode == 19) {
            upDirection = false;
            if (!downDirection) {
                v.set(v.x, 0);
            } else {
                keyDown(20);
            }
        }
        if (keycode == 20) {
            downDirection = false;
            if (!upDirection) {
                v.set(v.x, 0);
            } else {
                keyDown(19);
            }
        }
        if (keycode == 21) {
            leftDirection = false;
            if (!rightDirection) {
                v.set(0, v.y);
            } else {
                keyDown(22);
            }
        }
        if (keycode == 22) {
            rightDirection = false;
            if (!leftDirection) {
                v.set(0, v.y);
            } else {
                keyDown(21);
            }
        }
        return false;
    }

    private void checkBounds() {
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            touch.set(worldBounds.getRight() - halfWidth, touch.y);
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            touch.set(worldBounds.getLeft() + halfWidth, touch.y);
        }
        if (getTop() > worldBounds.getTop()) {
            setTop(worldBounds.getTop());
            touch.set(touch.x, worldBounds.getTop() - halfHeight);
        }
        if (getBottom() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom());
            touch.set(touch.x, worldBounds.getBottom() + halfHeight);
        }
    }
}
