package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Pause extends Sprite {

    private static final float HEIGHT = 0.18f;
    private static final float MARGIN = 0.2f;

    public Pause(TextureAtlas atlas) {
        super(atlas.findRegion("messagePause"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(MARGIN);
    }
}
