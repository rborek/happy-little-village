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
	private GameObject blackGem = new GameObject(Assets.getTexture("gems/gem_black.png"), 620 + position.x, 670 + position.y);

	public int setNumOfBlackGem(int numOfBlackGem) {
		return this.numOfBlackGem = numOfBlackGem;
	}

	private int numOfBlackGem = 0;

	public WeekSummary(Village village, GemBag gembag, GameHandler gameHandler) {
		super("", gameHandler);
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
		super.render(batch);
		//batch.draw(texture,position.x,position.y);
		position.y -= 20;
		int alignY = 600;
		int alignX = 80;
		font.draw(batch, "Food Consumed: " + village.getConsumedFood(), position.x + alignX, position.y + alignY);
		alignY -= 40;
		font.draw(batch, "Food Gathered: " + village.getGatheredFood(), position.x + alignX, position.y + alignY);
		alignY -= 40;
		font.draw(batch, "Food Total: " + village.getFood(), position.x + alignX, position.y + alignY);
		alignY -= 40;
		font.draw(batch, "Water Consumed: " + village.getConsumedWater(), position.x + alignX, position.y + alignY);
		alignY -= 40;
		font.draw(batch, "Water Gathered: " + village.getGatheredWater(), position.x + alignX, position.y + alignY);
		alignY -= 40;
		font.draw(batch, "Water Total: " + village.getWater(), position.x + alignX, position.y + alignY);
		alignY -= 40;
		font.draw(batch, "Population: " + village.getPop(), position.x + alignX, position.y + alignY);
		position.y += 20;
//		font.draw(batch, "Villagers Created: "+ village.getVillagerAdded(), position.x+200, position.y+150);
//		font.draw(batch, "Villagers Lost: "+ (village.getPop()-village.getVillagerAdded()), position.x+200, position.y+110);

		//for the gems
		int alignY2 = 620;
		int alignX2 = 670;
		if (village.getBlackGem() != 0) {
			blackGem.setPosition(position.x + alignX2 + 400, position.y + alignY2 - 50 );
			blackGem.render(batch);
			font.draw(batch, "Congratulations! You have earned " + numOfBlackGem, position.x + alignX2 + 20, position.y + alignY2);
		}
//		alignY2 -= 40;
//		font.draw(batch, "Red gems mined  " + village.getMinedGems()[0], position.x + alignX2, position.y + alignY2);
//		alignY2 -= 40;
//		font.draw(batch, "Yellow gems mined  " + village.getMinedGems()[1], position.x + alignX2, position.y + alignY2);
//		alignY2 -= 40;
//		font.draw(batch, "Green gems mined  " + village.getMinedGems()[2], position.x + alignX2, position.y + alignY2);
//		alignY2 -= 40;
//		font.draw(batch, "Blue gems mined  " + village.getMinedGems()[3], position.x + alignX2, position.y + alignY2);

		//for the weekly Ritual
		int alignY3 = 200;
//        font.draw(batch, text, position.x + alignX2, position.y + alignY3);
		for (int i = 0; i < gems.length; i++) {
			for (int j = 0; j < gems[0].length; j++) {
				if (gems[i][j] != null) batch.draw(gems[i][j], 720 + 64 * j, 350 - 64 * i, 64, 64);
			}
		}
		alignY3 -= 50;
		font.draw(batch, "Number of times to complete: " + village.getWeeklyRitual().getTimesLeftToDo(), position.x + alignX2, position.y + alignY3);
		alignY3 -= 50;
		font.draw(batch, "Days left to complete weekly ritual: " + village.getDaysLeft(), position.x + alignX2, position.y + alignY3);


	}

}
