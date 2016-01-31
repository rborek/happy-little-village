package game.ritual.rituals;

import game.ritual.gems.Gem;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;

public abstract class Ritual {
    private int id;
    protected static Village village;
    protected GemColour[] gemCombination;

    public Ritual() {
        gemCombination = getCombination();
    }

    public boolean attempt(Gem[] gems) {
        GemColour[] gemsToUse = new GemColour[gems.length];
        for (int i = 0; i < gems.length; i++) {
            gemsToUse[i] = gems[i].getColour();
        }
        boolean working = false;
        int ritualGemIndex = 0;
        int totalGemsUsed = 0;
        for (int i = 0; i < gemsToUse.length; i++) {
            if (gemCombination[ritualGemIndex] == gemsToUse[i]) {
                ritualGemIndex++;
                i = 0;
            }
            if (ritualGemIndex > gemCombination.length) {
                commence();
                return true;
            }
        }
        return false;
    }

    protected abstract GemColour[] getCombination();

    protected abstract void commence();

    public static void setVillage(Village village) {
        Ritual.village = village;
    }

    public int getID() {
        return id;
    }

}
