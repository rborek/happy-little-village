package com.happylittlevillage.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.objects.RotatableGameObject;
import com.happylittlevillage.rituals.DynamicRitual;
import com.happylittlevillage.Assets;

public class MiningWindow extends GameObject {
	private boolean show = false;
	private GameObject box = new GameObject(Assets.getTexture("ui/parchment2.png"), 340, 420, 250, 200);
	private GameObject continueButton = new GameObject(Assets.getTexture("ui/continue_button.png"), 400, 435, 100, 35);
	private GameObject chosenSign = new GameObject(Assets.getTexture("ui/chosen_sign.png"), 0, 0, 40, 40);
	private RotatableGameObject pickAxe = new RotatableGameObject(Assets.getTexture("ui/pick_axe.png"), position.x, position.y, 50, 50);
	private boolean[] mine = {false, false, false, false};
	private String text = "What gems would \nyou like to mine?";
	private BitmapFont font;
	private GemBag gemBag;
	private int count = 0; // number of activated gems
	private float angle = 0;

	public MiningWindow(GemBag gemBag, Texture texture, float xPos, float yPos, int width, int height) {
		super(texture, xPos, yPos, width, height);
		this.gemBag = gemBag;
	}

	public void interact(float x, float y) {
		Rectangle a = new Rectangle(0, 0, 0, 0);
		if (!show) { // open the window
			a.set(position.x, position.y, width, height);
			if (a.contains(x, y)) {
				show = true;
			}
		} else {
			a.set(continueButton.position.x, continueButton.position.y, continueButton.width, continueButton.height);
			if (a.contains(x, y)) { // close the window
				show = false;
				return;
			}
			for (int k = 0; k < 4; k++) { //chose gems to mine
				a.set(350 + k * 60, 475, 50, 50);
				if (a.contains(x, y)) {
					if (mine[k]) {
						mine[k] = false;
						count--;
					} else {
						mine[k] = true;
						count++;
					}
					setPercentage();
				}

			}
		}
	}

	private void setPercentage() {
		double[] probabilities = {0, 0, 0, 0};
		double extraProbability = 10;
		if (count == 0 || count == 4) {
			gemBag.setCumulativeProbabilities(probabilities);
		} else {
			for (int h = 0; h < mine.length; h++) {
				if (mine[h]) {
					probabilities[h] = extraProbability / (count);
				} else {
					probabilities[h] = -extraProbability / (mine.length - count);
				}
			}
			gemBag.setCumulativeProbabilities(probabilities);
		}
	}

	@Override
	public void render(Batch batch) {
		font = Assets.getFont(32);
		if (show) {
			box.render(batch); // the background theme
			continueButton.render(batch);
			font.draw(batch, text, 350, 600);

			for (int k = 0; k < 4; k++) {
				DynamicRitual.gemTextures[k].setPosition(350 + k * 60, 475);
				DynamicRitual.gemTextures[k].render(batch, 50, 50);
				if (mine[k]) {
					chosenSign.setPosition(375 + k * 60, 500);
					chosenSign.render(batch);
				}
			}
		} else {
			if (count == 0 || count == 4) {
				pickAxe.render(batch, 0, 0);
			} else {
				pickAxe.setAngle(30);
				pickAxe.render(batch, 0, 0);
			}

		}
	}

	@Override
	public void update(float delta) {
		angle += delta * 50;
		if (angle > 90) {
			angle = 0;
		}
		super.update(delta);
	}
}
