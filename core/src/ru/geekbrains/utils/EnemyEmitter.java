package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemysPool;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4f;
    private static final float GENERATE_INTERVAL_MIN = 2f;
    private static final int SCORES_TO_NEXT_LEVEL = 1000;

    private static final int ENEMY_SMALL_SCORE = 10;
    private static final int ENEMY_MEDIUM_SCORE = 50;
    private static final int ENEMY_BIG_SCORE = 100;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final int ENEMY_SMALL_HP = 1;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.011f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_SHOOT_INTERVAL = 3f;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final int ENEMY_MEDIUM_HP = 5;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_SHOOT_INTERVAL = 4f;

    private static final float ENEMY_BIG_HEIGHT = 0.25f;
    private static final int ENEMY_BIG_HP = 10;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_SHOOT_INTERVAL = 1f;

    private Rect worldBounds;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallV;
    private final Vector2 enemyMediumV;
    private final Vector2 enemyBigV;
    private final EnemysPool enemysPool;
    private final TextureRegion bulletRegion;

    private GameLevel gameLevel;

    public EnemyEmitter(TextureAtlas atlas, EnemysPool enemysPool, GameLevel gameLevel) {
        TextureRegion enemySmall = atlas.findRegion("enemySmall");
        TextureRegion enemyMedium = atlas.findRegion("enemyMedium");
        TextureRegion enemyBig = atlas.findRegion("enemyBig");
        this.enemySmallRegions = Regions.split(enemySmall, 1, 2, 2);
        this.enemyMediumRegions = Regions.split(enemyMedium, 1, 2, 2);
        this.enemyBigRegions = Regions.split(enemyBig, 1, 2, 2);
        this.enemySmallV = new Vector2(0, -0.2f);
        this.enemyMediumV = new Vector2(0, -0.03f);
        this.enemyBigV = new Vector2(0, -0.005f);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemysPool = enemysPool;
        this.gameLevel = gameLevel;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        float generateInterval = GENERATE_INTERVAL - 0.1f * gameLevel.getGameLevel() - 1;
        if (generateInterval < GENERATE_INTERVAL_MIN) {
            generateInterval = GENERATE_INTERVAL_MIN;
        }
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemysPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemyShip.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_BULLET_DAMAGE + gameLevel.getGameLevel() - 1,
                        ENEMY_SMALL_SHOOT_INTERVAL,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_SCORE,
                        EnemyShip.EnemyType.SMALL
                );
            } else if (type < 0.8f) {
                enemyShip.set(
                        enemyMediumRegions,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_BULLET_DAMAGE + gameLevel.getGameLevel() - 1,
                        ENEMY_MEDIUM_SHOOT_INTERVAL,
                        ENEMY_MEDIUM_HP,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_SCORE,
                        EnemyShip.EnemyType.MEDIUM
                );
            } else {
                enemyShip.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_BULLET_DAMAGE + gameLevel.getGameLevel() - 1,
                        ENEMY_BIG_SHOOT_INTERVAL,
                        ENEMY_BIG_HP,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_SCORE,
                        EnemyShip.EnemyType.BIG
                );
            }
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(), worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }
}
