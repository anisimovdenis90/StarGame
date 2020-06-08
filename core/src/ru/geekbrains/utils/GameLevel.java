package ru.geekbrains.utils;

import ru.geekbrains.sprite.Star;

public class GameLevel {

    private static final int SCORES_TO_NEXT_LEVEL = 1000;

    private BonusEmitter bonusEmitter;
    private Star[] stars;
    private int currentLevel;
    private int prevLevel;
    private int scores;

    public GameLevel(BonusEmitter bonusEmitter, Star[] stars) {
        this.bonusEmitter = bonusEmitter;
        this.stars = stars;
        prevLevel = currentLevel = 1;
    }

    public int getGameLevel() {
        return currentLevel;
    }

    public int getScores() {
        return scores;
    }

    public void addScores(int scores) {
        this.scores += scores;
        update();
    }

    public void update() {
        currentLevel = scores / SCORES_TO_NEXT_LEVEL + 1;
        generateBonusByNewLevel();
    }

    public void generateBonusByNewLevel() {
        if (prevLevel < currentLevel) {
            prevLevel = currentLevel;
            bonusEmitter.generate();
            for (Star star : stars) {
                star.addVy(currentLevel);
            }
        }
    }

    public void reset() {
        scores = 0;
        prevLevel = 1;
        for (Star star : stars) {
            star.addVy(prevLevel);
        }
        update();
    }
}
