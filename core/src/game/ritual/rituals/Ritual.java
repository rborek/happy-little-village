package game.ritual.rituals;

import game.ritual.gems.Gem;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;

public abstract class Ritual {
    public int id;
    protected static Village village;
    protected GemColour[] gemCombination;

    public Ritual() {
        gemCombination = getCombination();
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

    protected abstract void commence();

    public static void setVillage(Village village) {
        Ritual.village = village;
    }

    public int getID() {
        return id;
    }

}
