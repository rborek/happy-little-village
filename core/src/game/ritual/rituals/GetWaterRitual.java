package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class GetWaterRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.BLUE, GemColour.BLUE, GemColour.GREEN, GemColour.GREEN };
	}

	@Override
	protected void commence() {
		village.addWater(15);
	}

}

