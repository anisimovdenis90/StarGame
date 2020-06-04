package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledButton extends Sprite {

    private static final float PRESSED_SCALE = 0.9f;
    private static final float DEFAULT_SCALE = 1f;

    private boolean pressed;
    private int pointer;

    public ScaledButton(TextureRegion region) {
        super(region);
    }

    public static float getPressedScale() {
        return PRESSED_SCALE;
    }

    public static float getDefaultScale() {
        return DEFAULT_SCALE;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public abstract void action();

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (pressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        pressed = true;
        scale = PRESSED_SCALE;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = DEFAULT_SCALE;
        return false;
    }
}
