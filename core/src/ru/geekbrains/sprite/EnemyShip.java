package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.utils.Regions;

public class EnemyShip extends Sprite {

    private Rect worldBounds;
    private final Vector2 v;

    public EnemyShip() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    public void set(Rect worldBounds, TextureRegion region, Vector2 v0, float size, float margin) {
        this.worldBounds = worldBounds;
        this.regions = Regions.split(region, 1, 2, 2);
        v.set(v0);
        setHeightProportion(size);
        setTop(worldBounds.getTop() - margin);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }
}
