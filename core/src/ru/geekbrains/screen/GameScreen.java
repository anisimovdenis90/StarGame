package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Exit;
import ru.geekbrains.sprite.Logo;

public class GameScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Texture exit;
    private Background background;
    private Logo logo;
    private Exit ex;

    @Override
    public void show() {
        super.show();
        img = new Texture("textures/mainAtlas.png");
        bg = new Texture("textures/background.jpg");
        exit = new Texture("textures/menuAtlas.png");
        background = new Background(bg);
        logo = new Logo(img);
        ex = new Exit(exit);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
        ex.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        ex.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        exit.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        ex.touchDown(touch, pointer, button);
        return false;
    }
}
