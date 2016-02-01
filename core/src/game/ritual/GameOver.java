package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.ritual.gems.GemBag;
import game.ritual.village.Village;

public class GameOver extends MessageBox {
	private String message;
	private String[] reason;


	protected GameOver(GameHandler gameHandler) {
		super("", gameHandler);
		title = " GAME OVER ";
		message = "";
		reason = new String[0];
		reason[0] = " No more villager left";
		reason[1] = " Hunger: food less than 50";
		reason[2] = " Drought: water less than 50";
		reason[3] = " Did not finish ritual in time";
	}
	public void setReason( int a){
		message = reason[a];
	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, title, position.x + 70, position.y + 250);
		font.draw(batch, message, position.x + 70, position.y + 275);
	}

}
