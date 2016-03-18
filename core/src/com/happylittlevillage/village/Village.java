package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.rituals.WeeklyRitual;

import java.util.*;

public class Village {
	private static final int MAX_HOURS = 24;
	private ArrayList<Villager> villagers;
	private ArrayList<Villager> deadVillagers = new ArrayList<Villager>();
	private ArrayList<VillagerEffect> effects = new ArrayList<VillagerEffect>();
	private float food = 0;
	private float consumedFood = 0;
	private float gatheredFood = 0;
	private float water = 0;
	private float consumedWater = 0;
	private float gatheredWater = 0;
	private float happiness = 100;
	private float hungerTimer = 0;
	private float dehydrationTimer = 0;
	//TODO set case when happiness fall below 0
	private WeeklyRitual weeklyRitual;
	private VillageInformation villageInformation;
	private float hoursLeft;
	private float daysLeft;
	private int day;
	private boolean isNextDay = false;
	private int villagerAdded = 0;
	private int villagerRemoved = 0;
	private static Random random = new Random();
	private float villagerSpawnTimer = 0;
	private Queue<Villager> villagersToSpawn = new ArrayDeque<Villager>();
	private float gemThreshold = 0;
	//	private static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
//		@Override
//		protected Rectangle newObject() {
//			return new Rectangle();
//		}
//	};
	private int[] gemsMined = new int[4];
	private GemBag gemBag;

	private int hunger = 0;
	private int dehydration = 0;
	//TODO add rituals that monitor productions
	private float foodProduction;
	private float waterProduction;


	public Village(GemBag gemBag, float food, float water, float startingVillagers) {
		this.villagers = new ArrayList<Villager>();
		this.food = food;
		this.water = water;
		for (int i = 0; i < startingVillagers; i++) {
			this.addVillager(VillagerRole.CITIZEN);
		}
		hoursLeft = MAX_HOURS;
		daysLeft = 8;
		day = 0;
		this.gemBag = gemBag;
		villageInformation = new VillageInformation(this, 60, 10);
		generateNewWeeklyRitual();
	}


	public int getDaysLeft() {
		return (int) daysLeft;
	}

	public void setDaysLeft(int weeks) {
		daysLeft = weeks;
	}

	public boolean convertCitizen(VillagerRole role) {
		for (Villager villager : villagers) {
			if (villager.getRole() == VillagerRole.CITIZEN) {
				villager.setRole(role);
				effects.add(new VillagerEvolveEffect(villager));
				return true;
			}
		}
		for (Villager villager : villagersToSpawn) {
			if (villager.getRole() == VillagerRole.CITIZEN) {
				villager.setRole(role);
				return true;
			}
		}
		return false;
	}

	public void consume(float delta) {
//        return;
		float consumeFood = 0;
		float consumeWater = 0;
		for (Villager villager : villagers) {
			consumeWater += villager.getRole().waterConsumption() / 4;
			consumeFood += villager.getRole().foodConsumption() / 4;
		}
		food -= consumeFood * delta;
		consumedFood += consumeFood * delta;
		water -= consumeWater * delta;
		consumedWater += consumeWater * delta;
	}

	public WeeklyRitual getWeeklyRitual() {
		return weeklyRitual;
	}

	public void generateNewWeeklyRitual() {
		weeklyRitual = new WeeklyRitual(2, 3, this);
	}

	public void addFood(float x) {
		food += x;
		villageInformation.getAddedResource("food", (int) x);
	}

	public void addWater(float y) {
		water += y;
		villageInformation.getAddedResource("water", (int) y);
	}

	public void addHappiness(float y) {
		happiness += y;
		villageInformation.getAddedResource("happiness", (int) y);
	}

	public void removeFood(float x) {
		food -= x;
		food = Math.max(0, food);
	}

	public void removeWater(float x) {
		water -= x;
		water = Math.max(0, water);
	}

	public void update(float delta) {
		for (Villager villager : villagers) {
			villager.update(delta);
		}
		isNextDay = false;
		gatherResources(delta);
		consume(delta);
		food = Math.max(food, 0);
		water = Math.max(water, 0);
		timePass(delta);
		//decrement time of spawning
		if (villagerSpawnTimer > 0) {
			villagerSpawnTimer -= delta;
		} else { //if enough time pass call the rest of the villagers needed to be spawn
			queueSpawn();
		}
		decay(delta);
		updateVillagerEffects(delta);
		villageInformation.update(delta);
	}

	private void gatherResources(float delta) {
		gatherFood(delta);
		gatherWater(delta);
		mineGems(delta);
	}

	public void gatherFood(float delta) {
		float food = 0;
		foodProduction = 1f;
		for (Villager villager : villagers) {
			if (villager.getRole().equals(VillagerRole.FARMER)) {
				food += foodProduction * delta;
			}
		}
		if (food != 0) {
			this.food += food;
		}
	}

	//TODO check the algorithm. Maybe less dependent on delta
	public void gatherWater(float delta) {
		float water = 0;
		waterProduction = 1f;

		for (Villager villager : villagers) {
			if (villager.getRole().equals(VillagerRole.EXPLORER)) {
				water += waterProduction * delta;
			}
		}
		if (water != 0) {
			this.water += water;
		}
	}

	public void mineGems(float delta) {
		gemThreshold += delta / 30 * getNumberOf(VillagerRole.MINER);
		if (gemThreshold > 1) {
			//get a random gemColour and store it in gemsMined according to its ordinal
			GemColour g = gemBag.gainRandomGem();
			gemBag.add(g);
			gemThreshold = 0;
		}
	}

	private void updateVillagerEffects(float delta) {
		for (int i = 0; i < effects.size(); i++) {
			effects.get(i).update(delta);
			if (effects.get(i).isDone()) {
				if (effects.get(i) instanceof VillagerDeathEffect) {
					deadVillagers.remove(effects.get(i).getVillager());
				}
				effects.remove(i);
				i--;
			}
		}
	}

	private void decay(float delta) {
		if (food <= 0) {
			hungerTimer += delta;
			if (Math.random() * hungerTimer * 2 > 3) {
				this.removeVillager();
				hungerTimer = 0;
			}
		} else {
			hungerTimer = 0;
		}

		if (water <= 0) {
			dehydrationTimer += delta;
			if (Math.random() * dehydrationTimer * 2 > 3) {
				this.removeVillager();
				dehydrationTimer = 0;
			}
		} else {
			dehydrationTimer = 0;
		}
	}

	private void timePass(float delta) {
		hoursLeft -= delta * .3f;
		if (hoursLeft <= 0) {
			dayPass();
		}
	}

	public Vector2 getPositionOfARandomVillager() {
		return villagers.get(0).getPosition();
	}

	public void addVillager(VillagerRole role) {
		if (villagersToSpawn.isEmpty()) {
			if (villagerSpawnTimer <= 0) {
				villagers.add(new Villager(role, this));
				villagerAdded += 1;
				villagerSpawnTimer = (float) 0.5;
			} else {
				villagersToSpawn.add(new Villager(role, this));
			}
		} else {
			villagersToSpawn.add(new Villager(role, this));
		}
	}

	// The queue of spawning villagers
	private void queueSpawn() {
		if (!villagersToSpawn.isEmpty()) {
			villagers.add(villagersToSpawn.remove());
			villagerAdded += 1;
			villagerSpawnTimer = (float) 0.5;
		}
	}


	public void dayPass() {
		villagerRemoved = 0;
		daysLeft -= 1;
		day += 1;
		hoursLeft = MAX_HOURS;
		isNextDay = true;
		villagerAdded = 0;
	}

	public void mineGems() {
		// reset the gem from previous week
		for (int resetGem : gemsMined) {
			resetGem = 0;
		}
		//get a random gemColour and store it in gemsMined according to its ordinal
		for (int i = 0; i < getNumberOf(VillagerRole.MINER) * 3; i++) {
			GemColour g = gemBag.gainRandomGem();
			gemsMined[g.ordinal()]++;
			System.out.println(gemsMined[g.ordinal()]);
		}
	}

	public int[] getMinedGems() {
		return gemsMined;
	}

	public boolean isNextDay() {
		return isNextDay;
	}

	public float getHoursLeft() {
		return hoursLeft;
	}

	public float getDay() {
		return day;
	}

	public void render(Batch batch) {
		Collections.sort(villagers);
		for (Villager villager : villagers) {
			villager.render(batch);
		}
		for (Villager villager : deadVillagers) {
			villager.render(batch);
		}
		for (VillagerEffect villagerEffect : effects) {
			villagerEffect.render(batch);
		}
		villageInformation.render(batch);
	}


	// return a position that does not overlap current villagers
	public Vector2 getEmptyPosition() {
//        Rectangle newPosition = rectPool.obtain();
//        newPosition.set(randomX(), randomY(), 0, 0);
//        Rectangle test; // position of the current villager
//        Rectangle test2;// destination of the current villager
//        Rectangle[] forbidden = new Rectangle[villagers.size() * 2];
//
//        int count = 0;
//        for (Villager villager : villagers) {
//            newPosition.setWidth(villager.getWidth());
//            newPosition.setHeight(villager.getHeight());
//            test = rectPool.obtain();
//            test.set(villager.getPosition().x, villager.getPosition().y, villager.getWidth(),
//                    villager.getHeight());
//            forbidden[count] = test;
//            count++;
//            test2 = rectPool.obtain();
//            test2.set(villager.getDestination().x, villager.getDestination().y, villager.getWidth(),
//                    villager.getHeight());
//            forbidden[count] = test2;
//            count++;
//            rectPool.free(test);
//            rectPool.free(test2);
//        }
//        newPosition.setPosition(randomX(), randomY());
//        for (int i = 0; i < forbidden.length; i++) {
//            if (newPosition.overlaps(forbidden[i])) {
//                newPosition.setPosition(randomX(), randomY());
//                i = -1;
//            }
//        }
//        float xPos = newPosition.x;
//        float yPos = newPosition.y;
//        rectPool.free(newPosition);
		return new Vector2(randomX(), randomY());
	}

	private int randomX() {
		return random.nextInt(685) + 5;
	}

	private int randomY() {
		return random.nextInt(400) + 180;
	}


	public boolean removeVillager() {
		if (villagers.size() > 0) {
			Random random = new Random();
			int randomInt = random.nextInt(villagers.size());
			effects.add(new VillagerDeathEffect(villagers.get(randomInt)));
			deadVillagers.add(villagers.get(randomInt));
			villagers.remove(randomInt);
			villagerRemoved++;
			return true;
		}
		return false;
	}

	public Villager getVillager(int a) {
		return villagers.get(a);
	}

	public int getNumberOf(VillagerRole a) {
		int count = 0;
		for (Villager villager : villagers) {
			if (villager.getRole().equals(a)) {
				count++;
			}
		}
		return count;

	}

	public int getVillagerRemoved() {
		return villagerRemoved;
	}

	public int getSize() {
		return villagers.size();
	}

	public int getMaxHours() {
		return MAX_HOURS;
	}

	public boolean isEmpty() {
		return villagers.size() == 0;
	}

	// set the ArrayList of villagers
	public void setVillage(ArrayList<Villager> a) {
		villagers = a;
	}

	public int getFood() {
		return (int) Math.ceil(food);
	}

	public int getWater() {
		return (int) Math.ceil(water);
	}

	public int getGatheredFood() {
		return (int) gatheredFood;
	}

	public int getGatheredWater() {
		return (int) gatheredWater;
	}

	public int getPop() {
		return villagers.size();
	}

	public int getVillagerAdded() {
		return villagerAdded;
	}

	public int getConsumedFood() {
		return (int) consumedFood;
	}

	public int getConsumedWater() {
		return (int) consumedWater;
	}

	public int getHappiness() {
		return (int) happiness;
	}

	public float getVillagerSpawnTimer() {
		return villagerSpawnTimer;
	}

	public Queue<Villager> getVillagersToSpawn() {
		return villagersToSpawn;
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

	public ArrayList<Villager> getVillagers() {
		return villagers;
	}


}
