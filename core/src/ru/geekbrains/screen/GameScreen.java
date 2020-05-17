package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletsPool;
import ru.geekbrains.pool.EnemysPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.PlayerShip;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private TextureAtlas menuAtlas;
    private TextureAtlas mainAtlas;
    private PlayerShip playerShip;
    private BulletsPool bulletsPool;
    private EnemysPool enemysPool;
    private Rect worldBounds;
    private Star[] stars;
    private Music music;
    private Vector2 enemyV;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        menuAtlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        mainAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
        }
        bulletsPool = new BulletsPool();
        enemysPool = new EnemysPool();
        enemyV = new Vector2(0, -0.3f);
        playerShip = new PlayerShip(mainAtlas, bulletsPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        playerShip.resize(worldBounds);
    }

    private void createEnemy() {
        EnemyShip enemyShip = enemysPool.obtain();
        enemyShip.set(worldBounds, mainAtlas.findRegion("enemy1"), enemyV, 0.15f, 0f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
        if (enemysPool.getActiveObjects().isEmpty()) {
            createEnemy();
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletsPool.drawActiveSprites(batch);
        enemysPool.drawActiveSprites(batch);
        playerShip.draw(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletsPool.updateActiveSprites(delta);
        enemysPool.updateActiveSprites(delta);
        playerShip.update(delta);
    }

    private void free() {
        bulletsPool.freeAllDestroyed();
        enemysPool.freeAllDestroyed();
    }

    @Override
    public void dispose() {
        bg.dispose();
        menuAtlas.dispose();
        mainAtlas.dispose();
        bulletsPool.dispose();
        enemysPool.dispose();
        playerShip.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        playerShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        playerShip.touchUp(touch, pointer, button);
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
