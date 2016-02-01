package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.ritual.rituals.MonthlyRitual;

import java.util.ArrayList;
import java.util.Random;

public class Village {
	private static final int MAX_HOURS = 10;
	private ArrayList<Villager> villagers;
	private ArrayList<Villager> deadVillagers = new ArrayList<Villager>();
	private ArrayList<VillagerEffect> effects = new ArrayList<VillagerEffect>();
	private MonthlyRitual monthlyRitual = new MonthlyRitual(4, 4, this);
	private float food = 0;
	private float consumedFood = 0;
	private float gatheredFood = 0;
	private float water = 0;
	private float consumedWater = 0;
	private float gatheredWater = 0;
	private VillageInformation info;
	private float hoursLeft;
	private float weeksLeft;
	private int week;
	private boolean isNextWeek = false;
	private int villagerAdded = 0;
	private int villagerRemoved = 0;

	public MonthlyRitual getMonthlyRitual() {
		return monthlyRitual;
	}

	public void newMonthlyRitual() {
		monthlyRitual = new MonthlyRitual(4, 2 + week / 2, this);
	}

	public Village() {
		this.villagers = new ArrayList<Villager>();
		info = new VillageInformation(new Texture("villagers/info_menu.png"), 60, 10);
		food = 100;
		water = 100;
		hoursLeft = MAX_HOURS;
		weeksLeft = 5;
		week = 0;
	}

	public void setWeeksLeft(int weeks) {
		weeksLeft = weeks;
	}

	public boolean convertCitizen(VillagerRole role) {
		for (int i = 0; i < villagers.size(); i++) {
			System.out.println(villagers.get(i).getRole());
			if (villagers.get(i).getRole() == VillagerRole.CITIZEN) {
				villagers.get(i).setRole(role);
				effects.add(new VillagerEvolveEffect(villagers.get(i)));
				return true;
			}
		}
		return false;
	}

	public void consume(float delta) {
		float consumeFood = 0;
		float consumeWater = 0;
		for (Villager villager : villagers) {
			if (villager.getRole().equals(VillagerRole.FARMER)) {
				consumeFood += 1.05*(villager.getRole().foodConsumption());
				consumeWater += 1.10*(villager.getRole().waterConsumption());
			} else if (villager.getRole().equals(VillagerRole.EXPLORER)) {
				consumeFood += 1.15*(villager.getRole().foodConsumption()); 
				consumeWater += 1.20*(villager.getRole().waterConsumption());
			} else if (villager.getRole().equals(VillagerRole.MINER)) {
				consumeFood += 1.25*(villager.getRole().foodConsumption()); 
				consumeWater += 1.30*(villager.getRole().waterConsumption());
			} else {
			consumeFood += villager.getRole().foodConsumption();
			}
		}
		food -= consumeFood * delta;
		consumedFood += consumeFood * delta;

		for (Villager villager : villagers) {
			consumeWater += villager.getRole().foodConsumption();
		}
		water -= consumeWater * delta;
		consumedWater += consumeWater * delta;
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
		isNextWeek = false;
		consume(delta);
		info.setResources((int) food, (int) water, villagers.size(), (int) hoursLeft, (int) week, (int) weeksLeft);
		timePass(delta);
	}

	private void timePass(float delta) {
		hoursLeft -= (float) (delta * 1.4);
		if (hoursLeft <= 0) {
			weeksLeft -= 1;
			week += 1;
			hoursLeft = MAX_HOURS;
			isNextWeek = true;
			villagerAdded = 0;
		}
	}

	public boolean isNextWeek() {
		return isNextWeek;
	}

	public float getHoursLeft() {
		return hoursLeft;
	}

	public float getWeek() {
		return week;
	}

	public void render(Batch batch) {
		for (Villager villager : villagers) {
			villager.render(batch);
		}
		for (Villager villager : deadVillagers) {
			villager.render(batch);
		}
		for (VillagerEffect villagerEffect : effects) {
			villagerEffect.render(batch);
		}
		info.render(batch);

	}

	// return a position that does not overlap current villagers
	public Vector2 getEmptyPosition() {
		Rectangle newPosition = new Rectangle(randomX(), randomY(), 0, 0);
		Rectangle test; // position of the current villager
		Rectangle test2;// destination of the current villager
		Rectangle[] forbidden = new Rectangle[villagers.size() * 2];
		int count = 0;
		for (Villager villager : villagers) {
			newPosition.setWidth(villager.getWidth());
			newPosition.setHeight(villager.getHeight());
			test = new Rectangle(villager.getPosition().x, villager.getPosition().y, villager.getWidth(),
					villager.getHeight());
			forbidden[count] = test;
			count++;
			test2 = new Rectangle(villager.getDestination().x, villager.getDestination().y, villager.getWidth(),
					villager.getHeight());
			forbidden[count] = test2;
			count++;
		}
		for (int k = 0; k < forbidden.length; k++) {
			if (newPosition.overlaps(forbidden[k])) {
				newPosition.setPosition(randomX(), randomY());
			}
		}
		return new Vector2(newPosition.x, newPosition.y);
	}

	private int randomX() {
		Random random = new Random();
		return random.nextInt(680) + 10;
	}

	private int randomY() {
		Random random = new Random();
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
		return (int) food;
	}

	public int getWater() {
		return (int) water;
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
