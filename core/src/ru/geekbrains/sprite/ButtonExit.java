package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;

public class ButtonExit extends ScaledButton {

    private static final float HEIGHT = 0.2f;
    private static final float MARGIN = 0.05f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("buttonExit"));
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
        setRight(worldBounds.getRight() - MARGIN);
    }

}
