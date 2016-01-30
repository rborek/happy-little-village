package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	protected float height;
	protected Vector2 position;
	protected Texture text;
	protected float width;
	SpriteBatch batch;
	
	protected GameObject(Texture text, float xPos, float yPos){
		this.text = text;
		this.position = new Vector2(xPos, yPos);
        width = text.getWidth();
        height = text.getHeight();
	}
	
	public abstract void update(float delta);

	public void render(Batch batch) {
		batch.draw(text, position.x, position.y, width, height);
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

	public Texture getText() {
		return text;
	}

	
	
	
}
