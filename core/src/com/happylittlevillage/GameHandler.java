package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemBook;
import com.happylittlevillage.input.GameGestureDetector;
import com.happylittlevillage.input.InputHandler;
import com.happylittlevillage.menu.MiningWindow;
import com.happylittlevillage.messages.*;
import com.happylittlevillage.rituals.Ritual;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.rituals.RitualBook;
import com.happylittlevillage.rituals.RitualTree;
import com.happylittlevillage.village.Village;
import com.happylittlevillage.village.Villager;

import java.util.ArrayList;

public class GameHandler {
	private Village village;
	private RitualAltar ritualAltar;
	private Texture background = Assets.getTexture("bg/background.png");
	private GemBag gemBag;
	private InputHandler inputHandler;
	private GameGestureDetector gameGestureDetector;
	private Gem gem;
	private Texture scroll = Assets.getTexture("ui/scroll.png");
	private boolean paused;
	private GemBook miniBook = new GemBook(this);
	private boolean bookOpen;
	private RitualTree ritualTree = new RitualTree(this, 30, 15);
	private RitualBook ritualBook;
	private WinMessage winMessage;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private boolean DEBUG = false;
	// all menu items is put here
	private MessageBox messageBox;
	private LoseMessage loseMessage;
	private boolean win = false;
	private boolean intro = true;
	private boolean lose = false;
	private boolean isTutorial;
	private boolean finishTutorial = false;
	private static ArrayList<Vector2> arrow = new ArrayList<Vector2>();
	private TutorialMessage tutorialMessage;
	private Rectangle optionWheelPosition = new Rectangle(0, 600, 64, 64);
	private Json saveInfo = new Json();
	private int messageScreen = 0;
	private MiningWindow miningWindow;

	public GameHandler(GameGestureDetector gameGestureDetector, InputHandler inputHandler, boolean isTutorial, HappyLittleVillage happyLittleVillage) {
		this.isTutorial = isTutorial;
		this.inputHandler = inputHandler;
		this.gameGestureDetector = gameGestureDetector;
		init(isTutorial, happyLittleVillage);
	}

	public void init(boolean isTutorial, HappyLittleVillage happyLittleVillage) {
		gemBag = new GemBag(1280 - 420 - 36 - 32, 30 + 35 - 12);
		miningWindow = new MiningWindow(gemBag, null, 455, 450, 50, 50);
		if (isTutorial) {
			village = new Village(gemBag, 200, 100, 5);
			ritualAltar = new RitualAltar(gemBag, 1280 - 400 - 48 - 30, 720 - 400 - 40 - 12, village, ritualTree);
			tutorialMessage = new TutorialMessage(this, ritualAltar, miniBook);
			arrow.add(new Vector2(476, 579));
		} else {
			village = new Village(gemBag, 300, 200, 5);
			ritualAltar = new RitualAltar(gemBag, 1280 - 440 - 48 - 30, 720 - 400 - 40 - 12, village, ritualTree);
		}
		ritualBook = new RitualBook(ritualTree, 600, 0, village);
		messageBox = new Introduction(this, isTutorial);
		loseMessage = new LoseMessage(this, happyLittleVillage);
		winMessage = new WinMessage(this, happyLittleVillage);
		Ritual.setVillage(village);
		Gdx.input.setInputProcessor(gameGestureDetector);
		pause();
	}


	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		paused = true;
	}

	public void finishIntro() {
		intro = false;
		paused = false;
		messageBox = new WeekSummary(village, gemBag, this);
	}

	public void unpauseInGame() {
		//TODO 2 options: the weekly ritual can be in the RitualBook and the Altar. ( we choose this for now)
		// so we must call add weeklyRitual recipe to the RitualBook right when we show ritual tree
		//TODO the weekly ritual can be excluded from the RitualBook and only functions in the Altar
		// we dont update weeklyRitual to the chosenRituals anymore. Just dont update anything about it cuz the altar can call it from village

		if (messageScreen == 0) {
			messageScreen++; // move to the ritual Tree
			// this add the weekly Ritual To the chosen ritual
			if (!ritualTree.getChosenRituals().contains(village.getWeeklyRitual())) {
				ritualTree.addWeeklyRitualToChosenRitual(village.getWeeklyRitual());
			}
		} else if (messageScreen == 1) {
			messageScreen = 0;
			paused = false;
			ritualBook.setWeeklyChosenRitual(); //RitualBook will update new rituals from the ritual Tree after a week
			ritualAltar.setWeeklyChosenRitual();//Ritual Altar will know which new rituals are available
		}
	}

	// game logic goes here
	public void update(float delta) {
		// lose
		if (village.getSize() <= 0 || village.getDaysLeft() < 0) {
			lose = true;
			loseMessage.setCondition(0);
			pause();
			messageBox = loseMessage;
			return;
		}
		//win
		else if (village.getSize() >= 50) {
			win = true;
			winMessage.setCondition(1);
			pause();
			messageBox = winMessage;
			return;
		}

		if (!paused) { // not pause
			if (village.isNextDay()) { // end of day
				if (village.getWeeklyRitual().getRecipe() == null) { // if it is first day
					village.generateNewWeeklyRitual();
					village.setBlackGem(0);
				} else {
					if (((WeekSummary) messageBox).checkRitual()) {
						village.generateNewWeeklyRitual();
						ritualTree.addBlackGem(village.getBlackGem()); // add winning black gems to ritual tree
						((WeekSummary) messageBox).setNumOfBlackGem(village.getBlackGem());
					}
				}
				((WeekSummary) messageBox).stateRitual();
				((WeekSummary) messageBox).setNumOfBlackGem(village.getBlackGem());

				pause();
			}
			village.update(delta);
			ritualAltar.update(delta);
			ritualBook.update(delta);
			miningWindow.update(delta);
		} else { // when pause
			if (messageScreen == 1) {
				ritualTree.update(delta);
			}
		}
		if (isTutorial) {
			//arrow for screen 0 and 1
			if (tutorialMessage.getTutorialScreen() <= 1) {
				// make the arrow point to a villager
				tutorialMessage.setAngle(village.getPositionOfARandomVillager(), arrow.get(0));
			}
			tutorialMessage.update(delta);
		}
	}

	public void render(Batch batch) {
		if (!paused) {
			if (isTutorial) {
				tutorialMessage.render(batch);
			}
			batch.draw(background, 0, 0);
			village.render(batch);
			batch.draw(scroll, 1280 - 680, -12, scroll.getWidth() * 1.3f, scroll.getHeight());
			ritualAltar.render(batch);
			gemBag.render(batch);
			ritualBook.render(batch);
			if (ritualTree.isPickAxeUnlocked()) {
				miningWindow.render(batch);
			}
			inputHandler.renderSelectedGem(batch);
			inputHandler.renderSelectedRitual(batch);
//			Villager.renderPathMakers(batch);
			batch.end();
			if (DEBUG) {
				shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
				Villager.renderPath(shapeRenderer);
				Villager.renderLines(shapeRenderer);
				shapeRenderer.end();
			}
			batch.begin();
		} else {
			if (messageScreen == 0) {
				messageBox.render(batch);
			} else if (messageScreen == 1) {
				ritualTree.render(batch);
			}
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

	public TutorialMessage getTutorialMessage() {
		return tutorialMessage;
	}

	public boolean isTutorial() {
		return isTutorial;
	}

	public LoseMessage getLoseMessage() {
		return loseMessage;
	}

	public Rectangle getOptionWheelPosition() {
		return optionWheelPosition;
	}

	public int getMessageScreen() {
		return messageScreen;
	}

	public RitualTree getRitualTree() {
		return ritualTree;
	}

	public MiningWindow getMiningWindow() {
		return miningWindow;
	}

	public boolean isWin() {
		return win;
	}

	public boolean isLose() {
		return lose;
	}
}
