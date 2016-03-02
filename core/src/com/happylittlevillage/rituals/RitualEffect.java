package com.happylittlevillage.rituals;

import com.happylittlevillage.village.Village;
import com.happylittlevillage.village.VillagerRole;

public class RitualEffect {
	private VillageModifier modifier;
	private int amount;

	public RitualEffect(VillageModifier modifier, int amount) {
		this.modifier = modifier;
		this.amount = amount;
	}

	public void affectVillage(Village village) {
		switch (modifier){
			case FOOD:
				village.addFood(amount);
				break;
			case VILLAGER:
				int k =0;
				while( k < amount){
					village.addVillager(VillagerRole.CITIZEN);
				}
				break;
			case HAPPINESS:
				village.addHappiness(amount);
				break;
			case FARMER:
				village.convertCitizen(VillagerRole.FARMER);
				break;
			case EXPLORER:
				village.convertCitizen(VillagerRole.EXPLORER);
				break;
			case  MINER:
				village.convertCitizen(VillagerRole.MINER);
				break;

		}
	}
	public VillageModifier getModifier(){
		return modifier;
	}
	public int getAmount(){
		return amount;
	}
}
