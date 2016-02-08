package com.happylittlevillage;

import com.badlogic.gdx.Game;

public class HappyLittleVillage extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
}
