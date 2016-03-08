package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.Line;

import java.util.ArrayList;
import java.util.Random;

public class Villager extends GameObject implements Comparable<Villager> {
	private VillagerRole role;
	private TextureRegion region;
	private final static TextureAtlas villagerAtlas = Assets.getAtlas("villagers");
	private final static TextureRegion[][] idleTextures = {villagerAtlas.findRegions("citizen").toArray(TextureRegion.class),
			villagerAtlas.findRegions("miner").toArray(TextureRegion.class), villagerAtlas.findRegions("farmer").toArray(TextureRegion.class),
			villagerAtlas.findRegions("explorer").toArray(TextureRegion.class)};
	private final static TextureRegion[][] walkTextures = {villagerAtlas.findRegions("citizen_walk").toArray(TextureRegion.class),
			villagerAtlas.findRegions("miner_walk").toArray(TextureRegion.class), villagerAtlas.findRegions("farmer_walk").toArray(TextureRegion.class),
			villagerAtlas.findRegions("explorer_walk").toArray(TextureRegion.class)};
	private static Pool<Line> linePool = new Pool<Line>() {
		@Override
		protected Line newObject() {
			return new Line();
		}
	};

	private static ArrayList<Line> obstacles = new ArrayList<Line>() {
		{
			// house
			add(new Line(55, 285, 22, 379));
			add(new Line(22, 379, 62, 447));
			add(new Line(62, 447, 124, 483));
			add(new Line(124, 483, 248, 429));
			add(new Line(248, 429, 257, 338));
			add(new Line(257, 338, 164, 278));
			add(new Line(164, 278, 55, 285));
			// fences
			add(new Line(370, 270, 387, 334));
			add(new Line(387, 334, 441, 370));
			add(new Line(441, 370, 602, 370));
			add(new Line(602, 370, 625, 270));
			add(new Line(625, 270, 370, 270));
			// mine
			add(new Line(299, 423, 584, 419));
			add(new Line(584, 419, 596, 460));
			add(new Line(596, 460, 472, 527));
			add(new Line(472, 527, 327, 530));
			add(new Line(327, 530, 299, 480));
			add(new Line(299, 480, 299, 423));
		}
	};
	private Village village;
	private Vector2 destination;
	private float speed = 120; // magnitude of the villager
	private Vector2 velocity; // velocity of the villager
	private float restTimer = getNewRestDuration();
	private boolean resting = false;
	private float walkTimer = 0;


	// subtract delta from restTimer
	public Villager(VillagerRole role, Village village) {
		super(null, 0, 0, 28, 42);
		this.village = village;
		position = new Vector2(216, 300);
		this.role = role;
		velocity = new Vector2(0, 0);
		region = idleTextures[role.ordinal()][0];
		generateNewDestination();
	}

	public static void renderLines(ShapeRenderer renderer) {
		for (Line line : obstacles) {
			line.render(renderer);
		}
	}

	public boolean isMovingRight() {
		return position.x < destination.x;
	}

	private void calculateVelocity() {
		float x = destination.x - position.x;
		float y = destination.y - position.y;
		float ratio = Math.abs(x / y);
		// next 2 lines set the velocity so that the magnitude stay constant;
		velocity.y = (float) Math.sqrt(speed * speed / (1 + ratio) / (1 + ratio));
		velocity.x = velocity.y * ratio;
		int xDir = (int) Math.signum(x);
		int yDir = (int) Math.signum(y);
		velocity.x *= xDir;
		velocity.y *= yDir;
	}

	private void move(float delta) {
		int frame = (int) (walkTimer * 5 % 4);
		if (frame == 3) {
			region = walkTextures[role.ordinal()][frame - 2];
		} else {
			region = walkTextures[role.ordinal()][frame];
		}
		walkTimer += delta;
		position.add(velocity.x * delta, velocity.y * delta);
	}

	private boolean arrivedAtDestination() {
		return (Math.abs(position.x - destination.x) < 10 && Math.abs(position.y - destination.y) < 10);
	}

	@Override
	public void render(Batch batch) {
		batch.draw(region, isMovingRight() ? position.x + width : position.x, position.y, 0, 0, width, height, isMovingRight() ? -1 : 1, 1, 0);
	}

	@Override
	public void update(float delta) {
		if (resting) {
			region = idleTextures[role.ordinal()][0];
			restTimer -= delta;
			if (restTimer <= 0) {
				generateNewDestination();
				restTimer = getNewRestDuration();
				resting = false;
			}
		} else {
			move(delta);
			if (arrivedAtDestination()) {
				resting = true;
			}
		}
	}

	private float getNewRestDuration() {
		Random r = new Random();
		return r.nextFloat() * 4;
	}

	public void generateNewDestination() {
		destination = village.getEmptyPosition();
		Line path = linePool.obtain();
		path.set(position, destination);
		for (int i = 0; i < obstacles.size(); i++) { //check if it intersects with any bounding lines
			if (path.intersects(obstacles.get(i))) {
				destination = village.getEmptyPosition();
				path.set(position, destination);
				i = -1;
			}
		}
		linePool.free(path);
		calculateVelocity();
	}

	public Vector2 getDestination() {
		return destination;
	}

	public VillagerRole getRole() {
		return role;
	}

	public void setRole(VillagerRole a) {
		role = a;
	}

	@Override
	public int compareTo(Villager other) {
		if (this.position.y > other.position.y) {
			return -1;
		} else if (this.position.y == other.position.y) {
			return 0;
		} else {
			return 1;
		}
	}

}
