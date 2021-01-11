package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Background extends Sprite {

    private static final float HEIGHT = 1f;

    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        this.pos.set(worldBounds.pos);
    }
}
