package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class AddWaterRemoveFoodRitual extends Ritual {

	public AddWaterRemoveFoodRitual() {
		id = 3;
	}
	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.RED, GemColour.GREEN, GemColour.GREEN, GemColour.GREEN };
	}

	@Override
	protected void commence() {
		village.addWater(40);
		village.removeFood(25);
	}

}
