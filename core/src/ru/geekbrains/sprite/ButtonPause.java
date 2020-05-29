package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonPause extends ScaledButton {

    private static final float MARGIN = 0.01f;

    private GameScreen gameScreen;

    public ButtonPause(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("buttonPause"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void action() {
        gameScreen.pause();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setTop(worldBounds.getTop() - MARGIN);
        setRight(worldBounds.getRight() - MARGIN);
    }
}
