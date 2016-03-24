package com.happylittlevillage.messages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.menu.MenuItem;


public abstract class MessageBox extends GameObject implements MenuItem {
	protected BitmapFont font;
	protected GameHandler gameHandler;
	protected String text;
	protected String title;
	protected Texture continueButton;
	protected Texture backButton;
	protected float continueX;
	protected float continueY;

	//exclusively for the tutorial
	public  MessageBox (GameHandler gameHandler){
		super(Assets.getTexture("ui/tutorialMessageBox.png"), 480,590);
		this.gameHandler = gameHandler;
	}
	// for the daily information
	public MessageBox(String instruction, GameHandler gameHandler) {
		super(Assets.getTexture("ui/parchment2.png"), 70, 160);
		continueButton = Assets.getTexture("ui/continue_button.png");
		this.gameHandler = gameHandler;
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
		font = Assets.getFont(36);
		batch.draw(texture, position.x, position.y);
		batch.draw(continueButton, continueX, continueY);
		if (text != null) {
			font.draw(batch, text, continueX - 180, continueY + 340);
		}
	}

	@Override
	public boolean interact(float mouseX, float mouseY) {
		System.out.println("interacted with continue button");
		Rectangle r = new Rectangle(continueX, continueY, continueButton.getWidth(), continueButton.getHeight());
		if (r.contains(mouseX, mouseY)) {
			gameHandler.unpause();
			return true;
		}
		return false;
	}
}
