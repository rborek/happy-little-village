package game.ritual.village;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import game.ritual.GameObject;

public class Villager extends GameObject {
	private VillagerRole role;
	private final static Texture[] villagerTextures = { new Texture("villagers/citizen.jpg") };
	private Village village;
	private Vector2 destination;
	private float speed = 5;
	private boolean rest;

	public Villager(VillagerRole role, Village village) {
		super(villagerTextures[role.ordinal()], 0, 0);
		this.village = village;
		position = village.getEmptyPosition();
		this.role = role;
		if (village.isEmpty()) {
			destination = new Vector2( 100,100);
		} else {
			destination = village.getEmptyPosition();
		}
	}
	
	
	private void move(float delta){
		int xDir = (int) Math.signum(destination.x- position.x);
		int yDir = (int) Math.signum(destination.y- position.y);	
		position.add(speed*delta*xDir,speed*delta*yDir);
	}
	
	
	@Override
	public void update(float delta) {
		destination = village.getEmptyPosition();
		move(delta);
	}

	public Vector2 getDestination() {
		return destination;
	}

}
