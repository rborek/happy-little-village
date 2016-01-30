package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	protected float height;
	protected Vector2 position;
	protected Texture text;
	protected float width;
	
	GameObject(Texture text, float xPos, float yPos){
		this.text = text;
		this.position = new Vector2(xPos, yPos);
        width = text.getWidth();
        height = text.getHeight();
	}
	
	public abstract void update(float delta);
	
	
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
