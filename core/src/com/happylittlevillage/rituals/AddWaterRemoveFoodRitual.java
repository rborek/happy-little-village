package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;

public class AddWaterRemoveFoodRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.RED, GemColour.GREEN, GemColour.GREEN, GemColour.GREEN};
	}

	@Override
	protected void commence() {
		village.addWater(40);
		village.removeFood(25);
	}

}
