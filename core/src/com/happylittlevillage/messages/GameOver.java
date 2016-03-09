package com.happylittlevillage.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.HappyLittleVillage;

public class GameOver extends MessageBox {
	private String message;
	private static String[] condition = new String[]{
			"All villagers died"};
	private BitmapFont gameOverFont;
	HappyLittleVillage happyLittleVillage;


	public GameOver(GameHandler gameHandler, HappyLittleVillage happyLittleVillage) {
		super("", gameHandler);
		this.happyLittleVillage = happyLittleVillage;
		title = " GAME OVER ";
		message = "";
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
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
	}

	@Override
	public boolean interact(float mouseX, float mouseY) {
		Rectangle r = new Rectangle(continueX, continueY, continueButton.getWidth(), continueButton.getHeight());
		if (r.contains(mouseX, mouseY)) {
			System.out.println("GOTO MENU ");
			happyLittleVillage.setMenu();
			return true;
		}
		return false;
	}

}
