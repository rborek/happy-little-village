package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VillagerDeathEffect extends VillagerEffect {
	public VillagerDeathEffect(Villager villager) {
		super(villager);
	}

	@Override
	protected TextureRegion[] getFrames() {
		return effectAtlas.findRegions("death_sprite").toArray(TextureRegion.class);
	}

}
