package com.happylittlevillage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.input.GameGestureDetector;
import com.happylittlevillage.input.InputHandler;
import com.happylittlevillage.Assets;

public class GameScreen implements Screen {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private int lastResHeight;
	private int lastResWidth;
	private Texture sun;
	private Vector2 sunPos = new Vector2();
	private Vector2 screenPos = new Vector2();
	private HappyLittleVillage game;
	private int dayTime;
	private GameHandler gameHandler;
	private InputHandler inputHandler;
	private GameGestureDetector gameGestureDetector;
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
		batch = new SpriteBatch();
//		if (Gdx.app.getType() == Application.ApplicationType.Android || !Gdx.graphics.isGL30Available()) {
//			batch = new SpriteBatch();
//		} else {
//			batch = new SpriteBatch(1000, createDefaultShader());
//		}

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
		float skyAlpha = getSkyAlpha(percentage * 100) + 0.2f;
		sunPos.x = percentage * 640;
		sunPos.y = 485 + skyAlpha * 125f;
		Gdx.gl.glClearColor(0, 191 / 255f * skyAlpha, 255 / 255f * skyAlpha, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(sun, sunPos.x, sunPos.y);
		gameHandler.render(batch);
		font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), 0, 12);
		batch.end();
	}

	public Vector2 getRealScreenPos(float mouseX, float mouseY) {
		screenPos.set(mouseX, mouseY);
		return viewport.unproject(screenPos);
	}

	private float getSkyAlpha(float x) {
		return (float) (Math.sqrt((50f * 50f) - (float) Math.pow(x - 50, 2)) / 50f);
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

	public static ShaderProgram createDefaultShader() {
		String vertexShader = "#version 330 core\n"
				+ "in vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "in vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "in vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "uniform mat4 u_projTrans;\n" //
				+ "out vec4 v_color;\n" //
				+ "out vec2 v_texCoords;\n" //
				+ "\n" //
				+ "void main()\n" //
				+ "{\n" //
				+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
				+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "}\n";
		String fragmentShader = "#version 330 core\n"
				+ "#ifdef GL_ES\n" //
				+ "#define LOWP lowp\n" //
				+ "precision mediump float;\n" //
				+ "#else\n" //
				+ "#define LOWP \n" //
				+ "#endif\n" //
				+ "in LOWP vec4 v_color;\n" //
				+ "in vec2 v_texCoords;\n" //
				+ "out vec4 fragColor;\n" //
				+ "uniform sampler2D u_texture;\n" //
				+ "void main()\n"//
				+ "{\n" //
				+ "  fragColor = v_color * texture(u_texture, v_texCoords);\n" //
				+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false)
			throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}
}
