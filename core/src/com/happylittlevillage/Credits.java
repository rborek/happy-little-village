package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Credits extends Miscellaneous {
	private Texture backButton = new Texture(Gdx.files.internal("textures/bg/backButton.png"), true);
	private Rectangle backButtonPosition = new Rectangle(700, 100, backButton.getWidth(), backButton.getHeight());

	public Credits(HappyLittleVillage happyLittleVillage) {
		super(happyLittleVillage);
		background = new Texture(Gdx.files.internal("textures/bg/credits.png"), true);
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