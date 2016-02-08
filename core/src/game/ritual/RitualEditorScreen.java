package game.ritual;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.IOException;


public class RitualEditorScreen implements Screen{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    //private Texture sun = new Texture(Gdx.files.internal("textures/bg/sun.png"), true);
    //private Vector2 sunPos = new Vector2();
    //private Vector2 screenPos = new Vector2();
    private RitualGame game;
    private int dayTime;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font = new BitmapFont();
    private BufferedReader readritual;

    public RitualEditorScreen(RitualGame game) {
        this.game = game;
        Assets.load();
        batch = new SpriteBatch(1000, createDefaultShader());
        camera = new OrthographicCamera();
        camera.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
        camera.update();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        readritual =Assets.getRitual("rituals/0.txt");

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (delta > 0.1f) {
            delta = 0.01667f;
        }
        /*gameHandler.update(delta);
        float time = dayTime - gameHandler.getVillage().getHoursLeft();
        float percentage = (time / dayTime);
        float skyAlpha = getSkyAlpha(percentage * 100) + 0.2f;
        sunPos.x = percentage * 640;
        sunPos.y = 485 + skyAlpha * 125f;
        Gdx.gl30.glClearColor(0, 191 / 255f * skyAlpha, 255 / 255f * skyAlpha, 1);
        Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);*/
        batch.begin();
        //batch.draw(sun, sunPos.x, sunPos.y);
        //gameHandler.render(batch);
        //font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), 0, 12);

        batch.end();
    }

    private String storeRitual(BufferedReader b){
        String a = "";
        try {
            a.concat(b.readLine());
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file with path");
        }
        System.out.print(a);
        return a;

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
        if (!shader.isCompiled())
            throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        return shader;
    }
}



