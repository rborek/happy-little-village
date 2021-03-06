package com.happylittlevillage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.input.GameGestureDetector;
import com.happylittlevillage.input.InputHandler;

public class GameScreen implements Screen {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private int lastResHeight;
	private int lastResWidth;
	private Texture sun;
	private Sprite moon;
	private Vector2 sunPos = new Vector2();
	private Vector2 moonPos = new Vector2();
	private Vector2 screenPos = new Vector2();
	private HappyLittleVillage game;
	private int dayTime;
	private GameHandler gameHandler;
	private InputHandler inputHandler;
	private GameGestureDetector gameGestureDetector;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private BitmapFont font = new BitmapFont();
	private boolean android;
	private boolean isTutorial = false;

	public GameScreen(HappyLittleVillage game, boolean isTutorial) {
		this.game = game;
		this.isTutorial = isTutorial;
		sun = Assets.getTexture("bg/sun.png");
		moon = new Sprite(Assets.getTexture("bg/moon.png"));
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
		camera.update();
		viewport = new StretchViewport(WIDTH, HEIGHT, camera);
		viewport.apply();
		batch.setProjectionMatrix(camera.combined);
		inputHandler = new InputHandler(this, game);
		gameGestureDetector = new GameGestureDetector(inputHandler, this); // only change the 4th value which is the time to detect a long press
		gameHandler = new GameHandler(gameGestureDetector, inputHandler, isTutorial, game);
		inputHandler.linkTo(gameHandler);
		Gdx.input.setInputProcessor(gameGestureDetector);
		dayTime = gameHandler.getVillage().getMaxHours();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		if (delta > 0.1f) {
			delta = 0.01667f;
		}
		gameHandler.update(delta);
		float time = dayTime - gameHandler.getVillage().getHoursLeft();
		float percentage = (time / dayTime);
		float skyAlpha = getSkyAlpha(percentage * 200) + 0.2f;
		sunPos.x = percentage * WIDTH * 0.85f;
		sunPos.y = 485 + skyAlpha * 125f;
		Gdx.gl.glClearColor(0, (60 / 255f) + Math.max(131 / 255f * skyAlpha, 0), (90 / 255f) + Math.max(165 / 255f * skyAlpha, 0), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		boolean moonShow = time > gameHandler.getVillage().getMaxHours() / 2;
		if (!gameHandler.isPaused() && !moonShow) batch.draw(sun, sunPos.x, sunPos.y, 90, 90);
		if (moonShow) {
			float moonAlpha = Math.min((float) (1.6 - 25 * Math.abs(3.0 * gameHandler.getVillage().getMaxHours() / 4.0 - time) / gameHandler.getVillage().getMaxHours() / 4.0), 1);
			moon.setPosition(440, 620);
			moon.setAlpha(moonAlpha);
			moon.draw(batch);
		}
		gameHandler.render(batch);
		batch.end();
	}

	public Vector2 getRealScreenPos(float mouseX, float mouseY) {
		screenPos.set(mouseX, mouseY);
		return viewport.unproject(screenPos);
	}

	private float getSkyAlpha(float x) {
		float alpha = (float) (Math.sqrt((50f * 50f) - (float) Math.pow(x - 50, 2)) / 50f);
		if (Float.isNaN(alpha)) {
			return -1;
		}
		return alpha;
	}

	@Override
	public void resize(int width, int height) {
		if (width != lastResWidth || height != lastResHeight) {
			viewport.update(width, height);
			Assets.updateFonts();
			lastResWidth = width;
			lastResHeight = height;
		}
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
