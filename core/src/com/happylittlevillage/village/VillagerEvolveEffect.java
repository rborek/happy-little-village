package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.happylittlevillage.Assets;

public class
VillagerEvolveEffect extends VillagerEffect {
	public VillagerEvolveEffect(Villager villager) {
		super(villager);
	}

	@Override
	protected TextureRegion[] getFrames() {
		return effectAtlas.findRegions("evolve_sprite").toArray(TextureRegion.class);
	}


}
