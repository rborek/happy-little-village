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
		Assets.loadMenuTextures();
		if (mainMenu == null) {
			mainMenu = new MainMenu(this);
		}
		setScreen(mainMenu);
	}

	public void setOptions() {
		if (options == null) {
			options = new Options(this);
		}
		setScreen(options);
	}

	public void setCredits() {
		if (credits == null) {
			credits = new Credits(this);
		}
		setScreen(credits);
	}


}
