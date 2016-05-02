package com.happylittlevillage.messages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.village.Village;

public class WeekSummary extends MessageBox {
	private Village village;
	private GemBag gemBag;
	private int timesPerformed;
	private int timesToDo;
	private Texture[][] gems;
	private GameObject blackGem = new GameObject(Assets.getTexture("gems/gem_black.png"), 610 + position.x, 690 + position.y);
	private GameObject foodIcon = new GameObject(Assets.getTexture("ui/food.png"),0,0);
	private GameObject waterIcon = new GameObject(Assets.getTexture("ui/water.png"),0,0);
	private GameObject happyIcon = new GameObject(Assets.getTexture("ui/happy_icon.png"),0,0);

	public int setNumOfBlackGem(int numOfBlackGem) {
		return this.numOfBlackGem = numOfBlackGem;
	}

	private int numOfBlackGem = 0;

	public WeekSummary(Village village, GemBag gembag, GameHandler gameHandler) {
		super("Today's Summary", gameHandler);
		this.village = village;
		this.gemBag = gembag;
		title = "Week Summary";
	}


	public boolean checkRitual() {
		if (village.getWeeklyRitual().isComplete()) {
			return true;
		} else {
			timesToDo = village.getWeeklyRitual().getTimesToDo();
			timesPerformed = village.getWeeklyRitual().getTimesPerformed();
			return false;
		}
	}

	public void stateRitual() {
		GemColour[][] colours = village.getWeeklyRitual().getRecipe();
		Texture[] textures = Gem.getArrayOfTextures();
		gems = new Texture[colours.length][colours[0].length];
		for (int i = 0; i < gems.length; i++) {
			for (int j = 0; j < gems[0].length; j++) {
				if (colours[i][j] != null) {
					System.out.println(colours[i][j]);
					gems[i][j] = textures[colours[i][j].ordinal()];
				}
			}
		}

	}


	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}


	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
		font = Assets.getFont(50);
		font.draw(batch, text, position.x + 460, position.y + 640);
		font = Assets.getFont(36);
		continueButton.render(batch);
		position.y -= 20;
		int alignY = 540;
		int alignX = 110;
		foodIcon.setPosition(position.x + alignX, position.y + alignY);
		foodIcon.render(batch);
		font.draw(batch, "Consumed: " + village.getConsumedFood(), position.x + alignX + 50, position.y + alignY+ 40);
		alignY -= 50;

		font.draw(batch, "Gathered: " + village.getGatheredFood(), position.x + alignX+ 50, position.y + alignY+ 40);
		foodIcon.setPosition(position.x + alignX, position.y + alignY);
		foodIcon.render(batch);
		alignY -= 50;

		font.draw(batch, "Total: " + village.getFood(), position.x + alignX + 50, position.y + alignY + 40);
		foodIcon.setPosition(position.x + alignX, position.y + alignY);
		foodIcon.render(batch);
		alignY -= 70;

		waterIcon.setPosition(position.x + alignX, position.y + alignY);
		waterIcon.render(batch);
		font.draw(batch, "Consumed: " + village.getConsumedWater(), position.x + alignX + 50, position.y + alignY+ 40);
		alignY -= 50;

		waterIcon.setPosition(position.x + alignX, position.y + alignY);
		waterIcon.render(batch);
		font.draw(batch, "Gathered: " + village.getGatheredWater(), position.x + alignX+ 50, position.y + alignY+ 40);
		alignY -= 50;

		waterIcon.setPosition(position.x + alignX, position.y + alignY);
		waterIcon.render(batch);
		font.draw(batch, "Total: " + village.getWater(), position.x + alignX+ 50, position.y + alignY+ 40);
		alignY -= 70;

		font.draw(batch, "Population: " + village.getPop(), position.x + alignX+ 50, position.y + alignY+ 50);
		position.y += 20;
//		font.draw(batch, "Villagers Created: "+ village.getVillagerAdded(), position.x+200, position.y+150);
//		font.draw(batch, "Villagers Lost: "+ (village.getPop()-village.getVillagerAdded()), position.x+200, position.y+110);

		//for the gems
		int alignY2 = 580;
		int alignX2 = 670;
		if (village.getBlackGem() != 0) {
			blackGem.setPosition(position.x + alignX2 + 450, position.y + alignY2 - 30 );
			blackGem.render(batch);
			font.draw(batch, "Congratulations! You have earned    " + numOfBlackGem, position.x + alignX2, position.y + alignY2);
		}

		//for the weekly Ritual
		int alignY3 = 310;
		font.draw(batch, "Weekly Ritual:",position.x  + alignX2,position.y + alignY3 + 220);
		for (int i = 0; i < gems.length; i++) {
			for (int j = 0; j < gems[0].length; j++) {
				if (gems[i][j] != null) batch.draw(gems[i][j], 720 + 64 * j, 430 - 64 * i, 64, 64);
			}
		}
		alignY3 -= 50;
		font.draw(batch, "Number of times to complete: " + village.getWeeklyRitual().getTimesLeftToDo(), position.x + alignX2, position.y + alignY3);
		alignY3 -= 50;
		font.draw(batch, "Days left to complete weekly ritual: " + village.getDaysLeft(), position.x + alignX2, position.y + alignY3);


	}

}
