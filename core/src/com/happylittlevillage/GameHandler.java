package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemBook;
import com.happylittlevillage.input.InputHandler;
import com.happylittlevillage.messages.*;
import com.happylittlevillage.rituals.Ritual;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.rituals.RitualBook;
import com.happylittlevillage.village.Village;
import com.happylittlevillage.village.Villager;
import com.happylittlevillage.village.VillagerRole;

public class GameHandler {
	private Village village;
	private RitualAltar ritualAltar;
	private Texture background = Assets.getTexture("bg/background.png");
	private GemBag gemBag;
	private InputHandler inputHandler;
	private Gem gem;
	private Texture scroll = Assets.getTexture("ui/scroll.png");
	private boolean paused;
	private GemBook miniBook = new GemBook(this);
	private boolean bookOpen;
	private RitualBook ritualBook = new RitualBook(70, 160);
	private WinMessage winMessage;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private boolean DEBUG = false;
	// all menuitems is put here
	private MessageBox messageBox;
	private GemSummary gemSummary;
	private GodMessage godMessage;
	private GameOver gameOverMessage;
	private boolean win = false;
	private boolean intro = true;
	private boolean gameOver = false;


	public RitualAltar getRitualAltar() {
		return ritualAltar;
	}

	public GemBag getGemBag() {
		return gemBag;
	}

	public RitualBook getRitualBook() {
		return ritualBook;
	}

	public GemBook getMiniBook() {
		return miniBook;
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public GameHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
		init();
	}

	public void init() {
		gemBag = new GemBag(1280 - 420 - 36 - 32, 30 + 35 - 12);
		ritualAltar = new RitualAltar(gemBag, 1280 - 400 - 48 - 30, 720 - 400 - 40 - 12);
		village = new Village(gemBag);
		for (int i = 0; i < 100; i++) {
			village.addVillager(VillagerRole.CITIZEN);
		}
		messageBox = new Introduction(this);
		gameOverMessage = new GameOver(this);
		winMessage = new WinMessage(this);
		Ritual.setVillage(village);
		ritualAltar.gainRitual(village.getWeeklyRitual());
		Gdx.input.setInputProcessor(inputHandler);
		pause();

	}

	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		paused = true;
		if (miniBook.isOpen()) {
			miniBook.close();
		}
	}

	public void unpause() {
		if (messageBox instanceof WeekSummary) {
			messageBox = new GemSummary(gemBag, village, this);
		} else if (messageBox instanceof GemSummary) {
			messageBox = new GodMessage(gemBag, village, this);
			if (((GodMessage) messageBox).checkRitual()) { 
				//TODO change this messy code
				ritualAltar.removeRitual(village.getWeeklyRitual());
				village.newWeeklyRitual();
				ritualAltar.gainRitual(village.getWeeklyRitual());
			}
			((GodMessage) messageBox).stateRitual();
		} else {
			messageBox = new WeekSummary(village, this);
			paused = false;
		}
	}

	// game logic goes here
	public void update(float delta) {
		if (!win) {
			if (village.getSize() <= 0) {
				gameOverMessage.setCondition(0);
				gameOver = true;
			} else if (village.getFood() <= 0) {
				gameOverMessage.setCondition(1);
				gameOver = true;
			} else if (village.getWater() <= 0) {
				gameOverMessage.setCondition(2);
				gameOver = true;
			} else if ((!village.getWeeklyRitual().isComplete() && village.getDaysLeft() < 0)) {
				gameOverMessage.setCondition(3);
				gameOver = true;
			}
			if (gameOver) {
				messageBox = gameOverMessage;
				pause();
			}
		}
		if (!gameOver) {
			if (village.getSize() < -1) {
				winMessage.setCondition(1);
				win = true;
			}
		}

		if (!paused && gameOver == false && win == false) {
			if (village.isNextDay()) {
				pause();
				village.gatheredFood();
				village.gatheredWater();
			}
			village.update(delta);
			ritualAltar.update(delta);
		}

	}

	// rendering goes here
	public void render(Batch batch) {
		batch.draw(background, 0, 0);
		village.render(batch);
		batch.draw(scroll, 1280 - 550, -12);
		ritualAltar.render(batch);
		gemBag.render(batch);
		inputHandler.renderSelectedGem(batch);
		miniBook.render(batch);
		if (!gameOver && !win) {
			if (paused) {
				messageBox.render(batch);
			} else if (bookOpen) {
				ritualBook.render(batch);
			} else {
				if (DEBUG) {
					shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
					Villager.renderLines(shapeRenderer);
					shapeRenderer.end();
				}
			}
		} else {
			messageBox.render(batch);
		}
	}


	public Village getVillage() {
		return village;
	}

	public void openBook() {
		bookOpen = true;
	}

	public void closeBook() {
		bookOpen = false;
	}


}
