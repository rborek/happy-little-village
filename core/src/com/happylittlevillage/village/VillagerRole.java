package com.happylittlevillage.village;

public enum VillagerRole {
	CITIZEN, MINER, FARMER, EXPLORER;

	public float foodConsumption() {
		switch (this) {
			case CITIZEN:
				return 0.1f;
			case MINER:
				return 0.2f;
			case FARMER:
				return 0.1f;
			case EXPLORER:
				return 0.15f;
			default:
				return -1;
		}
	}

	public float happinessConsumption() {
		switch (this) {
			case CITIZEN:
				return 0.1f;
			case MINER:
				return 0.1f;
			case FARMER:
				return 0.2f;
			case EXPLORER:
				return 0.15f;
			default:
				return -1;
		}
	}
}
