package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Ship;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletsPool;
import ru.geekbrains.pool.EnemysPool;
import ru.geekbrains.pool.ExplosionsPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.PlayerShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private PlayerShip playerShip;
    private BulletsPool bulletsPool;
    private EnemysPool enemysPool;
    private ExplosionsPool explosionsPool;
    private Star[] stars;
    private Music music;
    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletsPool = new BulletsPool();
        explosionsPool = new ExplosionsPool(atlas);
        enemysPool = new EnemysPool(bulletsPool,explosionsPool,  worldBounds);
        enemyEmitter = new EnemyEmitter(atlas, enemysPool);
        playerShip = new PlayerShip(atlas, bulletsPool, explosionsPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        playerShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletsPool.drawActiveSprites(batch);
        enemysPool.drawActiveSprites(batch);
        explosionsPool.drawActiveSprites(batch);
        playerShip.draw(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletsPool.updateActiveSprites(delta);
        enemysPool.updateActiveSprites(delta);
        explosionsPool.updateActiveSprites(delta);
        playerShip.update(delta);
        enemyEmitter.generate(delta);
        for (Ship enemyShip : enemysPool.getActiveObjects()) {
            for (Bullet bullet : bulletsPool.getActiveObjects()) {
                if (enemyShip.isVisible() && isCollision(enemyShip, playerShip)) {
                    enemyShip.destroy();
                } else if (bullet.getOwner() instanceof PlayerShip && isCollision(enemyShip, bullet)) {
                    enemyShip.destroy();
                }
            }
        }
    }

    private void free() {
        bulletsPool.freeAllDestroyed();
        enemysPool.freeAllDestroyed();
        explosionsPool.freeAllDestroyed();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletsPool.dispose();
        explosionsPool.dispose();
        enemysPool.dispose();
        playerShip.dispose();
        music.dispose();
        super.dispose();
    }

    private boolean isCollision(Sprite enemy, Sprite player) {
        return enemy.getBottom() <= player.getTop() &&
                ((player.getLeft() >= enemy.getLeft() && player.getLeft() <= enemy.getRight()) ||
                (player.getRight() >= enemy.getLeft() && player.getRight() <= enemy.getRight()));
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
