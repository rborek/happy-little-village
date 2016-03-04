package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu extends Miscellaneous {
    private Texture optionsButton;
    private Texture startButton;
    private Texture creditsButton;
    private Texture tutorialButton;
    private Rectangle startButtonPosition;
    private Rectangle optionsButtonPosition;
    private Rectangle creditsButtonPosition;
    private Rectangle tutorialButtonPosition;
    private static int buttonX = 800;
    private static int buttonY = 100;

    public MainMenu(HappyLittleVillage happyLittleVillage) {
        super(happyLittleVillage);
        startButton = Assets.getTexture("menu/startButton.png");
        creditsButton = Assets.getTexture("menu/creditsButton.png");
        optionsButton = Assets.getTexture("menu/optionsButton.png");
        tutorialButton = Assets.getTexture("menu/tutorialButton.png");
        creditsButtonPosition = new Rectangle(buttonX, buttonY, creditsButton.getWidth(), creditsButton.getHeight());
        optionsButtonPosition = new Rectangle(buttonX, buttonY + 150, optionsButton.getWidth(), optionsButton.getHeight());
        tutorialButtonPosition = new Rectangle(buttonX, buttonY + 300, tutorialButton.getWidth(), tutorialButton.getHeight());
        startButtonPosition = new Rectangle(buttonX, buttonY + 450, startButton.getWidth(), startButton.getHeight());
        background = Assets.getTexture("menu/menu.png");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button); // set the realPos
        System.out.println(realPos);
        if (creditsButtonPosition.contains(realPos)) {
            happyLittleVillage.setCredits();
            return true;
        } else if (startButtonPosition.contains(realPos)) {
            happyLittleVillage.setGameScreen();
            return true;
        } else if (optionsButtonPosition.contains(realPos)) {
            happyLittleVillage.setOptions();
            return true;
        } else if(tutorialButtonPosition.contains(realPos)){
            happyLittleVillage.setTutorialScreen();
        }
        else {
            System.out.println("not contain");
        }
        return false;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(startButton, buttonX, buttonY + 450);
        batch.draw(tutorialButton, buttonX, buttonY + 300);
        batch.draw(optionsButton, buttonX, buttonY + 150);
        batch.draw(creditsButton, buttonX, buttonY);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }


}
