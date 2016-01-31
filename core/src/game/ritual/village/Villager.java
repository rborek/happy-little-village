package game.ritual.village;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import game.ritual.GameObject;

public class Villager extends GameObject {
	private VillagerRole role;
	private final static Texture[] villagerTextures = { new Texture("villagers/citizen/citizen.png") };
	private Village village;
	private Vector2 destination;
	private float speed = 120; // magnitude of the villager
	private Vector2 velocity; // velocity of the villager
	private float restTimer = 2;

	// subtract delta from restTimer
	public Villager(VillagerRole role, Village village) {
		super(villagerTextures[role.ordinal()], 0, 0);
		this.village = village;
		position = village.getEmptyPosition();
		this.role = role;
		if (village.isEmpty()) {
			destination = new Vector2(100, 100);
		} else {
			destination = village.getEmptyPosition();
		}
		velocity = new Vector2(0, 0);
	}

	private void move(float delta) {
		float x = destination.x - position.x;
		float y = destination.y - position.y;
		float ratio = Math.abs(x / y);
		// next 2 lines set the velocity so that the magnitude stay constant;
		velocity.y = (float) Math.sqrt(speed * speed / (1 + ratio) / (1 + ratio));
		velocity.x = velocity.y * ratio;
		int xDir = (int) Math.signum(x);
		int yDir = (int) Math.signum(y);
		position.add(velocity.x * delta * xDir, velocity.y * delta * yDir);
	}

	private boolean arrive() {
		return Math.abs(position.x - destination.x) < 10 && Math.abs(position.y - destination.y) < 10;
	}

	@Override
	public void update(float delta) {
		if (arrive()) {
			restTimer -= delta;
			if (restTimer <= 0) {
				destination = village.getEmptyPosition();
				Random r = new Random();
				restTimer = r.nextFloat() * 3 + 1;
			}

		} else {
			move(delta);
		}

	}

	public Vector2 getDestination() {
		return destination;
	}
	public VillagerRole getRole(){
		return role;
	}
}
