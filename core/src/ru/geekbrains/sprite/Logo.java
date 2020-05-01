package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private Vector2 touch;
    private Vector2 v;
    private Rect worldBounds;

    private float distance;
    private float range;

    public Logo(Texture texture) {
        super(new TextureRegion(texture, 920, 99, 188, 280));
        touch = new Vector2();
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.2f);
        pos.set(worldBounds.pos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        pos.add(v);
        range += v.len();
        if (distance - range <= v.len()) {
            pos.set(touch);
            v.setLength(0);
            range = 0;
        }
        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer, int button) {
        touch.set(target);
        checkTouch();
        distance = v.set(touch).sub(pos).len();
        range = 0;
        v.setLength(0.005f);
        return false;
    }

    private void checkTouch() {
        if (touch.x + getWidth() > worldBounds.getRight()) {
            touch.set(worldBounds.getRight() - halfWidth, touch.y);
        }
        if (touch.x - halfWidth < worldBounds.getLeft()) {
            touch.set(worldBounds.getLeft() + halfWidth, touch.y);
        }
        if (touch.y + getHeight() > worldBounds.getTop()) {
            touch.set(touch.x, worldBounds.getTop() - halfHeight);
        }
        if (touch.y - halfHeight < worldBounds.getBottom()) {
            touch.set(touch.x, worldBounds.getBottom() + halfHeight);
        }
    }
}
