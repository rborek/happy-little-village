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
        id = 1337;
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

    @Override
    protected GemColour[] getCombination() {
        return new GemColour[0];
    }

    public GemColour[] getColours() {
        return gemCombination;
    }
    
    private void generateRandom(int numGems) {
        gemCombination = new GemColour[numGems];
        for (int i = 0; i < numGems; i++) {
            gemCombination[i] = GemColour.values()[random.nextInt(GemColour.values().length)];
        }
    }

    @Override
    protected void commence() {
        timesPerformed++;
    }
}
