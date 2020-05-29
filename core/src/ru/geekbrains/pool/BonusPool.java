package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Bonus;

public class BonusPool extends SpritesPool<Bonus> {

    private Rect worldBounds;
    private Sound bonusSound;

    public BonusPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
        bonusSound = Gdx.audio.newSound(com.badlogic.gdx.Gdx.files.internal("sounds/bonusSound.wav"));
    }

    @Override
    protected Bonus newObject() {
        return new Bonus(bonusSound, worldBounds);
    }

    public void dispose() {
        super.dispose();
        bonusSound.dispose();
    }
}
