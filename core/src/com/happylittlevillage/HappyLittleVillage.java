package com.happylittlevillage;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.happylittlevillage.screens.Exit;
import com.happylittlevillage.screens.*;

public class HappyLittleVillage extends Game implements ApplicationListener {
	private GameScreen gameScreen;
	private MainMenu mainMenu;
	private Credits credits;
	private Options options;
	private Exit exit;
	private Load load;
	private LoadingScreen loadingScreen;

	@Override
	public void create() {
		setMenu();
	}

	public void setLoadingScreen(boolean isTutorial) {
		loadingScreen = new LoadingScreen(this, isTutorial);
		setScreen(loadingScreen);
	}

	public void setGameScreen(boolean isTutorial) {
		if (gameScreen == null) {
			gameScreen = new GameScreen(this, isTutorial);
		} else {
			gameScreen.dispose();
			gameScreen = new GameScreen(this, isTutorial);
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
		if (exit != null) {
			exit.dispose();
		}
		exit = null;
		if (load != null) {
			load.dispose();
		}
		load = null;
		Assets.unloadDir("data/textures/menu");
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

	public void setLoad() {
		if (load == null) {
			load = new Load(this);
		}
		setScreen(load);
	}

	public void setExit() {
		if (exit == null) {
			exit = new Exit(this);
		}
		setScreen(exit);
	}
}
