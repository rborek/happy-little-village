package com.happylittlevillage.rituals;

import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.Village;

import java.util.Random;

public class WeeklyRitual extends Ritual {
    private Village village;
    private static Random random = new Random();
    private int timesPerformed;
    private int timesToDo;

    public WeeklyRitual(int numGems, int timesToDo, Village village) {
        super();
        generateRandom(numGems);
        this.village = village;
        this.timesToDo = timesToDo;
    }

    public boolean isComplete() {
        return timesToDo == timesPerformed;
    }


    public int getTimesToDo() {
        return timesToDo;
    }

    public int getTimesPerformed() {
        return timesPerformed;
    }

    private void generateRandom(int numGems) {
        recipe = new GemColour[numGems][numGems];
        gemCombination = new GemColour[numGems];
        for (int i = 0; i < numGems; i++) {
            for (int j = 0; i < numGems; i++) {
                recipe[i][j] = GemColour.values()[random.nextInt(GemColour.values().length - 1)];
            }

        }
    }

    @Override
    protected void commence() {
        timesPerformed++;
    }
}