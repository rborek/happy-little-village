package com.happylittlevillage.gems;

import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.Assets;

public class GemBook extends GameObject {
	GameHandler gameHandler;
	boolean open = false;

	public GemBook(GameHandler gameHandler) {
		super(null, 1200, 410);
		this.gameHandler = gameHandler;
	}

	public boolean isOpen() {
		return open;
	}

	public void close() {
		open = false;
	}

	public void open() {
		open = true;
	}

	public void toggle(float x, float y) {
		Rectangle r = new Rectangle(position.x, position.y, width, height);
		if (r.contains(x, y)) {
			if (!open) {
				gameHandler.openBook();
				open = true;
				texture = Assets.getTexture("ui/small_book_open.png");

			} else {
				gameHandler.closeBook();
				open = false;
				texture = Assets.getTexture("ui/small_book.png");
			}

		}
	}

	@Override
	public void update(float delta) {

	}
}
