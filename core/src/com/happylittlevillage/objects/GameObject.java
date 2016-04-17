package com.happylittlevillage.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	public float height;
	public Vector2 position;
	public float width;
	protected Texture texture;

	public GameObject(Texture texture, float xPos, float yPos) {
		this.texture = texture;
		this.position = new Vector2(xPos, yPos);
		if (texture != null) {
			width = texture.getWidth();
			height = texture.getHeight();
		}
	}

	public GameObject(Texture texture, float xPos, float yPos, int width, int height) {
		this.texture = texture;
		this.position = new Vector2(xPos, yPos);
		this.width = width;
		this.height = height;
	}

	public void update(float delta) {

	}


	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y, width, height);
	}

	public void render(Batch batch, float textureX, float textureY) {
		batch.draw(texture, position.x, position.y, textureX, textureY);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void setPosition(float xPos, float yPos) {
		this.position = new Vector2(xPos, yPos);
	}

}
