package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    /* TODO
     * member variables:
     * width
     * height
     * position (2d vector)
     * texture
     *
     * abstract methods:
     * update(float delta)
     *
     * methods:
     * getTexture()
     *
     *
     *
     */
	protected float height;
	protected Vector2 position;
	protected Texture text;
	protected float width;
	
	GameObject(Texture text, Vector2 position){
		this.text=text;
		this.position= position;
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
