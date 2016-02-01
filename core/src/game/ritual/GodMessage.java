package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.ritual.gems.GemBag;
import game.ritual.village.Village;

public class GodMessage extends MessageBox {
	private GemBag gemBag;
	private Village village;
	private int timesPerformed;
	private int timesToDo;
	private String message;

	protected GodMessage(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		// TODO Auto-generated constructor stub
	}

	protected GodMessage(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = " God Message";
	}

	public boolean checkRitual() {
		if (village.getMonthlyRitual().isComplete()) {
			message = " You have completed monthly ritual of this month";
			return true;
		} else {
			message = " Complete monthly ritual before the time runs out";
			timesToDo = village.getMonthlyRitual().getTimesToDo();
			timesPerformed = village.getMonthlyRitual().getTimesPerformed();
			return false;
		}
	}
	public void stateRitual() {
		message += " \n                    is the ritual";
		village.getMonthlyRitual().getColours();

	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch,message, position.x + 70, position.y + 275);
		font.draw(batch, "weeks left to complete monthly ritual:" + timesToDo, position.x + 70, position.y + 275);

	}

}
