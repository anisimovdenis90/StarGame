package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Pause extends Sprite {

    public Pause(TextureAtlas atlas) {
        super(atlas.findRegion("messagePause"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.18f);
        setTop(0.2f);
    }
}
