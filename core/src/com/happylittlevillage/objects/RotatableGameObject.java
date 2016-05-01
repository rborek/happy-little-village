package com.happylittlevillage.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class RotatableGameObject extends GameObject {
	private float angle = 0;

	public RotatableGameObject(Texture texture, float xPos, float yPos, float angle) {
		super(texture, xPos, yPos);
		this.angle = angle;

	}

	public RotatableGameObject(Texture texture, float xPos, float yPos, int textureX, int textureY) {
		super(texture, xPos, yPos, textureX, textureY);
	}


	public RotatableGameObject(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
	}

	@Override
	public void render(Batch batch) {
		if (angle == 0) {
			batch.draw(texture, position.x, position.y);
		} else {
			batch.draw(texture, position.x, position.y, texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth(), texture.getHeight(), 1, 1, angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
		}
	}

	@Override
	public void render(Batch batch, float textureX, float textureY) {
		if (angle == 0) {
			batch.draw(texture, position.x, position.y, width, height);
		} else {
			batch.draw(texture, position.x, position.y, texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth(), texture.getHeight(), 1, 1, angle, 0, 0, (int) texture.getWidth(), (int) texture.getHeight(), false, false);
		}
	}

	@Override
	public void update(float delta) {

	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setPosition(float xPos, float yPos) {
		position.x = xPos;
		position.y = yPos;
	}
}
