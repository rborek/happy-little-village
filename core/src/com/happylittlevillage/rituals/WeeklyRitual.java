package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.Village;

import java.util.Random;

public class WeeklyRitual extends Ritual {
	private Village village;
	private static Random random = new Random();
	private int timesPerformed;
	private int timesToDo;

	public WeeklyRitual(Village village) {
		super();
		this.village = village;
		timesToDo = 3;
	}

	public boolean isComplete() {
		return timesToDo <= timesPerformed;
	}


	public int getTimesToDo() {
		return timesToDo;
	}

	public int getTimesPerformed() {
		return timesPerformed;
	}

	public int getTimesLeftToDo() {
		int time;
		return time = (timesToDo - timesPerformed >= 0) ? (timesToDo - timesPerformed) : 0;
	}

	public void generateRandom() {
		timesToDo = 3;
		timesPerformed = 0;

		int numGems = 3;
		recipe = new GemColour[numGems][numGems];
		gemCombination = new GemColour[numGems];
		for (int i = 0; i < numGems; i++) {
			for (int j = 0; j < numGems; j++) {
				recipe[i][j] = GemColour.values()[random.nextInt(GemColour.values().length - 1)];
			}
		}
		name = "Weekly Ritual";
		effects = new RitualEffect[0];
	}

	@Override
	protected void commence() {
		timesPerformed++;
	}
}