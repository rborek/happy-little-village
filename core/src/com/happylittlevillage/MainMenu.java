package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu extends Miscellaneous {
	private Texture optionsButton = Assets.getTexture("bg/optionsButton.png");
	private Texture startButton = new Texture(Gdx.files.internal("textures/bg/startButton.png"), true);
	private Texture creditsButton = new Texture(Gdx.files.internal("textures/bg/creditsButton.png"), true);
	private Texture[] buttons = {optionsButton, startButton, creditsButton};
	private Rectangle startButtonPosition = new Rectangle(800, 500, startButton.getWidth(), startButton.getHeight());
	private Rectangle optionsButtonPosition = new Rectangle(800, 300, optionsButton.getWidth(), optionsButton.getHeight());
	private Rectangle creditsButtonPosition = new Rectangle(800, 100, creditsButton.getWidth(), creditsButton.getHeight());

	public MainMenu(HappyLittleVillage happyLittleVillage) {
		super(happyLittleVillage);
		background = new Texture(Gdx.files.internal("textures/bg/menu.png"), true);
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
		} else {
			System.out.println("not contain");
		}
		return false;
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(startButton, 800, 500);
		batch.draw(optionsButton, 800, 300);
		batch.draw(creditsButton, 800, 100);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}


}
