package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Font extends BitmapFont {

    private float capHeight;

    public Font(String fontFile, String imageFile) {
        super(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false, false);
        getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        capHeight = getCapHeight();
    }

    public void setSize(float size) {
        getData().setScale(size / capHeight);
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int halign) {
        return super.draw(batch, str, x, y,0f, halign, false);
    }
}
