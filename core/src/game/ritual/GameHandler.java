package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.rituals.RitualAltar;
import game.ritual.input.InputHandler;
import game.ritual.rituals.Ritual;
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
	private MessageBox messageBox;
	private GemSummary gemSummary;
	private boolean intro = true;
	private GodMessage godMessage;

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
		messageBox = new MessageBox("Welcome to your happy little village!\n Your task is to maintain your villagers' happiness\n"
				+ " by providing food and water! Make sure to\n be efficient with your gems or you will be punished! \nCombine gems inside your bag on the bottom right into "
				+ "the\n altar to gain or sacrifice various resources! Click the button to combine \n"
				+ "a maximum of 4 gems of any kind! ", this);
		inputHandler = new InputHandler(ritualAltar, gemBag, messageBox);
		Ritual.setVillage(village);
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
			if(((GodMessage) messageBox).checkRitual()){
				ritualAltar.removeRitual(village.getMonthlyRitual());
				village.newMonthlyRitual();
				((GodMessage)messageBox).stateRitual();
			}
			System.out.println("printed");

		} else if (messageBox instanceof MessageBox) {
			messageBox = new WeekSummary(village, this);
			paused = false;
			inputHandler.enable();

		}

	}

	// game logic goes here
	public void update(float delta) {
		if (!paused) {
			if (village.isNextWeek()) {
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

	// rendering goes here
	public void render(Batch batch) {
		batch.draw(background, 0, 0);
		village.render(batch);
		batch.draw(scroll, 1280 - 550, -12);
		ritualAltar.render(batch);
		gemBag.render(batch);
		inputHandler.renderSelectedGem(batch);
		if (paused) {
			messageBox.render(batch);
		}

	}

}
