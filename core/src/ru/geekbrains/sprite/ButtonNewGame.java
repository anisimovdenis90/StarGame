package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private static final float HEIGHT = 0.1f;
    private static final float MARGIN = -0.05f;
    private static final float ANIMATE_INTERVAL = 1f;
    private static final float ANIMATION_SCALE_STEP = 0.003f;

    private GameScreen gameScreen;
    private float animateTimer;

    private boolean scaleUp = true;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("buttonNewGame"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            scaleUp = !scaleUp;
        }
        if (scaleUp) {
            setScale(getScale() + ANIMATION_SCALE_STEP);
        } else {
            setScale(getScale() - ANIMATION_SCALE_STEP);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(MARGIN);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }

    public boolean keyDown(int keycode) {
        if (isPressed()) {
            return false;
        }
        if (keycode == Input.Keys.ENTER) {
            scale = getPressedScale();
            setPressed(true);
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        if (!isPressed()) {
            return false;
        }
        if (keycode == Input.Keys.ENTER) {
            action();
            scale = getDefaultScale();
            setPressed(false);
        }
        return false;
    }
}
