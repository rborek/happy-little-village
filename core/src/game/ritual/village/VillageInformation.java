package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import game.ritual.GameObject;

public class VillageInformation extends GameObject {
	private BitmapFont font;
	private int food;
	private int water;
	private int pop;
	private int week;
	private int weekLeft;
	// add file to constructors
	private Texture foodTexture;
	private Texture waterTexture;
	private Texture popTexture;
	

	protected VillageInformation(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		font = new BitmapFont();
	}

	
	public void setResources(int food, int water, int pop, int week, int weekLeft){
		this.food = food;
		this.water = water;
		this.pop = pop;
	}

	public int getFood(){
		return food;
	}
	public int getWater(){
		return water;
	}
	public int getPop(){
		return pop;
	}
	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
		font.draw(batch, "Food: " + food, 200, 50);
		font.draw(batch, "Water: " + water, 300, 50);
		font.draw(batch, "Population: " + pop, 400, 50);
	}

}
