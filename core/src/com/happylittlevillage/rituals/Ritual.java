package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.Village;

import java.util.ArrayList;

public abstract class Ritual {
	private String name;
	protected static Village village;
	protected GemColour[] gemCombination;
	private GemColour[][] recipe;
	private RitualEffect[] effects;
	protected static ArrayList<Ritual> rituals;

	public Ritual() {
		gemCombination = getCombination();
		// TODO
	}

	public Ritual(String file) {
		// TODO
	}

	public boolean attempt(Gem[] gems) {
		GemColour[] gemsToUse = new GemColour[gems.length];
		for (int i = 0; i < gems.length; i++) {
			if (gems[i] != null) {
				gemsToUse[i] = gems[i].getColour();
			}
		}
		if (gemCombination.length != gemsToUse.length) {
			return false;
		}
		boolean working = true;
		for (int i = 0; i < gemCombination.length; i++) {
			if (gemCombination[i] != gemsToUse[i]) {
				working = false;
			}
			if (!working) {
				return false;
			}
		}
		commence();
		return true;
	}

	protected abstract GemColour[] getCombination();

	/** Returns the recipe of GemColours for the ritual in a 2D array */
	public GemColour[][] getRecipe() {
		return recipe;
	}

	/** Returns the effects of the ritual in an array */
	public RitualEffect[] getEffects() {
		return effects;
	}

	protected abstract void commence();

	public static void setVillage(Village village) {
		Ritual.village = village;
	}

	public String getName() {
		return name;
	}

}
