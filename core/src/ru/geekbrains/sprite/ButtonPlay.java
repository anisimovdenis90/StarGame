package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private static final float MARGIN = 0.05f;

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        setBottom(worldBounds.getBottom() + MARGIN);
        setLeft(worldBounds.getLeft() + MARGIN);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
