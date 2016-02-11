package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.rituals.WeeklyRitual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Village {
	private static final int MAX_HOURS = 24;
	private ArrayList<Villager> villagers;
	private ArrayList<Villager> deadVillagers = new ArrayList<Villager>();
	private ArrayList<VillagerEffect> effects = new ArrayList<VillagerEffect>();
	private WeeklyRitual weeklyRitual = new WeeklyRitual(4, 4, this);
	private float food = 0;
	private float consumedFood = 0;
	private float gatheredFood = 0;
	private float water = 0;
	private float consumedWater = 0;
	private float gatheredWater = 0;
	private VillageInformation villageInformation;
	private float hoursLeft;
	private float daysLeft;
	private int day;
	private boolean isNextDay = false;
	private int villagerAdded = 0;
	private int villagerRemoved = 0;
	private static Random random = new Random();
//	private static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
//		@Override
//		protected Rectangle newObject() {
//			return new Rectangle();
//		}
//	};
	private int[] gemsMined = new int[4];
	private GemBag gemBag;

	public Village(GemBag gemBag) {
		this.villagers = new ArrayList<Villager>();
		food = 5000;
		water = 5000;
		hoursLeft = MAX_HOURS;
		daysLeft = 5;
		day = 0;
		this.gemBag = gemBag;
		villageInformation = new VillageInformation(this, 60, 10);
	}

	public WeeklyRitual getWeeklyRitual() {
		return weeklyRitual;
	}

	public void newWeeklyRitual() {
		weeklyRitual = new WeeklyRitual(4, 2 + day / 2, this);
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
		return false;
	}

	public void consume(float delta) {
//        return;
		float consumeFood = 0;
		float consumeWater = 0;
		for (Villager villager : villagers) {
			if (villager.getRole().equals(VillagerRole.FARMER)) {
				consumeFood += 1.05 * (villager.getRole().foodConsumption());
				consumeWater += 1.10 * (villager.getRole().waterConsumption());
			} else if (villager.getRole().equals(VillagerRole.EXPLORER)) {
				consumeFood += 1.15 * (villager.getRole().foodConsumption());
				consumeWater += 1.20 * (villager.getRole().waterConsumption());
			} else if (villager.getRole().equals(VillagerRole.MINER)) {
				consumeFood += 1.25 * (villager.getRole().foodConsumption());
				consumeWater += 1.30 * (villager.getRole().waterConsumption());
			} else {
				consumeFood += villager.getRole().foodConsumption();
			}
		}
		food -= consumeFood * delta;
		consumedFood += consumeFood * delta;
		consumedFood /= 10;

		for (Villager villager : villagers) {
			consumeWater += villager.getRole().foodConsumption();
		}
		water -= consumeWater * delta;
		consumedWater += consumeWater * delta;
		consumedWater /= 10;
	}

	public void addFood(float x) {
		food += x;
	}

	public void addWater(float y) {
		water += y;
	}

	public void gatheredFood() {
		float food = 0;
		for (Villager villager : villagers) {
			if (villager.getRole().equals(VillagerRole.FARMER)) {
				food += 7;
			}
		}
		addFood(food);
		gatheredFood = food;
	}

	public void gatheredWater() {
		float water = 0;
		for (Villager villager : villagers) {
			if (villager.getRole().equals(VillagerRole.EXPLORER)) {
				water += 10;
			}
			addWater(water);
			gatheredWater = water;
		}
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
		isNextDay = false;
		consume(delta);

		timePass(delta);
	}

	private void timePass(float delta) {
		hoursLeft -= (float) (delta * 0.2);
		if (hoursLeft <= 0) {
			dayPass();
		}
	}

	public void dayPass() {
		villagerRemoved = 0;
		daysLeft -= 1;
		day += 1;
		hoursLeft = MAX_HOURS;
		isNextDay = true;
		villagerAdded = 0;
		mineGems();
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

	public void addVillager(VillagerRole role) {
		villagers.add(new Villager(role, this));
		villagerAdded += 1;
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
}
