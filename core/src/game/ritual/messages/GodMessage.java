package game.ritual.messages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.GameHandler;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;

public class GodMessage extends MessageBox {
	private GemBag gemBag;
	private Village village;
	private int timesPerformed;
	private int timesToDo;
	private Texture[] gems;


	public GodMessage(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = " God Message";
	}

	public boolean checkRitual() {
		if (village.getWeeklyRitual().isComplete()) {
			text = " You have completed monthly ritual of this month.\n";
			village.setDaysLeft(4);
			return true;
		} else {
			text = " Complete the monthly ritual \n before the time runs out!";
			timesToDo = village.getWeeklyRitual().getTimesToDo();
			timesPerformed = village.getWeeklyRitual().getTimesPerformed();
			return false;
		}
	}

	public void stateRitual() {
		text += " \n\n              is the ritual";
		GemColour[] colours = village.getWeeklyRitual().getColours();
		Texture[] textures = Gem.getArrayOfMiniTextures();
		gems = new Texture[colours.length];
		for (int i = 0; i < gems.length; i++) {
			gems[i] = textures[colours[i].ordinal()];
		}

	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, text, position.x + 70, position.y + 275);
		if (gems[0] != null) {
			batch.draw(gems[0], 200, 300);
		}
		if (gems[1] != null) {
			batch.draw(gems[1], 240, 300);
		}
		if (gems[2] != null) {
			batch.draw(gems[2], 200, 260);
		}
		if (gems[3] != null) {
			batch.draw(gems[3], 240, 260);
		}
		int howMany = village.getWeeklyRitual().getTimesToDo() - village.getWeeklyRitual().getTimesPerformed();
		font.draw(batch, "Number of times to complete: " + howMany, position.x + 70, position.y + 335);
		font.draw(batch, "Weeks left to complete monthly ritual: " + timesToDo, position.x + 70, position.y + 390);

	}

}
