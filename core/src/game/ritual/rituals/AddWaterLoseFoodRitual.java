package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class AddWaterLoseFoodRitual extends Ritual {
	
	public AddWaterLoseFoodRitual() {
		id = 3;
	}

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.GREEN, GemColour.GREEN, GemColour.GREEN, GemColour.RED };
	}

	@Override
	protected void commence() {
		village.addWater(15);
		village.removeFood(20);
	}

}
