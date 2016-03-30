package com.happylittlevillage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
//    private Rectangle optionsButtonPosition;
//    private Rectangle creditsButtonPosition;
    private Rectangle tutorialButtonPosition;
    private Rectangle loadButtonPosition;
    private Rectangle exitButtonPosition;
    private static int buttonX = 862;
    private static int buttonY = 80;

    public MainMenu(HappyLittleVillage happyLittleVillage) {
        super(happyLittleVillage);
        startButton = new GameObject(Assets.getTexture("menu/start_button.png"), buttonX, buttonY + 450, 360, 150);
        tutorialButton = new GameObject(Assets.getTexture("menu/tutorial_button.png"), buttonX, buttonY + 325, 360, 150);
        loadButton = new GameObject(Assets.getTexture("menu/load_button.png"), buttonX, buttonY + 200, 360, 150);
//        optionsButton = new GameObject(Assets.getTexture("menu/options_button.png"), buttonX, buttonY + 75);
//        creditsButton = new GameObject(Assets.getTexture("menu/credits_button.png"), buttonX, buttonY);
        exitButton = new GameObject(Assets.getTexture("menu/exit_button.png"), buttonX, buttonY + 50, 360, 150);
        exitButtonPosition = new Rectangle(buttonX, buttonY - 100 , exitButton.getWidth(), exitButton.getHeight());
//        creditsButtonPosition = new Rectangle(buttonX, buttonY  , creditsButton.getWidth(), creditsButton.getHeight());
//        optionsButtonPosition = new Rectangle(buttonX, buttonY + 75, optionsButton.getWidth(), optionsButton.getHeight());
        loadButtonPosition = new Rectangle(buttonX, buttonY + 200, loadButton.getWidth(), loadButton.getHeight());
        tutorialButtonPosition = new Rectangle(buttonX, buttonY + 325, tutorialButton.getWidth(), tutorialButton.getHeight());
        startButtonPosition = new Rectangle(buttonX, buttonY + 450, startButton.getWidth(), startButton.getHeight());
        background = Assets.getTexture("menu/menu.png");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button); // set the realPos
        System.out.println(realPos);
//        if (creditsButtonPosition.contains(realPos)) {
//            happyLittleVillage.setCredits();
//            return true;
//        }
        if (startButtonPosition.contains(realPos)) {
            happyLittleVillage.setLoadingScreen(false);
            return true;
//        } else if (optionsButtonPosition.contains(realPos)) {
//            happyLittleVillage.setOptions();
//            return true;
        } else if (tutorialButtonPosition.contains(realPos)) {
            happyLittleVillage.setLoadingScreen(true);
        } else if(loadButtonPosition.contains(realPos)){
            happyLittleVillage.setLoad();
        } else if(exitButtonPosition.contains(realPos)){
            happyLittleVillage.setExit();
            return true;
        }
        return false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        startButton.render(batch);
        tutorialButton.render(batch);
        loadButton.render(batch);
//        optionsButton.render(batch);
//        creditsButton.render(batch);
        exitButton.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }


}
