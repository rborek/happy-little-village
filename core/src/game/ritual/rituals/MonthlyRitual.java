package game.ritual.rituals;

import game.ritual.gems.GemColour;
import game.ritual.village.Village;

import java.util.Random;

public class MonthlyRitual extends Ritual {
    private Village village;
    private int timesPerformed;
    private int timesToDo;
    public MonthlyRitual(int numGems, Village village) {
        super();
        generateRandom(numGems);
        this.village = village;
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

    private void generateRandom(int numGems) {
        gemCombination = new GemColour[numGems];
        Random random = new Random();
        for (int i = 0; i < numGems; i++) {
            gemCombination[i] = GemColour.values()[random.nextInt(GemColour.values().length)];
        }
    }

    @Override
    protected void commence() {
        timesPerformed++;
    }
}
