package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import game.ritual.village.Village;
import game.ritual.village.VillageInformation;

public class MessageBox extends GameObject {
	protected BitmapFont font;
	protected String text;
	protected Texture continueButton = new Texture("scroll/toContinue.png");
	protected String clickToContinue = "Click to Continue";

	protected MessageBox(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		font = new BitmapFont();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		font = generator.generateFont(parameter);
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
<<<<<<< HEAD
		batch.draw(texture, position.x, position.y);
		batch.draw(continueButton, position.x+ 260, position.y+30);
		font.draw(batch, clickToContinue,  position.x+300, position.y + 60);
=======
		batch.draw(texture,position.x,position.y);
//		font.draw(batch, text, position.x+20, position.y+200);
//		font.draw(batch, "The amount of consumed food is:"+ village.getConsumedFood(), position.x+20, position.y+200);
		//font.draw(batch, "The amount of gathered food is:"+ village.getGatheredFood(), position.x+60, position.y+200);
		//font.draw(batch, "The remaining amount of food is:"+ village.getFood(), position.x+100, position.y+200);
	}
	public void setVillage(Village village2) {
		village = village2;
		System.out.println(village);
>>>>>>> a687d8b93893c8730d204f4a82b889bdfa87e048
	}

}
