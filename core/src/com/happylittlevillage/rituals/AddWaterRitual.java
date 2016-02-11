package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;

public class AddWaterRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.BLUE, GemColour.BLUE, GemColour.GREEN, GemColour.GREEN};
	}

	@Override
	protected void commence() {
		village.addWater(25);
	}

}

