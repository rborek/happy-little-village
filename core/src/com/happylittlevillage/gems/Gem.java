package com.happylittlevillage.gems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.Assets;


public class Gem {
	private GemColour colour;
	private final static Texture[] gemTextures = {Assets.getTexture("gems/gem_red.png"), Assets.getTexture("gems/gem_blue.png"),
			Assets.getTexture("gems/gem_green.png"), Assets.getTexture("gems/gem_yellow.png")};

	public Gem(GemColour colour) {
		this.colour = colour;
	}

	public void render(Batch batch, float xPos, float yPos) {
		batch.draw(getTexture(), xPos, yPos, RitualAltar.SLOT_SIZE, RitualAltar.SLOT_SIZE);
	}

	public void render(Batch batch, float xPos, float yPos, float width, float height) {
		batch.draw(getTexture(), xPos, yPos, width, height);
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


}
