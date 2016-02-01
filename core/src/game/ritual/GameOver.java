package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.ritual.gems.GemBag;
import game.ritual.village.Village;

public class GameOver extends MessageBox {
	private String message;


	protected GameOver(GameHandler gameHandler) {
		super("", gameHandler);
		title = " GAME OVER ";
		message = "";
	}
	

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, title, position.x + 70, position.y + 250);
		font.draw(batch, message, position.x + 70, position.y + 275);
	}

}
