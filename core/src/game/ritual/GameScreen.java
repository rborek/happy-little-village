package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import game.ritual.village.Village;
import game.ritual.village.Villager;
import game.ritual.village.VillagerRole;

public class GameScreen implements Screen {
	private RitualGame game;
	private Village village;

	public GameScreen(RitualGame game) {
		this.game = game;
		init();
	}

	private void init() {
		Texture a = new Texture("yoshi.jpg");
		village.addVillager(new Villager(VillagerRole.CITIZEN, a));
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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
