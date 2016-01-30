package game.ritual.gems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.GameObject;

public class Gem {
	GemColour colour;
	private final static Texture[] gemTextures = {new Texture("gems/gem_red.png"), new Texture("gems/gem_blue.png"),
			new Texture("gems/gem_green.png"), new Texture("gems/gem_yellow.png")};

	public Gem(GemColour colour) {
		this.colour = colour;
	}

	public void render(Batch batch, float xPos, float yPos) {
		batch.draw(getTexture(), xPos, yPos);
	}

	
	public GemColour getColour(){
		return colour;
	}

	public Texture getTexture() {
		return gemTextures[colour.ordinal()];
	}



	
}
