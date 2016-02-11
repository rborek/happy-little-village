package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;

public class AddFoodRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.GREEN, GemColour.GREEN, null, GemColour.RED};
	}

	@Override
	protected void commence() {
		village.addFood(25);
	}

}
