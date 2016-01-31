package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class AddFoodLoseWaterRitual extends Ritual {

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.BLUE, GemColour.BLUE, GemColour.GREEN, GemColour.BLUE };
	}

	@Override
	protected void commence() {
		village.addFood(15);
		village.removeWater(20);
	}

}
