package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.VillagerRole;

public class ToFarmerRitual extends Ritual {

	public ToFarmerRitual() {
		id = 7;
	}

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.GREEN, GemColour.GREEN, GemColour.GREEN, GemColour.GREEN};
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.FARMER);
	}
}
