package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.messages.*;
import game.ritual.rituals.RitualAltar;
import game.ritual.input.InputHandler;
import game.ritual.rituals.Ritual;
import game.ritual.rituals.RitualBook;
import game.ritual.village.Village;
import game.ritual.village.VillagerRole;

public class GameHandler {
	private Village village;
	private RitualAltar ritualAltar;
	private Texture background = new Texture("scroll/background.png");
	private GemBag gemBag;
	private InputHandler inputHandler;
	private Gem gem;
	private Texture scroll = new Texture("scroll/scroll.png");
	private boolean paused;
	private BookIcon miniBook = new BookIcon(this);
	private MessageBox messageBox;
	private GemSummary gemSummary;
	private boolean bookOpen;
	private boolean intro = true;
	private GodMessage godMessage;
	private boolean GameOver = false;
	private RitualBook ritualBook = new RitualBook(70, 160);
	private GameOver gameOverMessage;
	private boolean win = false;
	private WinMessage winMessage;

	public GameHandler() {
		init();
	}


	public void init() {
		gemBag = new GemBag(1280 - 420 - 36 - 32, 30 + 35 - 12);
		ritualAltar = new RitualAltar(gemBag, 1280 - 400 - 48 - 30, 720 - 400 - 40 - 12, 2, 2);
		village = new Village();
		for (int i = 0; i < 7; i++) {
			village.addVillager(VillagerRole.CITIZEN);
		}
		gemSummary = new GemSummary(this);
		messageBox = new MessageBox("  Welcome to your happy little village!\n Efficiently maintain your villagers'\n happiness"
				+ " by giving them food and\n water! Combine gems from your bag \n to gain or sacrifice different \n resources and villagers! You can\n combine"
				+ " a maximum of 4 gems\n of any kind! ", this);
		inputHandler = new InputHandler(ritualAltar, gemBag, messageBox, ritualBook, miniBook);
		gameOverMessage = new GameOver(this);
		winMessage = new WinMessage(this);
		Ritual.setVillage(village);
		ritualAltar.gainRitual(village.getWeeklyRitual());
		Gdx.input.setInputProcessor(inputHandler);
		pause();

	}

	public void pause() {
		paused = true;
		inputHandler.disable();
	}

	public void unpause() {
		if (messageBox instanceof WeekSummary) {
			messageBox = new GemSummary(gemBag, village, this);
			((GemSummary) messageBox).gemMined();

		} else if (messageBox instanceof GemSummary) {
			messageBox = new GodMessage(gemBag, village, this);
			if (((GodMessage) messageBox).checkRitual()) {
				ritualAltar.removeRitual(village.getWeeklyRitual());
				village.newMonthlyRitual();
				ritualAltar.gainRitual(village.getWeeklyRitual());
			}
			((GodMessage) messageBox).stateRitual();
			System.out.println("printed");
		} else if (messageBox instanceof MessageBox) {
			messageBox = new WeekSummary(village, this);
			paused = false;
			inputHandler.enable();

		}

	}

	// game logic goes here
	public void update(float delta) {
		if (win == false) {
			if (village.getSize() <= 0) {
				gameOverMessage.setCondition(0);
				GameOver = true;
			} else if (village.getFood() <= 0) {
				gameOverMessage.setCondition(1);
				GameOver = true;
			} else if (village.getWater() <= 0) {
				gameOverMessage.setCondition(2);
				GameOver = true;
			} else if ((!village.getWeeklyRitual().isComplete() && village.getWeeksleft() < 0)) {
				gameOverMessage.setCondition(3);
				GameOver = true;
			}
		}
		if (GameOver == false) {
			if (village.getSize() >= 15) {
				winMessage.setCondition(1);
				win = true;
			} else if (village.getFood() >= 150) {
				winMessage.setCondition(0);
				win = true;
			} else if (village.getWater() >= 150) {
				winMessage.setCondition(2);
				win = true;
			}
		}

		if (!paused && GameOver == false && win == false) {
			if (village.isNextDay()) {
				pause();
				village.gatheredFood();
				village.gatheredWater();
			}
			village.update(delta);
			ritualAltar.update(delta);
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


	// rendering goes here
	public void render(Batch batch) {
		batch.draw(background, 0, 0);
		village.render(batch);
		batch.draw(scroll, 1280 - 550, -12);
		ritualAltar.render(batch);
		gemBag.render(batch);
		inputHandler.renderSelectedGem(batch);
		miniBook.render(batch);
		if (!GameOver && !win) {
			if (bookOpen) {
				ritualBook.render(batch);
			}
			if (paused) {
				messageBox.render(batch);
			}
		} else if (GameOver && !win) {
			gameOverMessage.render(batch);
		} else if (!GameOver && win){
			winMessage.render(batch);
		}
	}

}
