package com.happylittlevillage.rituals;

public enum VillageModifier {
    FOOD, HAPPINESS, VILLAGER, FARMER, EXPLORER, MINER, WATER;



    public int orderOf() {
        switch (this) {
            case FOOD:
            case HAPPINESS:
            case WATER:
                return 1;
            case VILLAGER:
                return 2;
            case FARMER:
            case EXPLORER:
            case MINER:
                return 3;
            default:
                return -1;
        }
    }
}
