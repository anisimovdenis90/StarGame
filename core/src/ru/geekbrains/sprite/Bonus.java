
package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Bonus extends Sprite {

    public enum BonusType {
        FIRST_AID,
        SUPER_SHOOT
    }

    private static final float HEIGHT = 0.09f;
    private static final float V_Y = -0.2f;
    private static final float BONUS_SOUND_VOLUME = 0.5f;
    private static final float ANIMATE_INTERVAL = 0.8f;
    private static final float ANIMATION_SCALE_STEP = 0.008f;

    private Sound bonusSound;
    private Rect worldBounds;
    private Vector2 v;
    private BonusType bonusType;

    private float animateTimer;

    private boolean scaleUp = true;

    public Bonus(Sound bonusSound, Rect worldBounds) {
        regions = new TextureRegion[1];
        this.bonusSound = bonusSound;
        this.worldBounds = worldBounds;
        v = new Vector2();
    }

    public void set(TextureRegion region, BonusType bonusType, Vector2 pos) {
        this.regions[0] = region;
        this.bonusType = bonusType;
        this.pos.set(pos);
        v.set(0, V_Y);
        setHeightProportion(HEIGHT);
        animateTimer = 0f;
        scaleUp = false;
        setScale(1f);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            scaleUp = !scaleUp;
        }
        if (scaleUp) {
            setScale(getScale() + ANIMATION_SCALE_STEP);
        } else {
            setScale(getScale() - ANIMATION_SCALE_STEP);
        }
        pos.mulAdd(v, delta);
        if (getTop() <= worldBounds.getBottom()) {
            super.destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        bonusSound.play(BONUS_SOUND_VOLUME);
    }

    public BonusType getBonusType() {
        return bonusType;
    }
}
