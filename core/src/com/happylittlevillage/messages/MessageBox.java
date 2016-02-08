package com.happylittlevillage.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.menu.MenuItem;


public abstract class MessageBox extends GameObject implements MenuItem {
	protected GameHandler gameHandler;
	protected BitmapFont font;
	protected String text;
	protected String title;
	protected Texture continueButton = Assets.getTexture("ui/continue_button.png");
	protected float continueX;
	protected float continueY;

	// for the instruction
	public MessageBox(String instruction, GameHandler gameHandler) {
		super(Assets.getTexture("ui/parchment.png"), 70, 160);
		this.gameHandler = gameHandler;
		font = new BitmapFont();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 36;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		parameter.genMipMaps = true;
		parameter.magFilter = Texture.TextureFilter.MipMapLinearLinear;
		parameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
		font = generator.generateFont(parameter);
		this.text = instruction;
		title = "";
		setButtonPos();
	}

	private void setButtonPos() {
		continueX = position.x + 250;
		continueY = position.y + 45;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
		batch.draw(continueButton, continueX, continueY);
		if (text != null) {
			font.draw(batch, text, continueX - 180, continueY + 340);
		}
	}

	@Override
	public boolean interact(float mouseX, float mouseY) {
		Rectangle r = new Rectangle(continueX, continueY, continueButton.getWidth(), continueButton.getHeight());
		if (r.contains(mouseX, mouseY)) {
			gameHandler.unpause();
			return true;
		}
		return false;
	}
}
