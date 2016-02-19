package com.happylittlevillage;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class HappyLittleVillage extends Game implements ApplicationListener {
	private GameScreen gameScreen;
	private Menu menu;
	private Credits credits;
	private Options options;

	@Override
	public void create() {
		setMenu();
	}
	public void setGameScreen(){
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}
	public void setMenu(){
		menu = new Menu(this);
		setScreen(menu);
	}
	public void setOptions(){
		options = new Options(this);
		setScreen(options);
	}
	public void setCredits(){
		credits = new Credits(this);
		setScreen(credits);
	}


}
