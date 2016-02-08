package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;

public class AddFoodLoseWaterRitual extends Ritual {

	public AddFoodLoseWaterRitual() {
		id = 0;
	}

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.BLUE, GemColour.BLUE, GemColour.BLUE, GemColour.GREEN};
	}

	@Override
	protected void commence() {
		village.addFood(40);
		village.removeWater(25);
	}

}
