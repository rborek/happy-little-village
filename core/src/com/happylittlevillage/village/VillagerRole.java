package com.happylittlevillage.village;

public enum VillagerRole {
	CITIZEN, MINER, FARMER, EXPLORER;

	public float foodConsumption() {
		switch (this) {
			case CITIZEN:
				return 1f;
			case MINER:
				return 1.25f;
			case FARMER:
				return 1f;
			case EXPLORER:
				return 1.5f;
			default:
				return -1;
		}
	}

	public float waterConsumption() {
		switch (this) {
			case CITIZEN:
				return 1f;
			case MINER:
				return 1.25f;
			case FARMER:
				return 1.5f;
			case EXPLORER:
				return 1f;
			default:
				return -1;
		}
	}
}
