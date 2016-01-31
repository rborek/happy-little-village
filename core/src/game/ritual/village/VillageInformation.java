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

	protected VillageInformation(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		font = new BitmapFont();
		
	}

	public void setFood(int food) {
		this.food = food;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
		font.draw(batch, "Food: " + food, 200, 200);
		font.draw(batch, "Water:" + water, 300, 200);
		font.draw(batch, "Pop" + pop, 400, 200);
	}

}
