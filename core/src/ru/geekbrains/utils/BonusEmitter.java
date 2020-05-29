package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BonusPool;
import ru.geekbrains.sprite.Bonus;
import ru.geekbrains.sprite.EnemyShip;

public class BonusEmitter {

    private int oldLevel;

    private Rect worldBounds;
    private BonusPool bonusPool;

    private final TextureRegion firstAidRegion;
    private final TextureRegion superShootRegion;

    private static final int FIRST_AID_TYPE = 0;
    private static final int SUPER_SHOOT_TYPE = 1;

    public BonusEmitter(TextureAtlas atlas, BonusPool bonusPool) {
        firstAidRegion = atlas.findRegion("bonusFirstAid");
        superShootRegion = atlas.findRegion("bonusSuperShoot");
        this.bonusPool = bonusPool;
        oldLevel = 1;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void generate(EnemyShip enemyShip) {
        float type = (float) Math.random();
        if (enemyShip.getType() == 2 && type < 0.8f) {
            Bonus bonus = bonusPool.obtain();
            if (type <= 0.2f) {
                bonus.set(firstAidRegion, FIRST_AID_TYPE, enemyShip.pos);
            } else {
                bonus.set(superShootRegion, SUPER_SHOOT_TYPE, enemyShip.pos);
            }
        }
    }

    public void generate(int level) {
        if(oldLevel != level) {
            oldLevel = level;
            Bonus bonus = bonusPool.obtain();
            Vector2 pos0 = new Vector2();
            float posX = Rnd.nextFloat(worldBounds.getLeft() + bonus.getHalfWidth(), worldBounds.getRight() - bonus.getHalfWidth());
            float posY = worldBounds.getTop() + bonus.getHeight();
            pos0.set(posX, posY);
            bonus.set(firstAidRegion, FIRST_AID_TYPE, pos0);
        }
    }

    public void reset() {
        oldLevel = 1;
    }
}
