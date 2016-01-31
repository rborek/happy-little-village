package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import game.ritual.village.Village;
import game.ritual.village.VillageInformation;

public class MessageBox extends GameObject {
	private BitmapFont font;
	private VillageInformation info;

	private String text;
	private Village village;

	protected MessageBox(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		font = new BitmapFont();
		text = " this week you did this much";
	}
	// for the instruction
	protected MessageBox(Texture texture, float xPos, float yPos, String instruction) {
		super(texture, xPos, yPos);
		font = new BitmapFont();
		this.text = instruction;
	}


	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture,position.x,position.y);
		font.draw(batch, text, position.x+20, position.y+200);
		//font.draw(batch, "The amount of consumed food is:"+ village.getConsumedFood(), position.x+20, position.y+200);
		//font.draw(batch, "The amount of gathered food is:"+ village.getGatheredFood(), position.x+60, position.y+200);
		//font.draw(batch, "The remaining amount of food is:"+ village.getFood(), position.x+100, position.y+200);
	}
	public void setvillage(Village village2) {
		village = village2;
	}

}
