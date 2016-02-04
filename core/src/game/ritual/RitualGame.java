package game.ritual;

import com.badlogic.gdx.Game;

public class RitualGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
}
