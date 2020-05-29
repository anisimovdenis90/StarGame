package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BonusPool;
import ru.geekbrains.pool.BulletsPool;
import ru.geekbrains.pool.EnemysPool;
import ru.geekbrains.pool.ExplosionsPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bonus;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPause;
import ru.geekbrains.sprite.ButtonPlay;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.ButtonNewGame;
import ru.geekbrains.sprite.HealthBar;
import ru.geekbrains.sprite.Pause;
import ru.geekbrains.sprite.PlayerShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.BonusEmitter;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final float TEXT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.015f;
    private static final String SCORES = "Scores: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private int scores;
    private int oldLevel;

    public GameScreen() {
    }

    private enum State {
        PLAYING,
        PAUSE,
        GAME_OVER
    }

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas menuAtlas;
    private ButtonExit buttonExit;
    private ButtonPlay buttonResume;
    private ButtonPause buttonPause;
    private PlayerShip playerShip;
    private BulletsPool bulletsPool;
    private EnemysPool enemysPool;
    private ExplosionsPool explosionsPool;
    private Star[] stars;
    private Music music;
    private EnemyEmitter enemyEmitter;
    private State state;
    private GameOver gameOver;
    private Pause pause;
    private ButtonNewGame buttonNewGame;
    private HealthBar healthBar;
    private Font font;
    private StringBuilder sbScores;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    private BonusPool bonusPool;
    private BonusEmitter bonusEmitter;

    @Override
    public void pause() {
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        state = State.PLAYING;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/main_atlas.atlas"));
        menuAtlas = new TextureAtlas(Gdx.files.internal("textures/menu_atlas.atlas"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
        }
        bulletsPool = new BulletsPool();
        explosionsPool = new ExplosionsPool(atlas);
        enemysPool = new EnemysPool(bulletsPool, explosionsPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlas, enemysPool);
        playerShip = new PlayerShip(atlas, bulletsPool, explosionsPool);
        healthBar = new HealthBar(atlas, playerShip);
        bonusPool = new BonusPool(worldBounds);
        bonusEmitter = new BonusEmitter(atlas, bonusPool);
        gameOver = new GameOver(atlas);
        pause = new Pause(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        buttonExit = new ButtonExit(menuAtlas);
        buttonResume = new ButtonPlay(menuAtlas, this);
        buttonPause = new ButtonPause(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        sbScores = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;
        oldLevel = 1;
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        playerShip.resize(worldBounds);
        healthBar.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        bonusEmitter.resize(worldBounds);
        gameOver.resize(worldBounds);
        pause.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonResume.resize(worldBounds);
        buttonPause.resize(worldBounds);
        font.setSize(FONT_SIZE);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        free();
        draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        menuAtlas.dispose();
        bulletsPool.dispose();
        enemysPool.dispose();
        explosionsPool.dispose();
        playerShip.dispose();
        bonusPool.dispose();
        music.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            if (buttonPause.isMe(touch)) {
                buttonPause.touchDown(touch, pointer, button);
            } else {
                playerShip.touchDown(touch, pointer, button);
            }
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
            buttonExit.touchDown(touch, pointer, button);
        } else if (state == State.PAUSE) {
            buttonResume.touchDown(touch, pointer, button);
            buttonExit.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            if (buttonPause.isMe(touch)) {
                buttonPause.touchUp(touch, pointer, button);
            } else {
                playerShip.touchUp(touch, pointer, button);
            }
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
            buttonExit.touchUp(touch, pointer, button);
        } else if (state == State.PAUSE) {
            buttonResume.touchUp(touch, pointer, button);
            buttonExit.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            playerShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            playerShip.keyUp(keycode);
        }
        return false;
    }

    public void startNewGame() {
        scores = 0;
        playerShip.reset();
        enemysPool.freeAllActiveObjects();
        bulletsPool.freeAllActiveObjects();
        bonusPool.freeAllActiveObjects();
        for (Star star : stars) {
            star.reset();
        }
        bonusEmitter.reset();
        music.setPosition(0f);
        state = State.PLAYING;
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            playerShip.draw(batch);
            enemysPool.drawActiveSprites(batch);
            bonusPool.drawActiveSprites(batch);
            healthBar.draw(batch);
            bulletsPool.drawActiveSprites(batch);
            buttonPause.draw(batch);
            printInfo();
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
            buttonExit.draw(batch);
            printResult();
        } else if (state == State.PAUSE) {
            pause.draw(batch);
            healthBar.draw(batch);
            buttonResume.draw(batch);
            buttonExit.draw(batch);
            printInfo();
        }
        explosionsPool.drawActiveSprites(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta, enemyEmitter.getLevel());
        }
        explosionsPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            playerShip.update(delta);
            healthBar.update(delta);
            bulletsPool.updateActiveSprites(delta);
            enemysPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, scores);
            bonusPool.updateActiveSprites(delta);
            bonusEmitter.generate(enemyEmitter.getLevel());
        } else if (state == State.GAME_OVER) {
            buttonNewGame.update(delta);
        }
    }

    private void checkCollision() {
        if (state != State.PLAYING) {
            return;
        }
        List<EnemyShip> enemyShips = enemysPool.getActiveObjects();
        List<Bullet> bulletList = bulletsPool.getActiveObjects();
        List<Bonus> bonusList = bonusPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShips) {
            float minDist = enemyShip.getHalfWidth() + playerShip.getHalfWidth();
            if (playerShip.pos.dst(enemyShip.pos) < minDist) {
                enemyShip.destroy();
                playerShip.damage(enemyShip.getBulletDamage());
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != playerShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {
                        scores += enemyShip.getScoresForKill();
                        bonusEmitter.generate(enemyShip);
                    }
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == playerShip || bullet.isDestroyed()) {
                continue;
            }
            if (playerShip.isCollision(bullet)) {
                playerShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (playerShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
        for (Bonus bonus : bonusList) {
            if (playerShip.isCollision(bonus)) {
                playerShip.activateBonus(bonus);
                bonus.destroy();
            }
        }
    }

    private void free() {
        bulletsPool.freeAllDestroyed();
        enemysPool.freeAllDestroyed();
        bonusPool.freeAllDestroyed();
        explosionsPool.freeAllDestroyed();
    }

    private void printInfo() {
        sbScores.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbScores.append(SCORES).append(scores), worldBounds.getLeft() + 0.118f, worldBounds.getTop() - 0.038f);
        font.draw(batch, sbHp.append(HP).append(playerShip.getHp()), worldBounds.getLeft() + 0.013f, worldBounds.getTop() - 0.037f);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.pos.x, worldBounds.getTop() - TEXT_MARGIN, Align.center);
    }

    private void printResult() {
        sbScores.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbScores.append(SCORES).append(scores), worldBounds.pos.x, worldBounds.getTop() - 0.15f, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.pos.x, worldBounds.getTop() - 0.1f, Align.center);
    }
}
