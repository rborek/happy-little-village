package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.VillagerRole;

public class AddVillagerRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.YELLOW, GemColour.BLUE, GemColour.GREEN, GemColour.RED};
	}

	@Override
	protected void commence() {
		village.addVillager(VillagerRole.CITIZEN);
	}
}
