package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by User on 18/02/16.
 */
public class Menu implements Screen, InputProcessor {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private HappyLittleVillage happyLittleVillage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Vector2 screenPos = new Vector2();
    private Texture menu = new Texture(Gdx.files.internal("textures/bg/menu.png"), true);
    private Texture optionsButton = new Texture(Gdx.files.internal("textures/bg/optionsButton.png"), true);
    private Texture startButton = new Texture(Gdx.files.internal("textures/bg/startButton.png"), true);
    private Texture creditsButton = new Texture(Gdx.files.internal("textures/bg/creditsButton.png"), true);
    private Texture[] buttons = {optionsButton, startButton, creditsButton};
    private Rectangle startButtonPosition = new Rectangle(800, 500, startButton.getWidth(), startButton.getHeight());
    private Rectangle optionsButtonPosition = new Rectangle(800, 300, optionsButton.getWidth(), optionsButton.getHeight());
    private Rectangle creditsButtonPosition = new Rectangle(800, 100, creditsButton.getWidth(), creditsButton.getHeight());
    //private Rectangle [] buttonPositions = {startButtonPosition,optionsButtonPosition,creditsButtonPosition};

    public Menu(HappyLittleVillage happyLittleVillage) {
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 realPos =getRealScreenPos(screenX, screenY);
        System.out.println("Mouse click at " + realPos);
//        if (creditsButtonPosition.contains(realPos)) {
//            happyLittleVillage.setCredits();
//        } else if (startButtonPosition.contains(realPos)) {
//            happyLittleVillage.setGameScreen();
//        } else if (optionsButtonPosition.contains(realPos)) {
//            happyLittleVillage.setOptions();
//        }
        return true;
    }

    public Vector2 getRealScreenPos(float mouseX, float mouseY) {
        screenPos.set(mouseX, mouseY);
        return viewport.unproject(screenPos);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.C)){
            System.out.print("Press C");
            happyLittleVillage.setCredits();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.G)){
            System.out.print("Press G");
            happyLittleVillage.setGameScreen();
        }
        else  if(Gdx.input.isKeyPressed(Input.Keys.O)){
            System.out.print("Press O");
            happyLittleVillage.setOptions();
        }
        batch.begin();
        batch.draw(menu, 0, 0);
        batch.draw(startButton, 800, 500);
        batch.draw(optionsButton, 800, 300);
        batch.draw(creditsButton, 800, 100);
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
