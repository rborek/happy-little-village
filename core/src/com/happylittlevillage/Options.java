package com.happylittlevillage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.happylittlevillage.HappyLittleVillage;

/**
 * Created by User on 18/02/16.
 */
public class Options implements Screen {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private HappyLittleVillage happyLittleVillage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture options = new Texture(Gdx.files.internal("textures/bg/Options.png"), true);
    private Texture backButton = new Texture(Gdx.files.internal("textures/bg/backButton.png"), true);

    private SpriteBatch batch;

    public Options(HappyLittleVillage happyLittleVillage){
        this.happyLittleVillage = happyLittleVillage;
        camera = new OrthographicCamera();
        camera.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
        camera.update();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        viewport.apply();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            System.out.print("Press M");
            happyLittleVillage.setGameScreen();
        }
        batch.begin();
        batch.draw(options,0,0);
        batch.draw(backButton,700,100);
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

