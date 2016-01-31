package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class ToFarmerRitual extends Ritual {
	
	public ToFarmerRitual() {
		id = 7;
	}
	
	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.GREEN, GemColour.GREEN, GemColour.GREEN, GemColour.GREEN };
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.FARMER);
	}
}
