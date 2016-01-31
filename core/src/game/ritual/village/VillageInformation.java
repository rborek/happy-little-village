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
	private int hour;
	private int weeksLeft;
	private int week;
	// add file to constructors
	private Texture foodTexture = new Texture("scroll/food.png");
	private Texture waterTexture = new Texture("scroll/water.png");
	private Texture popTexture;

	protected VillageInformation(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		font = new BitmapFont();
	}

	public void setResources(int food, int water, int pop, int hour, int week, int weekLeft) {
		this.food = food;
		this.water = water;
		this.pop = pop;
		this.hour = hour;
		this.week = week;
		this.weeksLeft = weekLeft;
	}

	public int getFood() {
		return food;
	}

	public int getWater() {
		return water;
	}

	public int getPop() {
		return pop;
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
		font.draw(batch, "" + food, position.x + 80, position.y + 80);
		batch.draw(foodTexture, position.x + 20, 65);
		font.draw(batch, "" + water, position.x + 180, position.y + 80);
		batch.draw(waterTexture, position.x + 120, 70);
		font.draw(batch, "Population: " + pop, position.x + 280, position.y + 80);
		font.draw(batch, "Hours Left: " + hour, position.x + 400, 50);
		font.draw(batch, "Weeks So Far: " + week, position.x + 400, 90);
		font.draw(batch, "Weeks Left to Ritual: " + weeksLeft, position.x + 400, 130);
	}

}
