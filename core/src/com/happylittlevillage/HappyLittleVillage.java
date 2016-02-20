package com.happylittlevillage;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class HappyLittleVillage extends Game implements ApplicationListener {
    private GameScreen gameScreen;
    private MainMenu mainMenu;
    private Credits credits;
    private Options options;

    @Override
    public void create() {
         setMenu();

    }

    public void setGameScreen() {
        if (gameScreen == null) {
            gameScreen = new GameScreen(this);
        }
        setScreen(gameScreen);
    }

    public void setMenu() {
        if (mainMenu == null) {
            mainMenu = new MainMenu(this);
            System.out.println("Already setMenu in Screen");
        }
        setScreen(mainMenu);
    }

    public void setOptions() {
        if (options == null) {
            options = new Options(this);
        }
        setScreen(options);
        System.out.println("Already setOptions in Screen");
    }

    public void setCredits() {
        if (credits == null) {
            credits = new Credits(this);
        }
        setScreen(credits);
        System.out.println("Already setCredits");
    }


}
