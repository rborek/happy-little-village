package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.Villager;
import game.ritual.village.VillagerRole;

public class ToMinerRitual extends Ritual {
	
	public ToMinerRitual() {
		id = 8;
	}
	
	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.YELLOW, GemColour.YELLOW, GemColour.YELLOW, GemColour.YELLOW };
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.MINER);
	}
}
