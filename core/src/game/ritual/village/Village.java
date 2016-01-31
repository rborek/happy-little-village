package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Village {
	private static final int MAX_HOURS = 168;
	private ArrayList<Villager> villagers;
	private float food = 0;
	private float consumedFood = 0;
	private float gatheredFood = 0;
	private float water = 0;
	private float consumedWater = 0;
	private VillageInformation info;
	private float hoursLeft;
	private float weekLeft;
	private float week;
	private boolean isNextWeek = false;

	public Village() {
		this.villagers = new ArrayList<Villager>();
		info = new VillageInformation(new Texture("villagers/info_menu.png"), 60, 10);
		food = 100;
		water = 100;
		hoursLeft = MAX_HOURS;
		weekLeft = 4;
		week = 0;
	}

	public boolean convertCitizen(VillagerRole role) {
		for (int k = 0; k < villagers.size(); k++) {
			if (villagers.get(k).getRole().equals(VillagerRole.CITIZEN)) {
				villagers.get(k).setRole(role);
				return true;
			}
		}
		return false;
	}

	public void consume(float delta) {
		float consumeFood = 0;
		float consumeWater = 0;
		for (Villager villager : villagers) {
			consumeFood += villager.getRole().foodConsumption();
		}
		food -= consumeFood * delta;
		consumedFood = consumeFood*delta;

		for (Villager villager : villagers) {
			consumeWater += villager.getRole().foodConsumption();
		}
		water -= consumeWater * delta;
		consumedWater = consumeWater*delta;
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
				food += 20;
			}
		}
		addFood(food);
		gatheredFood = food;
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
		isNextWeek = false;
		consume(delta);
		info.setResources((int) food, (int) water, villagers.size(), (int) hoursLeft, (int) week, (int) weekLeft);
		timePass(delta);
	}

	private void timePass(float delta) {
		hoursLeft -= (float) (delta * 1.4);
		if (hoursLeft <= 0) {
			weekLeft -= 1;
			week += 1;
			hoursLeft = MAX_HOURS;
			isNextWeek = true;
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
	}

	public void removeVillager(Villager a) {
		villagers.remove(a);
	}

	public Villager getVillager(int a) {
		return villagers.get(a);
	}

	public int getSize() {
		return villagers.size();
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

	public int getGatheredFood(){
		return (int) gatheredFood;
	}
	public int getWater() {
		return (int) water;
	}

	public int getPop() {
		return villagers.size();
	}
	public int getConsumedFood(){
		return (int) consumedFood;
	}
	public int getConsumedWater(){
		return (int) consumedWater;
	}
}
