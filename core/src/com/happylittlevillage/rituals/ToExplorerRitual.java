package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.VillagerRole;

public class ToExplorerRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.BLUE, GemColour.BLUE, GemColour.BLUE, GemColour.BLUE};
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.EXPLORER);
	}

}
