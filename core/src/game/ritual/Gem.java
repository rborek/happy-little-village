package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Gem extends GameObject {
	GemColour colour;
	private static GemSlots gemSlot;

	Gem(Texture text, float xPos, float yPos, GemColour colour) {
		super(text, xPos, yPos);
		this.colour = colour;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	
}
