package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.VillagerRole;
import game.ritual.village.Villager;

public class AddFoodRitual extends Ritual {
	
	public AddFoodRitual() {
		id =0;
	}
	@Override
	protected GemColour[] getCombination() {
		return new GemColour[] { GemColour.GREEN, GemColour.GREEN, GemColour.RED };
	}

	@Override
	protected void commence() {
		village.addFood(10);
	}

}
