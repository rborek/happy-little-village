package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class AddWaterRitual extends Ritual {
	
	public AddWaterRitual() {
		id = 4;
	}
	
	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.BLUE, GemColour.BLUE, GemColour.GREEN, GemColour.GREEN };
	}

	@Override
	protected void commence() {
		village.addWater(25);
	}

}

