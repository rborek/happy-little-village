package game.ritual.gems;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.GameObject;

public class Gem {
	GemColour colour;
	private static Texture[] gemTextures;
	private static GemSlots gemSlot;

	public Gem(Texture text, float xPos, float yPos, GemColour colour) {
		this.colour = colour;
	}
	
	public GemColour getColour(){
		return colour;
	}

	public Texture getTexture() {
		return gemTextures[colour.ordinal()];
	}



	
}
