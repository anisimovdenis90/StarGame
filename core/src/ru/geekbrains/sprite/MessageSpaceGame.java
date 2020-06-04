package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MessageSpaceGame extends Sprite {

    private static final float HEIGHT = 0.35f;
    private static final float MARGIN = 0.3f;

    public MessageSpaceGame(TextureAtlas atlas) {
        super(atlas.findRegion("messageSpaceGame"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(MARGIN);
    }
}
