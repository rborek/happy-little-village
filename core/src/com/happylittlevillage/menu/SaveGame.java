package com.happylittlevillage.menu;

import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.rituals.Ritual;
import com.happylittlevillage.rituals.WeeklyRitual;
import com.happylittlevillage.village.Villager;

import java.util.ArrayList;

public class SaveGame {
	private int food;
	private int water;
	private int happiness;
	private float hoursLeft;
	private int daysLeft;
	private float day;
	private boolean isNextDay = false;
	private float villagerSpawnTimer;
	private int numVillagersToSpawn;
	private float gemThreshold;
	private int hunger;
	private int dehydration;
//    private WeeklyRitual weeklyRitual; // weeklyRitual
//    private ArrayList<Villager> villagers;
//    private GemBag gembag; // number of gems
//    private ArrayList<Ritual> rituals; // unlocked ritual

	public SaveGame(int food, int water, int happiness,
	                WeeklyRitual weeklyRitual, float hoursLeft, int daysLeft, float day,
	                boolean isNextDay, float villagerSpawnTimer, int numVillagersToSpawn,
	                float gemThreshold, int hunger, int dehydration, ArrayList<Villager> villagers,
	                GemBag gembag, ArrayList<Ritual> rituals) {
		this.food = food;
		this.water = water;
		this.happiness = happiness;
		this.hoursLeft = hoursLeft;
		this.daysLeft = daysLeft;
		this.day = day;
		this.isNextDay = isNextDay;
		this.villagerSpawnTimer = villagerSpawnTimer;
		this.numVillagersToSpawn = numVillagersToSpawn;
		this.gemThreshold = gemThreshold;
		this.hunger = hunger;
		this.dehydration = dehydration;
//        this.weeklyRitual = weeklyRitual;
//        this.villagers = villagers;
//        this.gembag = gembag;
//        this.rituals = rituals;
	}

	public SaveGame() {
	}


	public float getFood() {
		return food;
	}

	public float getWater() {
		return water;
	}

	public float getHappiness() {
		return happiness;
	}

	public float getHoursLeft() {
		return hoursLeft;
	}

	public float getDaysLeft() {
		return daysLeft;
	}

	public float getDay() {
		return day;
	}

	public boolean isNextDay() {
		return isNextDay;
	}

	public float getVillagerSpawnTimer() {
		return villagerSpawnTimer;
	}

	public int getNumVillagersToSpawn() {
		return numVillagersToSpawn;
	}

	public float getGemThreshold() {
		return gemThreshold;
	}

	public int getHunger() {
		return hunger;
	}

	public int getDehydration() {
		return dehydration;
	}

//    public ArrayList<Villager> getVillagers() {
//        return villagers;
//    }
//    public GemBag getGembag() {
//        return gembag;
//    }
//    public ArrayList<Ritual> getRituals() {
//        return rituals;
//    }
//    public WeeklyRitual getWeeklyRitual() {
//        return weeklyRitual;
//    }

}
