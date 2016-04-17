package com.happylittlevillage.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.Assets;

public class Options extends Miscellaneous {
	private float xPos = 800;
	private float yPos = 400;
	private Texture buttonScale;
	private Rectangle buttonScalePosition;
	private Texture bar;
	private Rectangle barPosition;

	public Options(HappyLittleVillage happyLittleVillage) {
		super(happyLittleVillage);
		background = Assets.getTexture("menu/options.png");
		backButton = Assets.getTexture("menu/backButton.png");
		backButtonPosition = new Rectangle(700, 100, backButton.getWidth(), backButton.getHeight());
		buttonScale = Assets.getTexture("menu/buttonScale.png");
		buttonScalePosition = new Rectangle(20, 20, buttonScale.getWidth(), buttonScale.getHeight());
		bar = Assets.getTexture("menu/bar.png");
		barPosition = new Rectangle(800, 400 - 20, bar.getWidth(), bar.getHeight() + 40);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		super.touchDown(screenX, screenY, pointer, 0); // set the realPos
		if (barPosition.contains(realPos)) {
			xPos = realPos.x;
			if (xPos > 800 + bar.getWidth() - buttonScale.getWidth() / 2) {
				xPos = barPosition.x + bar.getWidth() - buttonScale.getWidth() / 2;
			} else if (xPos < barPosition.x) {
				xPos = barPosition.x;
			}
		}
		return super.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button); // set the realPos
		if (backButtonPosition.contains(realPos)) {
			happyLittleVillage.setMenu();
		}

		return true;
	}

	private void setPosition() {
		xPos = realPos.x;
		yPos = realPos.y;
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(backButton, 800, 100);
		batch.draw(bar, 800, 400);
		batch.draw(buttonScale, xPos, yPos + 5);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}


}

