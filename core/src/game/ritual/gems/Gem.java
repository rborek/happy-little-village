package game.ritual.gems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.Assets;


public class Gem {
	private GemColour colour;
	private final static Texture[] gemTextures = {Assets.getTexture("gems/gem_red.png"), Assets.getTexture("gems/gem_blue.png"),
			Assets.getTexture("gems/gem_green.png"), Assets.getTexture("gems/gem_yellow.png")};
	private final static Texture[] smallGemTextures = {new Texture("gems/mini_gem_red.png"), new Texture("gems/mini_gem_blue.png"),
			new Texture("gems/mini_gem_green.png"), new Texture("gems/mini_gem_yellow.png")};

	public Gem(GemColour colour) {
		this.colour = colour;
	}

	public void render(Batch batch, float xPos, float yPos) {
		batch.draw(getTexture(), xPos, yPos);
	}

	public GemColour getColour() {
		return colour;
	}

	public Texture getTexture() {
		return gemTextures[colour.ordinal()];
	}

	public static Texture[] getArrayOfTextures() {
		return gemTextures;
	}

	public static Texture[] getArrayOfMiniTextures() {
		return smallGemTextures;
	}


}
