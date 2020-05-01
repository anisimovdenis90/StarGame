package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Exit extends Sprite {

    public Exit(Texture texture) {
        super(new TextureRegion(texture, 8, 26, 234, 234));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        pos.set(worldBounds.getRight() - getWidth(), worldBounds.getTop() - getHeight());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (isMe(touch)) {
            setScale(0.6f);
            Gdx.app.exit();
        }
        return false;
    }
}
