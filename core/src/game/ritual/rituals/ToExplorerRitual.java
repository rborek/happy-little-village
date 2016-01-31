package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class ToExplorerRitual extends Ritual {
	
	public ToExplorerRitual() {
		id = 6;
	}
	
	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.BLUE, GemColour.BLUE, GemColour.BLUE, GemColour.BLUE };
	}

	@Override
	protected void commence() {
		village.convertCitizen(VillagerRole.EXPLORER);
	}

}
