package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MessageSpaceGame extends Sprite {

    public MessageSpaceGame(TextureAtlas atlas) {
        super(atlas.findRegion("messageSpaceGame"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.35f);
        setTop(0.3f);
    }
}
