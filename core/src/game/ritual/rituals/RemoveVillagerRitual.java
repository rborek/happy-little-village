package game.ritual.rituals;

import game.ritual.gems.GemColour;

public class RemoveVillagerRitual extends Ritual {

	public RemoveVillagerRitual() {
		id = 5;
	}

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{null, GemColour.YELLOW, GemColour.GREEN, null};
	}

	@Override
	protected void commence() {
		if (village.removeVillager()) {
			village.addWater(40);
			village.addFood(25);
		}
	}
}
