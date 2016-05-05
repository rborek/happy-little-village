package com.happylittlevillage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.Assets;
import com.happylittlevillage.rituals.Ritual;

public class LoadingScreen extends ScreenAdapter {
	protected HappyLittleVillage happyLittleVillage;
	protected OrthographicCamera camera;
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected Vector2 screenPos = new Vector2();
	protected int lastResHeight;
	protected int lastResWidth;
	private boolean isTutorial;
	private GameObject background;
	private GameObject[] dots = new GameObject[3];
	private float timer = 0;


	public LoadingScreen(HappyLittleVillage happyLittleVillage, boolean isTutorial) {
		this.happyLittleVillage = happyLittleVillage;
		this.isTutorial = isTutorial;
		camera = new OrthographicCamera();
		camera.position.set(GameScreen.WIDTH / 2f, GameScreen.HEIGHT / 2f, 0);
		camera.update();
		viewport = new StretchViewport(GameScreen.WIDTH, GameScreen.HEIGHT, camera);
		viewport.apply();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		background = new GameObject(Assets.getTexture("menu/loading_screen.png"), 0, 0);
		for (int i = 0; i < 3; i++) {
			dots[i] = new GameObject(Assets.getTexture("menu/loading_dot.png"), 80 * i, 0);
		}

		Assets.load();
	}

	@Override
	public void render(float delta) {
		timer += delta;
		int frame = (int) (timer * 2 % 4);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.render(batch);
		for (int i = 0; i <= frame; i++) {
			if (i > 0) {
				dots[i - 1].render(batch);
			}
		}
		batch.end();
		if (Assets.update()) {
			Ritual.load();
			happyLittleVillage.switchToGameScreen(isTutorial);
		}
	}

	@Override
	public void resize(int width, int height) {
		//Took this from GameScreen
		if (width != lastResWidth || height != lastResHeight) {
			viewport.update(width, height);
			lastResWidth = width;
			lastResHeight = height;
		}
	}

}
