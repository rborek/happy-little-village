package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;

import game.ritual.GameObject;

public class Villager extends GameObject {
	private VillagerRole role;
	private Texture text;

	public Villager(VillagerRole role, Texture text) {
		super(text, 0, 0);
		this.role = role;
		this.text = text;
	}

	public void update(float delta) {

	}

}
