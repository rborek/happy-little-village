package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.gems.GemSlots;
import game.ritual.input.InputHandler;
import game.ritual.village.Village;
import game.ritual.village.Villager;
import game.ritual.village.VillagerRole;

public class GameHandler {
	private Village village;
	private GemSlots gemSlots;
	private GemBag gemBag;
	private InputHandler inputHandler;
	private Gem gem;
	private boolean paused = false;

	public GameHandler() {
		init();
	}

	public void init() {
		gemSlots = new GemSlots(640, 600, 2, 2);
		gemSlots.add(new Gem(GemColour.RED));
        gemSlots.add(new Gem(GemColour.BLUE));
        gemSlots.add(new Gem(GemColour.YELLOW));
        gemSlots.add(new Gem(GemColour.GREEN));
		gemBag = new GemBag(new Texture("gems/slot.png"), 780, 0);
		village = new Village();
		village.addVillager(new Villager(VillagerRole.CITIZEN, village));
		village.addVillager(new Villager(VillagerRole.CITIZEN, village));
		village.addVillager(new Villager(VillagerRole.CITIZEN, village));
		village.addVillager(new Villager(VillagerRole.CITIZEN, village));
		village.addVillager(new Villager(VillagerRole.CITIZEN, village));
		village.addVillager(new Villager(VillagerRole.CITIZEN, village));
		village.addVillager(new Villager(VillagerRole.CITIZEN, village));
		inputHandler = new InputHandler(gemSlots, gemBag);
		Gdx.input.setInputProcessor(inputHandler);
	}

	// game logic goes here
	public void update(float delta) {
		if (!paused) {
			village.update(delta);
		}
	}

	// rendering goes here
	public void render(Batch batch) {
		village.render(batch);
		gemSlots.render(batch);

	}

}

