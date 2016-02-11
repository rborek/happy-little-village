package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.VillagerRole;

public class ToFarmerRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.GREEN, GemColour.GREEN, GemColour.GREEN, GemColour.GREEN};
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.FARMER);
	}
}
