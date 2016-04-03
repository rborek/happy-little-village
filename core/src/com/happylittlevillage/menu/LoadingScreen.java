package com.happylittlevillage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.HappyLittleVillage;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen implements Screen, InputProcessor {
    protected HappyLittleVillage happyLittleVillage;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected SpriteBatch batch;
    protected Vector2 screenPos = new Vector2();
    protected int lastResHeight;
    protected int lastResWidth;
    private boolean isTutorial;
    private GameObject screen;

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
        screen = new GameObject(Assets.getTexture("menu/loading_screen.png"),0,0);
        // if we ever wanna interact with the loading screen we have to set the input processor to this class, implement InputProcessor
        //and methods like touchDragged touch down
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        // this load the loading Screen texture and wait until it is finished loading
//        Assets.loadLoadingScreenTextures();
        Assets.load();


    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        screen.render(batch);
        batch.end();
        if (Assets.update()) {
            if(Gdx.input.isTouched()){
                happyLittleVillage.setGameScreen(isTutorial);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        //Took this from GameScreen
        System.out.println("calling resize");
        if (width != lastResWidth || height != lastResHeight) {
            System.out.println("resizing");
            viewport.update(width, height);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
