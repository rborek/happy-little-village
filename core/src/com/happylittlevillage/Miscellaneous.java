package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.happylittlevillage.menu.GameScreen;

public class Miscellaneous implements Screen, InputProcessor {
    protected HappyLittleVillage happyLittleVillage;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected SpriteBatch batch;
    protected Vector2 screenPos = new Vector2();
    protected Texture background;
    protected int lastResHeight;
    protected int lastResWidth;
    protected Vector2 realPos = new Vector2();
    protected Texture backButton;
    protected Rectangle backButtonPosition;


    public Miscellaneous(HappyLittleVillage happyLittleVillage) {
        this.happyLittleVillage = happyLittleVillage;
        camera = new OrthographicCamera();
        camera.position.set(GameScreen.WIDTH / 2f, GameScreen.HEIGHT / 2f, 0);
        camera.update();
        viewport = new StretchViewport(GameScreen.WIDTH, GameScreen.HEIGHT, camera);
        viewport.apply();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.input.setInputProcessor(this);
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
        realPos.set(getRealScreenPos(screenX, screenY));
        return true;
    }

    public Vector2 getRealScreenPos(float mouseX, float mouseY) {
        screenPos.set(mouseX, mouseY);
        return viewport.unproject(screenPos);
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
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
        batch.dispose();
    }
}