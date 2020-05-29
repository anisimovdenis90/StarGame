
package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Bonus extends Sprite {

    private static final float SIZE = 0.1f;
    private static final float V_Y = -0.2f;

    private Sound bonusSound;
    private Rect worldBounds;
    private Vector2 v;
    private int bonusType;

    private float animateTimer;
    private float animateInterval;

    public Bonus(Sound bonusSound, Rect worldBounds) {
        regions = new TextureRegion[1];
        this.bonusSound = bonusSound;
        this.worldBounds = worldBounds;
        v = new Vector2();
        animateInterval = 1f;
    }

    public void set(TextureRegion region, int bonusType, Vector2 pos) {
        this.regions[0] = region;
        this.bonusType = bonusType;
        this.pos.set(pos);
        v.set(0, V_Y);
        setHeightProportion(SIZE);
    }

    @Override
    public void update(float delta) {
        setScale(getScale() - 0.0008f);
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            setScale(1f);
            animateTimer = 0f;
        }
        pos.mulAdd(v, delta);
        if (getTop() <= worldBounds.getBottom()) {
            super.destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        bonusSound.play(0.4f);
    }

    public int getBonusType() {
        return bonusType;
    }
}
