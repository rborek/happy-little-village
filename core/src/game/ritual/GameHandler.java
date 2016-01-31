package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
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
	private boolean paused = true;
	private MessageBox messageBox;
	private MessageBox introduction;
	private MessageBox messagePointer;
	private boolean intro = true;

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
		inputHandler = new InputHandler(ritualAltar, gemBag);
		Ritual.setVillage(village);
		Gdx.input.setInputProcessor(inputHandler);
		messageBox = new MessageBox(new Texture("scroll/Summary.png"), 20, 300);
		introduction = new MessageBox(new Texture("scroll/Summary.png"), 20, 300,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n Fusce ullamcorper rutrum purus, vel auctor sapien tincidunt vel.\n"
						+ " Proin felis massa, venenatis ut imperdiet in, scelerisque a ex. Donec eget mauris enim.\n"
						+ " In nec est vel risus ultricies placerat.\n"
						+ " Cras vulputate, enim at semper volutpat, libero sem euismod justo, gravida lobortis nulla nunc id nunc.\n"
						+ " Suspendisse lacinia felis odio, vel mollis velit sodales id. Maecenas id erat rhoncus, vehicula augue sit amet, suscipit nulla.");
	}

	// game logic goes here
	public void update(float delta) {
		if (!paused) {
			if (village.isNextWeek()) {
				paused = true;
			}
			village.update(delta);
		} else {
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				paused = false;
				intro = false;
			}
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
		if (paused) {
			if (intro) {
				introduction.render(batch);
			} else {
				messageBox.render(batch);
			}
		}

	}

}
