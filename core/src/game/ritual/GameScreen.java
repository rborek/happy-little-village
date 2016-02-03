package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.ritual.input.InputHandler;

public class GameScreen implements Screen {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private Texture sun = new Texture(Gdx.files.internal("scroll/sun.png"), true);
	private Vector2 sunPos = new Vector2();
	private RitualGame game;
	private int dayTime;
	private GameHandler gameHandler;
	private InputHandler inputHandler;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	public GameScreen(RitualGame game) {
		this.game = game;
		batch = new SpriteBatch(1000, createDefaultShader());
		camera = new OrthographicCamera();
		camera.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
		camera.update();
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		viewport.apply();
		batch.setProjectionMatrix(camera.combined);
		inputHandler = new InputHandler(this);
		gameHandler = new GameHandler(inputHandler);
		inputHandler.linkTo(gameHandler);
		Gdx.input.setInputProcessor(inputHandler);
		dayTime = gameHandler.getVillage().getMaxHours();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		gameHandler.update(delta);
		float time = dayTime - gameHandler.getVillage().getHoursLeft();
		float percentage = (time / dayTime);
		float skyAlpha = getSkyAlpha(percentage * 100) + 0.2f;
		sunPos.x = percentage * 640;
		sunPos.y = 485 + skyAlpha * 125f;
		Gdx.gl30.glClearColor(0, 191 / 255f * skyAlpha, 255 / 255f * skyAlpha, 1);
		Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(sun, sunPos.x, sunPos.y);
		gameHandler.render(batch);
		batch.end();
	}

	public Vector2 getRealScreenPos(float mouseX, float mouseY) {
		Vector2 pos = new Vector2(mouseX, mouseY);
		return viewport.unproject(pos);
	}

	private float getSkyAlpha(float x) {
		return (float) (Math.sqrt((50f * 50f) - (float) Math.pow(x - 50, 2)) / 50f);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
