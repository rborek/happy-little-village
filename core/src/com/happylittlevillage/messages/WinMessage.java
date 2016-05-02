package com.happylittlevillage.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.HappyLittleVillage;

public class WinMessage extends MessageBox {
	private String message;
	private String[] condition;
	HappyLittleVillage happyLittleVillage;


	public WinMessage(GameHandler gameHandler, HappyLittleVillage happyLittleVillage) {
		// TODO Duke - don't worry about this
		super("", gameHandler);
		this.happyLittleVillage = happyLittleVillage;
		message = "";
		condition = new String[3];
		condition[0] = "Enough food to survive, your \nvillagers part away with their food";
		condition[1] = "Enough villagers to create a tribe";
		condition[2] = "Enough happiness to survive, your vilagers part away with their happiness wtf";
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 36;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		font = generator.generateFont(parameter);
	}

	public void setCondition(int a) {
		message = condition[a];
	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, title, position.x + 70, position.y + 250);
		font.draw(batch, message, position.x + 70, position.y + 280);
		font.draw(batch, "YOU WIN!", position.x + 70, position.y + 230);
	}

	@Override
	public boolean interact(float mouseX, float mouseY) {
		Rectangle r = new Rectangle(continueButton.getPosition().x, continueButton.getPosition().y, continueButton.getWidth(), continueButton.getHeight());
		if (r.contains(mouseX, mouseY)) {
			System.out.println("interacted with continue button");
			happyLittleVillage.setMenu();
			return true;
		}
		return false;
	}
}
