package game.ritual.rituals;

import game.ritual.gems.GemColour;

public class AddFoodLoseWaterRitual extends Ritual {

	public AddFoodLoseWaterRitual() {
		id = 0;
	}

	@Override
	protected GemColour[] getCombination() {
		return new GemColour[]{GemColour.BLUE, GemColour.BLUE, GemColour.BLUE, GemColour.GREEN};
	}

	@Override
	protected void commence() {
		village.addFood(40);
		village.removeWater(25);
	}

}
