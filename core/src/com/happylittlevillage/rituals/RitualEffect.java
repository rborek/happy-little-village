package com.happylittlevillage.rituals;

import com.happylittlevillage.village.Village;

public class RitualEffect {
	private VillageModifier modifier;
	private int amount;

	public RitualEffect(VillageModifier modifier, int amount) {
		this.modifier = modifier;
		this.amount = amount;
	}

	public void affectVillage(Village village) {
		// TODO
	}

}
