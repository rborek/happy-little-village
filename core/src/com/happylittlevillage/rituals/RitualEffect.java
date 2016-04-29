package com.happylittlevillage.rituals;

import com.happylittlevillage.village.Village;
import com.happylittlevillage.village.VillagerRole;

public class RitualEffect implements Comparable<RitualEffect> {
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
				if (amount > 0) {
					for (int k = 0; k < amount; k++) {
						village.addVillager(VillagerRole.CITIZEN);
					}
				} else {
					for (int k = 0; k < (-amount); k++) {
						village.removeVillager();
					}
				}
				break;
			case WATER:
				village.addWater(amount);
				System.out.println("Water added is:" + amount);
				break;
			case FARMER:
				if(amount > 0 ) { // if add
					for (int k = 0; k < amount; k++) {
						village.convertCitizen(VillagerRole.FARMER);
					}
				}
				else{ // if reconvert a role
					for(int k = 0; k < - amount; k ++){
						village.reconvertCitizen(VillagerRole.FARMER);
					}
				}
				break;
			case EXPLORER:
				if(amount > 0 ) { // if add
					for (int k = 0; k < amount; k++) {
						village.convertCitizen(VillagerRole.EXPLORER);
					}
				}
				else{ // if reconvert a role
					for(int k = 0; k < - amount; k ++){
						village.reconvertCitizen(VillagerRole.EXPLORER);
					}
				}
				break;
			case MINER:
				if(amount > 0 ) { // if add
					for (int k = 0; k < amount; k++) {
						village.convertCitizen(VillagerRole.MINER);
					}
				}
				else{ // if reconvert a role
					for(int k = 0; k < - amount; k ++){
						village.reconvertCitizen(VillagerRole.MINER);
					}
				}
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

	@Override
	public int compareTo(RitualEffect other) {
		return this.modifier.orderOf() - other.modifier.orderOf();
	}
}
