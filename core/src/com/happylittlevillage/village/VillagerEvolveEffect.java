package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
