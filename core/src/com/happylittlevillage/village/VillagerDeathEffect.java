package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.happylittlevillage.Assets;

public class VillagerDeathEffect extends VillagerEffect {
	public VillagerDeathEffect(Villager villager) {
		super(villager);
	}

	@Override
	protected TextureRegion[] getFrames() {
		return effectAtlas.findRegions("death_sprite").toArray(TextureRegion.class);
	}

}
