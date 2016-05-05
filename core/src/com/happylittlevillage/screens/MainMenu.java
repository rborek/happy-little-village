package com.happylittlevillage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.rituals.Ritual;

public class MainMenu extends Miscellaneous {
	private GameObject optionsButton;
	private GameObject startButton;
	private GameObject creditsButton;
	private GameObject tutorialButton;
	private GameObject loadButton;
	private GameObject exitButton;
	private Rectangle startButtonPosition;
	//    private Rectangle optionsButtonPosition;
//    private Rectangle creditsButtonPosition;
	private Rectangle tutorialButtonPosition;
	private Rectangle loadButtonPosition;
	private Rectangle exitButtonPosition;
	private static int buttonX = 862;
	private static int buttonY = 68;
	private GameObject loadingScreen;
	private GameObject[] dots = new GameObject[3];
	private boolean started = false;
	private boolean toStart = false;
	private boolean startedLoading = false;
	private boolean loadDisplayed = false;
	private float timer = 0;

	public MainMenu(HappyLittleVillage happyLittleVillage) {
		super(happyLittleVillage);
		startButton = new GameObject(Assets.getTexture("menu/start_button.png"), buttonX, buttonY + 450, 360, 150);
//		tutorialButton = new GameObject(Assets.getTexture("menu/tutorial_button.png"), buttonX, buttonY + 325, 360, 150);
//		loadButton = new GameObject(Assets.getTexture("menu/load_button.png"), buttonX, buttonY + 200, 360, 150);
//        optionsButton = new GameObject(Assets.getTexture("menu/options_button.png"), buttonX, buttonY + 75);
//        creditsButton = new GameObject(Assets.getTexture("menu/credits_button.png"), buttonX, buttonY);
		exitButton = new GameObject(Assets.getTexture("menu/exit_button.png"), buttonX, buttonY + 10, 360, 150);
		exitButtonPosition = new Rectangle(buttonX, buttonY + 10, exitButton.getWidth(), exitButton.getHeight());
//        creditsButtonPosition = new Rectangle(buttonX, buttonY  , creditsButton.getWidth(), creditsButton.getHeight());
//        optionsButtonPosition = new Rectangle(buttonX, buttonY + 75, optionsButton.getWidth(), optionsButton.getHeight());
//		loadButtonPosition = new Rectangle(buttonX, buttonY + 200, loadButton.getWidth(), loadButton.getHeight());
//		tutorialButtonPosition = new Rectangle(buttonX, buttonY + 325, tutorialButton.getWidth(), tutorialButton.getHeight());
		startButtonPosition = new Rectangle(buttonX, buttonY + 450, startButton.getWidth(), startButton.getHeight());
		background = Assets.getTexture("menu/menu.png");
		loadingScreen = new GameObject(Assets.getTexture("menu/loading_screen.png"), 0, 0);
		for (int i = 0; i < 3; i++) {
			dots[i] = new GameObject(Assets.getTexture("menu/loading_dot.png"), 80 * i, 0);
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!started) {
			super.touchDown(screenX, screenY, pointer, button); // set the realPos
			if (startButtonPosition.contains(realPos)) {
				toStart = true;
				return true;
			} else if (exitButtonPosition.contains(realPos)) {
				happyLittleVillage.exit();
				return true;
			}
		}
		return false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (!toStart) {
			batch.draw(background, 0, 0);
			startButton.render(batch);
			exitButton.render(batch);
		} else {
			timer += delta;
			int frame = (int) (timer * 2 % 4);
			loadingScreen.render(batch);
			for (int i = 0; i <= frame; i++) {
				if (i > 0) {
					dots[i - 1].render(batch);
				}
			}
			loadDisplayed = true;
		}
		batch.end();
		if (started && !startedLoading) {
			Assets.load();
			startedLoading = true;
		} else if (started && Assets.update()) {
			Ritual.load();
			happyLittleVillage.switchToGameScreen(false);
		}
		started = loadDisplayed;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}


}
