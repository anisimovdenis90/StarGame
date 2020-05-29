package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class HealthBar extends Sprite {

    private static final float MARGIN = 0.003f;
    private static final float FILL_HEIGHT = 0.015f;
    private static final float FILL_MARGIN_TOP = 0.011f;
    private static final float FILL_MARGIN_LEFT = 0.01f;

    private PlayerShip playerShip;

    private Sprite healthFill;

    public HealthBar(TextureAtlas atlas, PlayerShip playerShip) {
        super(atlas.findRegion("healthBar"));
        this.playerShip = playerShip;
        healthFill = new Sprite(atlas.findRegion("healthFill"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.06f);
        setTop(worldBounds.getTop());
        setLeft(worldBounds.getLeft() + MARGIN);
        healthFill.setHeight(FILL_HEIGHT);
        healthFill.setTop(getTop() - FILL_MARGIN_TOP);
    }

    @Override
    public void update(float delta) {
        healthFill.setWidth((getWidth() - 0.025f) * playerShip.getHp() / 100);
        healthFill.setLeft(getLeft() + FILL_MARGIN_LEFT);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        healthFill.draw(batch);
    }
}
