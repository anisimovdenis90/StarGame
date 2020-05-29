package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("messageGameOver"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.14f);
        setTop(0.2f);
    }
}
