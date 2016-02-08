package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.VillagerRole;

public class ToMinerRitual extends Ritual {

	public ToMinerRitual() {
		id = 8;
	}

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.YELLOW, GemColour.YELLOW, GemColour.YELLOW, GemColour.YELLOW};
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.MINER);
	}
}
