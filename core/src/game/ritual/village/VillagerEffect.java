package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.Assets;
import game.ritual.GameObject;

public abstract class VillagerEffect extends GameObject {
	Villager villager;
	Texture[] frames;
	protected float timer;
	protected boolean done = false;

	public VillagerEffect(Villager villager) {
		super(Assets.getTexture("villagers/evolve/evolve_sprite_1.png"), villager.getPosition().x, villager.getPosition().y);
		position = villager.getPosition();
		this.villager = villager;
		frames = getFrames();
	}

	public Villager getVillager() {
		return villager;
	}

	protected abstract Texture[] getFrames();

	@Override
	public void update(float delta) {
		int frame = (int) (timer * 10 % frames.length);
		texture = frames[frame];
		timer += delta;
		if (timer > frames.length / 10f) {
			done = true;
		}
	}

	public boolean isDone() {
		return done;
	}

}
