package com.happylittlevillage.input;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.screens.GameScreen;

public class GameGestureDetector extends GestureDetector {

	private InputHandler inputHandler;
	private GameScreen screen;

	public GameGestureDetector(InputHandler inputHandler, GameScreen screen) {
		super(inputHandler);
		this.inputHandler = inputHandler;
		this.screen = screen;
		setLongPressSeconds(0.16f);
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		Vector2 realPos = screen.getRealScreenPos(x, y);
		inputHandler.dropGem(realPos.x, realPos.y);
		inputHandler.dropRitual(realPos.x, realPos.y);
		return super.touchUp(realPos.x, realPos.y, pointer, button);
	}

}