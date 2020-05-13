package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.PlayerShip;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private TextureAtlas menuAtlas;
    private TextureAtlas mainAtlas;
    private PlayerShip playerShip;

    private Star[] stars;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        menuAtlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        mainAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        playerShip = new PlayerShip(mainAtlas.findRegion("main_ship"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        playerShip.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        playerShip.draw(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        playerShip.update(delta);
    }

    @Override
    public void dispose() {
        bg.dispose();
        menuAtlas.dispose();
        mainAtlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        playerShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        playerShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        playerShip.keyUp(keycode);
        return false;
    }
}
