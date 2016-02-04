package game.ritual.gems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.Assets;
import game.ritual.GameHandler;
import game.ritual.GameObject;

public class GemBook extends GameObject {
	GameHandler gameHandler;
	boolean opened = false;

	public GemBook(GameHandler gameHandler) {
		super(Assets.getTexture("ui/small_book.png"), 675, 50);
		this.gameHandler = gameHandler;
	}

	public void toggle(float x, float y) {
		Rectangle r = new Rectangle(position.x, position.y, width, height);
		if (r.contains(x, y)) {
			if (!opened) {
				gameHandler.openBook();
				opened = true;
				texture = Assets.getTexture("ui/small_book_open.png");
			} else {
				gameHandler.closeBook();
				opened = false;
				texture = Assets.getTexture("ui/small_book.png");
			}

		}
	}

	@Override
	public void update(float delta) {

	}
}
