package com.happylittlevillage;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.happylittlevillage.menu.Credits;
import com.happylittlevillage.menu.GameScreen;
import com.happylittlevillage.menu.MainMenu;
import com.happylittlevillage.menu.Options;

public class HappyLittleVillage extends Game implements ApplicationListener {
	private GameScreen gameScreen;
	private MainMenu mainMenu;
	private Credits credits;
	private Options options;

	@Override
	public void create() {
		setMenu();
	}

	public void setGameScreen(boolean isTutorial) {
		if (gameScreen == null) {
			gameScreen = new GameScreen(this,isTutorial);
		}
		if (mainMenu != null) {
			mainMenu.dispose();
		}
		mainMenu = null;
		if (options != null) {
			options.dispose();
		}
		options = null;
		if (credits != null) {
			credits.dispose();
		}
		credits = null;
		Assets.unloadDir("textures/menu");
		setScreen(gameScreen);
	}

	public void setMenu() {
		if (mainMenu == null) {
			Assets.loadMenuTextures();
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
