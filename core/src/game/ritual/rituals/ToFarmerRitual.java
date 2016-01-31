package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class ToFarmerRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.GREEN, GemColour.GREEN, GemColour.GREEN, GemColour.GREEN };
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.FARMER);
	}
}
