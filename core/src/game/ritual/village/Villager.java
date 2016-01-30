package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;

import game.ritual.GameObject;

public class Villager extends GameObject {
	private VillagerRole role;
	private final static  Texture[] villagerTextures = {new Texture("villagers/citizen.jpg")};

	public Villager(VillagerRole role) {
		super(villagerTextures[role.ordinal()], 0, 0);
		this.role = role;

	}

	public void update(float delta) {

	}

}
