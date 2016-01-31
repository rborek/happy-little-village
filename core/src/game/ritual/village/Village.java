package game.ritual.village;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Village {
	private ArrayList<Villager> villagers;
	private float food;
	private float water;

	public void consume(float delta) {
		float consumeFood = 0;
		float consumeWater = 0;
		for (Villager villager : villagers) {
			consumeFood += villager.getRole().foodConsumption();
		}
		food -= consumeFood * delta;

		for (Villager villager : villagers) {
			consumeWater += villager.getRole().foodConsumption();
		}
		water -= consumeWater * delta;
	}

	public void addFood(float x) {
		food += x;
	}

	public void addWater(float y) {
		water += y;
	}

	public Village() {
		this.villagers = new ArrayList<Villager>();
	}

	public void update(float delta) {
		for (Villager villager : villagers) {
			villager.update(delta);
		}
		consume(delta);

	}

	private void updateResources(float delta) {
		// TODO
		// update resources here (e.g. food being eaten)
	}

	public void render(Batch batch) {
		for (Villager villager : villagers) {
			villager.render(batch);
		}
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
		return random.nextInt(640 - 32);
	}

	private int randomY() {
		Random random = new Random();
		return random.nextInt(720 - 48);
	}

	public void addVillager(Villager a) {
		villagers.add(a);
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
}
