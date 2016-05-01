package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.Village;

import java.util.Random;

public class WeeklyRitual extends Ritual {
	private Village village;
	private static Random random = new Random();
	private int timesPerformed;
	private int timesToDo;
	private int instanceOfWeeklyRitual = 0; // how many weekly ritual has there been.

	public WeeklyRitual(Village village) {
		super();
		this.village = village;
		timesToDo = 3;
	}

	public boolean isComplete() {
		return timesToDo <= timesPerformed;
	}


	private void setCombination(int numGemsRow, int numGemsCol){
		name = "Weekly Ritual";
		effects = new RitualEffect[0];
		recipe = new GemColour[numGemsRow][numGemsCol];
		timesPerformed = 0;
		for (int i = 0; i < numGemsRow; i++) {
			for (int j = 0; j < numGemsCol; j++) {
				recipe[i][j] = GemColour.values()[random.nextInt(GemColour.values().length - 1)];
			}
		}
	}

	public void generateRandom() {
		instanceOfWeeklyRitual++;
		if(instanceOfWeeklyRitual < 3){
			timesToDo = 3;
			int numGemsRow = 2;
			int numGemsCol = 2;
			setCombination(numGemsRow, numGemsCol);
		} else if(instanceOfWeeklyRitual < 7) {
			timesToDo = 3;
			int numGemsRow = 2;
			int numGemsCol = 3;
			setCombination(numGemsRow, numGemsCol);
		} else if (instanceOfWeeklyRitual < 11){
			timesToDo = 3;
			int numGemsRow = 3;
			int numGemsCol = 3;
			setCombination(numGemsRow, numGemsCol);
		}

	}

	@Override
	protected void commence() {
		timesPerformed++;
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
}