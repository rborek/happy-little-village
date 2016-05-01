package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.Village;
import com.happylittlevillage.village.VillageInformation;


public class DynamicRitual {

	private Ritual ritual;
	private Rectangle[][] recipePositions;
	private static int spaceBetweenGems = 32;
	private static int gemSize = 32;
	private static int startY = 190;
	private static int startX = 630;
	private static int spaceX = 150;
	private int index;
	private float posX = 0;
	public static final double SLIDING_SPEED = 5;
	public static GameObject frame = new GameObject(Assets.getTexture("ui/frame_converted.png"), 0, 0, 140, 222);
	public static GameObject weeklyFrame = new GameObject(Assets.getTexture("ui/frame_weekly.png"), 0, 0, 140, 222);
	public static GameObject[] gemTextures = { // red blue green yellow
			new GameObject(Gem.getArrayOfTextures()[0], 0, 0, gemSize, gemSize),
			new GameObject(Gem.getArrayOfTextures()[1], 0, 0, gemSize, gemSize),
			new GameObject(Gem.getArrayOfTextures()[2], 0, 0, gemSize, gemSize),
			new GameObject(Gem.getArrayOfTextures()[3], 0, 0, gemSize, gemSize),};
	public static GameObject foodIcon = new GameObject(Assets.getTexture("ui/food.png"), 0, 0, 35, 35);
	public static GameObject waterIcon = new GameObject(Assets.getTexture("ui/water.png"), 0, 0, 35, 35);
	public static GameObject happyIcon = new GameObject(Assets.getTexture("ui/happy_icon.png"), 0, 0, 32, 32);

	public DynamicRitual(Ritual ritual) {
		this.ritual = ritual;
		recipePositions = new Rectangle[ritual.getRecipe().length][ritual.getRecipe()[0].length];
		movePosition(posX);
	}

	public void render(Batch batch, BitmapFont font, Village village) { // TODO it is a shame we have to pass village in just for the weekly ritual but time does not permit to optimize everything
		renderRitual(batch, font, this, startY, gemSize, spaceBetweenGems, posX, village);

	}

	// rendering moving Ritual
	private void renderRitual(Batch batch, BitmapFont font, DynamicRitual dynamicRitual, float startY, int gemSize, int spaceBetweenGems, float posX, Village village) {
		for (int k = 0; k < dynamicRitual.getRitual().getRecipe().length; k++) {
			for (int h = 0; h < dynamicRitual.getRitual().getRecipe()[0].length; h++) {
				if (dynamicRitual.getRitual().getRecipe()[k][h] != null) {
					if (dynamicRitual.getRitual().getRecipe()[k][h].equals(GemColour.BLUE)) {
						gemTextures[1].setPosition(dynamicRitual.getRecipePositions()[k][h].x, startY - k * spaceBetweenGems);
						gemTextures[1].render(batch, gemSize, gemSize);
					} else if (dynamicRitual.getRitual().getRecipe()[k][h].equals(GemColour.GREEN)) {
						gemTextures[2].setPosition(dynamicRitual.getRecipePositions()[k][h].x, startY - k * spaceBetweenGems);
						gemTextures[2].render(batch, gemSize, gemSize);
					} else if (dynamicRitual.getRitual().getRecipe()[k][h].equals(GemColour.YELLOW)) {
						gemTextures[3].setPosition(dynamicRitual.getRecipePositions()[k][h].x, startY - k * spaceBetweenGems);
						gemTextures[3].render(batch, gemSize, gemSize);
					} else if (dynamicRitual.getRitual().getRecipe()[k][h].equals(GemColour.RED)) {
						gemTextures[0].setPosition(dynamicRitual.getRecipePositions()[k][h].x, startY - k * spaceBetweenGems);
						gemTextures[0].render(batch, gemSize, gemSize);
					}
				}
			}
		}
		if (dynamicRitual.getRitual().getEffects().length == 0) { // weekly ritual
			font.draw(batch, "Days left: " + village.getDaysLeft(), posX, startY - 100);
			if (village.getWeeklyRitual().getRecipe() != null) {
				font.draw(batch, "Times to do: " + village.getWeeklyRitual().getTimesLeftToDo(), posX, startY - 130);
			}
			weeklyFrame.setPosition(posX - 3, 5);
			weeklyFrame.render(batch);
		} else { // normal ritual
			for (int k = 0; k < dynamicRitual.getRitual().getEffects().length; k++) {// render effects
				float alignY = startY - 130 - k * 27; // align for the icons
				String modifierName = dynamicRitual.getRitual().getEffects()[k].getModifier().name();
				if (modifierName.equals("FOOD")) {
					foodIcon.setPosition(posX, alignY);
					foodIcon.render(batch);
				} else if (modifierName.equals("WATER")) {
					waterIcon.setPosition(posX, alignY);
					waterIcon.render(batch);
				} else if (modifierName.equals("HAPPINESS")) {
					happyIcon.setPosition(posX, alignY);
					happyIcon.render(batch);
				} else if (modifierName.equals("VILLAGER")) {
					batch.draw(VillageInformation.villagers.get(0), posX, alignY, 28, 35);
				} else if (modifierName.equals("FARMER")) {
					batch.draw(VillageInformation.villagers.get(1), posX, alignY, 28, 35);
				} else if (modifierName.equals("EXPLORER")) {
					batch.draw(VillageInformation.villagers.get(2), posX, alignY, 28, 35);
				} else if (modifierName.equals("MINER")) {
					batch.draw(VillageInformation.villagers.get(3), posX, alignY, 28, 35);
				}
				if (dynamicRitual.getRitual().getEffects()[k].getAmount() > 0) {
					font.draw(batch, " +" + dynamicRitual.getRitual().getEffects()[k].getAmount(), posX + 30, startY - 100 - k * 27);
				} else {
					font.draw(batch, " " + dynamicRitual.getRitual().getEffects()[k].getAmount(), posX + 30, startY - 100 - k * 27);
				}
			}
			frame.setPosition(posX - 3, 5);
			frame.render(batch);
		}

	}

	//this update the initial position before sliding
	public void update(float delta, int index) {
		if (posX != spaceX * (index) + startX) {
			posX = spaceX * (index) + startX;
			movePosition(posX);
		}
	}

	public void updateMovement(float delta, int sign) {
		//index is the relative position of the ritual
		//sign determines if the ritual goes left or right
		//int indexX = 650 + index * 200 ;
		posX -= 200 * ((float) delta) * sign * SLIDING_SPEED;
		movePosition(posX);
	}

	//this update the position of a ritual when it is slided or first initialized
	private void movePosition(float startX) {
		for (int k = 0; k < ritual.getRecipe().length; k++) {
			for (int h = 0; h < ritual.getRecipe()[0].length; h++) {
				if (ritual.getRecipe()[k][h] != null) {
					//avoid creating new Rectangle
					if (recipePositions[k][h] != null) {
						recipePositions[k][h].setX(posX + h * spaceBetweenGems);
					} else {
						recipePositions[k][h] = new Rectangle(posX + h * spaceBetweenGems,
								startY - k * spaceBetweenGems, gemSize, gemSize);
					}
				}
			}
		}
	}

	public void Slide(int startX) {
		movePosition(startX);
	}

	public Rectangle[][] getRecipePositions() {
		return recipePositions;
	}

	public Ritual getRitual() {
		return ritual;
	}

	public Rectangle getRectanglePosition(){
		return new Rectangle(posX - 3, 5, weeklyFrame.getWidth() ,weeklyFrame.getHeight());
	}
}
