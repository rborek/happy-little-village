package com.happylittlevillage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.Miscellaneous;

public class MainMenu extends Miscellaneous {
    private GameObject optionsButton;
    private GameObject startButton;
    private GameObject creditsButton;
    private GameObject tutorialButton;
    private GameObject loadButton;
    private GameObject exitButton;
    private Rectangle startButtonPosition;
    private Rectangle optionsButtonPosition;
    private Rectangle creditsButtonPosition;
    private Rectangle tutorialButtonPosition;
    private Rectangle loadButtonPosition;
    private Rectangle exitButtonPosition;
    private static int buttonX = 800;
    private static int buttonY = 100;

    public MainMenu(HappyLittleVillage happyLittleVillage) {
        super(happyLittleVillage);
        startButton = new GameObject(Assets.getTexture("menu/startButton.png"), buttonX, buttonY + 450);
        tutorialButton = new GameObject(Assets.getTexture("menu/tutorialButton.png"), buttonX, buttonY + 325);
        loadButton = new GameObject(Assets.getTexture("menu/loadButton.png"), buttonX, buttonY + 200);
        optionsButton = new GameObject(Assets.getTexture("menu/optionsButton.png"), buttonX, buttonY + 75);
        creditsButton = new GameObject(Assets.getTexture("menu/creditsButton.png"), buttonX, buttonY);
        exitButton = new GameObject(Assets.getTexture("menu/exitButton.png"), buttonX, buttonY -100);
        exitButtonPosition = new Rectangle(buttonX, buttonY - 100 , exitButton.getWidth(), exitButton.getHeight());
        creditsButtonPosition = new Rectangle(buttonX, buttonY  , creditsButton.getWidth(), creditsButton.getHeight());
        optionsButtonPosition = new Rectangle(buttonX, buttonY + 75, optionsButton.getWidth(), optionsButton.getHeight());
        loadButtonPosition = new Rectangle(buttonX, buttonY + 200, loadButton.getWidth(), loadButton.getHeight());
        tutorialButtonPosition = new Rectangle(buttonX, buttonY + 325, tutorialButton.getWidth(), tutorialButton.getHeight());
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
            happyLittleVillage.setGameScreen(false);
            return true;
        } else if (optionsButtonPosition.contains(realPos)) {
            happyLittleVillage.setOptions();
            return true;
        } else if (tutorialButtonPosition.contains(realPos)) {
            happyLittleVillage.setGameScreen(true);
        } else if(loadButtonPosition.contains(realPos)){
            happyLittleVillage.setLoad();
        } else if(exitButtonPosition.contains(realPos)){
            happyLittleVillage.setExit();
        }
        return false;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0);
        startButton.render(batch);
        tutorialButton.render(batch);
        loadButton.render(batch);
        optionsButton.render(batch);
        creditsButton.render(batch);
        exitButton.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }


}
