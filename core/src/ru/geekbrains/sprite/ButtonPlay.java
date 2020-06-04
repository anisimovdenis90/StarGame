package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private static final float HEIGHT = 0.25f;
    private static final float MARGIN = 0.05f;

    private Game game;

    private GameScreen gameScreen;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("buttonPlay"));
        this.game = game;
    }

    public ButtonPlay(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("buttonPlay"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
        setLeft(worldBounds.getLeft() + MARGIN);
    }

    @Override
    public void action() {
        if (game != null) {
            game.setScreen(new GameScreen());
        } else if (gameScreen != null) {
            gameScreen.resume();
        }
    }

    public boolean keyDown(int keycode) {
        if (isPressed()) {
            return false;
        }
        switch (keycode) {
            case Input.Keys.ENTER:
            case Input.Keys.SPACE:
            case Input.Keys.P:
                scale = getPressedScale();
                setPressed(true);
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        if (!isPressed()) {
            return false;
        }
            switch (keycode) {
                case Input.Keys.ENTER:
                case Input.Keys.SPACE:
                case Input.Keys.P:
                    action();
                    scale = getDefaultScale();
                    setPressed(false);
                    break;
            }
        return false;
    }
}
