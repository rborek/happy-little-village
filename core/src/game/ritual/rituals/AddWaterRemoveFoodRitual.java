package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class AddWaterRemoveFoodRitual extends Ritual {

	public AddWaterRemoveFoodRitual() {
		id = 4;
	}
	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.GREEN, GemColour.GREEN, GemColour.GREEN, GemColour.RED };
	}

	@Override
	protected void commence() {
		village.addWater(40);
		village.removeFood(25);
	}

}
