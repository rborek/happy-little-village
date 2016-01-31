package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.Villager;
import game.ritual.village.VillagerRole;

public class AddVillagerRitual extends Ritual {
	
	public AddVillagerRitual() {
		id = 2;
	}
    @Override
    protected GemColour[] getCombination() {
        return new GemColour[]{GemColour.YELLOW, GemColour.BLUE, GemColour.GREEN, GemColour.RED};
        }

    @Override
    protected void commence() {
        village.addVillager(VillagerRole.CITIZEN);
    }
}
