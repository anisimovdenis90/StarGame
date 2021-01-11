package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Explosion extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.017f;
    private static final float EXPLOSION_SOUND_VOLUME = 0.4f;

    private float animateTimer;

    private Sound sound;

    public Explosion(TextureAtlas atlas, Sound sound) {
        super(atlas.findRegion("explosion"), 6, 8, 46);
        this.sound = sound;
    }

    public void set(float height, Vector2 pos) {
        setHeightProportion(height);
        this.pos.set(pos);
        sound.play(EXPLOSION_SOUND_VOLUME);
        frame = 0;
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }


}
