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
        System.out.println("Call generate");
        float type = (float) Math.random();
        if (EnemyShip.EnemyType.BIG.equals(enemyShip.getType()) && type < 0.7f) {
            Bonus bonus = bonusPool.obtain();
            if (type < 0.2f) {
                bonus.set(firstAidRegion, Bonus.BonusType.FIRST_AID, enemyShip.pos);
                System.out.println("FirstAid generated");
            } else {
                bonus.set(superShootRegion, Bonus.BonusType.SUPER_SHOOT, enemyShip.pos);
                System.out.println("SuperShoot generated");
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
            bonus.set(firstAidRegion, Bonus.BonusType.FIRST_AID, pos0);
        }
    }

    public void reset() {
        oldLevel = 1;
    }
}
