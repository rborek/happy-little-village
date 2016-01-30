package game.ritual.gems;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.GameObject;

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
	
	public GemColour getColour(){
		return colour;
	}
	
}
