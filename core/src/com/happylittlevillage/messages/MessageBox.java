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
	protected GameObject continueButton;
	protected Texture backButton;

	//exclusively for the tutorial
	public  MessageBox (GameHandler gameHandler){
		super(Assets.getTexture("ui/tutorialMessageBox.png"), 480,590);
		this.gameHandler = gameHandler;
	}
	// for the daily information
	public MessageBox(String instruction, GameHandler gameHandler) {
		super(Assets.getTexture("ui/parchment3.png"), 30, 15);
		continueButton = new GameObject(Assets.getTexture("ui/continue_button.png"), position.x + 1030, position.y + 5);
		this.gameHandler = gameHandler;
		this.text = instruction;
		title = "";
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Batch batch) {
		font = Assets.getFont(36);
		batch.draw(texture, position.x, position.y);
		continueButton.render(batch);
		if (text != null) {
			font.draw(batch, text, position.x + 220, position.y + 510);
		}
	}

	@Override
	public boolean interact(float mouseX, float mouseY) {
		Rectangle r = new Rectangle(continueButton.getPosition().x, continueButton.getPosition().y, continueButton.getWidth(), continueButton.getHeight());
		if (r.contains(mouseX, mouseY)) {
			System.out.println("interacted with continue button");
			gameHandler.unpauseInGame();
			return true;
		}
		return false;
	}
}
