package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.Villager;
import game.ritual.village.VillagerRole;

public class RemoveVillagerRitual extends Ritual {
	
	public RemoveVillagerRitual() {
		id = 9;
	}
    @Override
    protected GemColour[] getCombination() {
        return new GemColour[]{null, GemColour.YELLOW, GemColour.GREEN, null};
        }

    @Override
    protected void commence() {
        village.removeVillager();
    	village.addWater(40);
		village.addFood(25);
    }
}
