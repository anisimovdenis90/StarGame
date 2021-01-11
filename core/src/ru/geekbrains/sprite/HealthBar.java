package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class HealthBar extends Sprite {

    private static final float HEIGHT = 0.06f;
    private static final float LEFT_MARGIN = 0.003f;
    private static final float FILLER_HEIGHT = 0.015f;
    private static final float FILLER_MARGIN_TOP = 0.011f;
    private static final float FILLER_MARGIN_LEFT = 0.01f;

    private PlayerShip playerShip;

    private Sprite healthBarFiller;

    public HealthBar(TextureAtlas atlas, PlayerShip playerShip) {
        super(atlas.findRegion("healthBar"));
        this.playerShip = playerShip;
        healthBarFiller = new Sprite(atlas.findRegion("healthBarFiller"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(worldBounds.getTop());
        setLeft(worldBounds.getLeft() + LEFT_MARGIN);
        healthBarFiller.setHeight(FILLER_HEIGHT);
        healthBarFiller.setTop(getTop() - FILLER_MARGIN_TOP);
    }

    @Override
    public void update(float delta) {
        healthBarFiller.setWidth((getWidth() - 0.025f) * playerShip.getHp() / PlayerShip.getMaxHp());
        healthBarFiller.setLeft(getLeft() + FILLER_MARGIN_LEFT);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        healthBarFiller.draw(batch);
    }
}
