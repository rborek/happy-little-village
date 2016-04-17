package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.happylittlevillage.Assets;
import com.happylittlevillage.objects.GameObject;

public abstract class VillagerEffect extends GameObject {
	Villager villager;
	TextureRegion region;
	TextureRegion[] frames;
	protected final static TextureAtlas effectAtlas = Assets.getAtlas("villagers");
	protected float timer;
	protected boolean done = false;

	public VillagerEffect(Villager villager) {
		super(null, villager.getPosition().x, villager.getPosition().y);
		position = villager.getPosition();
		this.villager = villager;
		frames = getFrames();
		region = frames[0];
	}

	public Villager getVillager() {
		return villager;
	}

	protected abstract TextureRegion[] getFrames();

	@Override
	public void update(float delta) {
		int frame = (int) (timer * 10 % frames.length);
		region = frames[frame];
		timer += delta;
		if (timer > frames.length / 10f) {
			done = true;
		}
	}

	@Override
	public void render(Batch batch) {
		batch.draw(region, position.x, position.y);
	}

	public boolean isDone() {
		return done;
	}

}
