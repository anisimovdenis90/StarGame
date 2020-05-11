package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Exit;
import ru.geekbrains.sprite.Play;

public class MenuScreen extends BaseScreen {

    private Game game;

    private Texture bg;
    private Texture exit;
    private Texture play;
    private Background background;
    private Exit ex;
    private Play pl;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        exit = new Texture("textures/menuAtlas.png");
        play = new Texture("textures/menuAtlas.png");
        background = new Background(bg);
        ex = new Exit(exit);
        pl = new Play(play, game);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        ex.resize(worldBounds);
        pl.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        ex.draw(batch);
        pl.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        exit.dispose();
        play.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ex.touchDown(touch, pointer, button);
        pl.touchDown(touch, pointer, button);
        return false;
    }
}
