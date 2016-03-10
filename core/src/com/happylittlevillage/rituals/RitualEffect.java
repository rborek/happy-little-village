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
		switch (modifier) {
			case FOOD:
				village.addFood(amount);
				System.out.println("Food added is:" + amount);
				break;
			case VILLAGER:
				if(amount > 0){
					for(int k = 0; k < amount; k++){
						village.addVillager(VillagerRole.CITIZEN);
					}
				}
				else{
					for(int k = 0; k < (-amount); k++){
						village.removeVillager();
					}
				}
				break;
			case WATER:
				village.addWater(amount);
				System.out.println("Water added is:" + amount);
				break;
			case FARMER:
				village.convertCitizen(VillagerRole.FARMER);
				System.out.println("A farmer is converted");
				break;
			case EXPLORER:
				village.convertCitizen(VillagerRole.EXPLORER);
				System.out.println("An explorer is converted");
				break;
			case MINER:
				village.convertCitizen(VillagerRole.MINER);
				System.out.println("A miner is converted");
				break;
			case HAPPINESS:
				village.addHappiness(amount);
		}
	}

	public VillageModifier getModifier() {
		return modifier;
	}

	public int getAmount() {
		return amount;
	}
}
