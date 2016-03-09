package com.happylittlevillage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.Miscellaneous;

public class Credits extends Miscellaneous {
    private Texture backButton;
    private Rectangle backButtonPosition;

    public Credits(HappyLittleVillage happyLittleVillage) {
        super(happyLittleVillage);
        backButton = Assets.getTexture("menu/backButton.png");
        backButtonPosition = new Rectangle(700, 100, backButton.getWidth(), backButton.getHeight());
        background = Assets.getTexture("menu/credits.png");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button); // set the realPos
        if (backButtonPosition.contains(realPos)) {
            happyLittleVillage.setMenu();
        }
        return true;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(backButton, 800, 100);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }


}