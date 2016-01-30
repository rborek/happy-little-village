package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.ritual.gems.Gem;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;
import game.ritual.village.Villager;
import game.ritual.village.VillagerRole;

public class GameScreen implements Screen {
	private RitualGame game;
	private GameHandler gameHandler;
	private SpriteBatch batch;

	public GameScreen(RitualGame game) {
		this.game = game;
		batch = new SpriteBatch();
		gameHandler = new GameHandler();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		gameHandler.update(delta);
		Gdx.gl30.glClearColor(1, 1, 1, 1);
		Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batch.begin();
		gameHandler.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
