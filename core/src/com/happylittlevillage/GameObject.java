package com.happylittlevillage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	protected float height;
	protected Vector2 position;
	protected float width;
	protected Texture texture;

	protected GameObject(Texture texture, float xPos, float yPos) {
		this.texture = texture;
		this.texture.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.MipMapLinearLinear);
		this.position = new Vector2(xPos, yPos);
		width = texture.getWidth();
		height = texture.getHeight();
	}

	protected GameObject(Texture texture, float xPos, float yPos, int width, int height) {
		this(texture, xPos, yPos);
		this.width = width;
		this.height = height;
	}

	public abstract void update(float delta);

	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y, width, height);
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


}
