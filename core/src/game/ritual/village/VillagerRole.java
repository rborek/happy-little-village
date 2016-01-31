package game.ritual.village;

public enum VillagerRole {
	CITIZEN, MINER, FARMER, EXPLORER;

	public float foodConsumption() {
		switch (this) {
		case CITIZEN:
			return 1;
		case MINER:
			return 2;
		case FARMER:
			return 1;
		case EXPLORER:
			return 1.5f;
		default:
			return -1;
		}
	}

	public float waterConsumption() {
		switch (this) {
		case CITIZEN:
			return 1;
		case MINER:
			return 1;
		case FARMER:
			return 2;
		case EXPLORER:
			return 1.5f;
		default:
			return -1;
		}
	}
}
