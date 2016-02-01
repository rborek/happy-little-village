package game.ritual.village;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import game.ritual.GameObject;

public class VillageInformation extends GameObject {
	private BitmapFont resourceFont;
	private BitmapFont statusFont;
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
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		parameter.size = 36;
		resourceFont = generator.generateFont(parameter);
		parameter.size = 30;
		statusFont = generator.generateFont(parameter);
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
		resourceFont.draw(batch, "" + food, position.x + 80, position.y + 90);
		batch.draw(foodTexture, position.x + 20, 65);
		resourceFont.draw(batch, "" + water, position.x + 190, position.y + 90);
		batch.draw(waterTexture, position.x + 140, 70);
		statusFont.draw(batch, "Population: " + pop, position.x + 250 , position.y + 115);
		statusFont.draw(batch, "Hours: " + hour, position.x + 250, 80);
		statusFont.draw(batch, "Weeks elapsed: " + week, position.x + 405, 120);
		statusFont.draw(batch, "Weeks left: " + weeksLeft, position.x + 405, 80);
	}

}
