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
	private GemBag gemBag;
	private InputHandler inputHandler;
	private Gem gem;
	private Texture scroll = new Texture("scroll/scroll.png");
	private boolean paused = false;
	private MessageBox message;
	public GameHandler() {
		init();
	}

	public void init() {
		gemBag = new GemBag(1280-420-36-32, 30+35-12);
		ritualAltar = new RitualAltar(gemBag, 1280-400-48-30, 720-400-40-12, 2, 2);
		ritualAltar.add(new Gem(GemColour.RED));
		ritualAltar.add(new Gem(GemColour.BLUE));
		ritualAltar.add(new Gem(GemColour.YELLOW));
		ritualAltar.add(new Gem(GemColour.GREEN));
		village = new Village();
		for (int i = 0; i < 7; i++) {
			village.addVillager(VillagerRole.CITIZEN);
		}
		inputHandler = new InputHandler(ritualAltar, gemBag);
		Ritual.setVillage(village);
		Gdx.input.setInputProcessor(inputHandler);
		message = new MessageBox(new Texture("scroll/Summary.png"),20,300);
	}

	// game logic goes here
	public void update(float delta) {
		if (!paused) {
			village.update(delta);
			if(village.weekPass()){
				paused =true;
			}
		} else {
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				paused = false;
			}
		}

	}

	// rendering goes here
	public void render(Batch batch) {
		village.render(batch);
		batch.draw(scroll, 1280 - 550, -12);
		ritualAltar.render(batch);
		gemBag.render(batch);
		inputHandler.renderSelectedGem(batch);
		if(paused){
			message.render(batch);
		}
	}

}

