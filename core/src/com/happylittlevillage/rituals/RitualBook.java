package com.happylittlevillage.rituals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.screens.GameScreen;
import com.happylittlevillage.Assets;
import com.happylittlevillage.village.Village;

import java.util.ArrayList;

public class RitualBook extends GameObject {
	private int firstIndex = 0;
	private int slidingIndex = 0;
	private int count = 0;
	private Rectangle leftArrow;
	private Rectangle rightArrow;
	//TODO all the recipes, recipePositions, names, effects need to be called from RitualTree
	private ArrayList<DynamicRitual> dynamicRituals = new ArrayList<DynamicRitual>();
	private BitmapFont font;
	private boolean isMoving = false;
	private boolean updateInitialPositions = false;
	private boolean slideLeft = false;
	private boolean slideRight = false;
	private float slideTime = 0;
	private RitualTree ritualTree;
	private int lastIndex = 0;
	private Village village;


	public RitualBook(RitualTree ritualTree, float xPos, float yPos, Village village) {
		super(Assets.getTexture("ui/parchment2.png"), xPos, yPos, 700, 250);
		this.ritualTree = ritualTree;
		this.village = village;
		setWeeklyChosenRitual();
	}

	// to be called every week after pausing the game to update available rituals
	public void setWeeklyChosenRitual() {
		dynamicRituals.clear(); //clear out all the content before adding new rituals
		count = 0;
		for (Ritual ritual : ritualTree.getChosenRituals()) {
			if (ritual != null) {
				dynamicRituals.add(new DynamicRitual(ritual));
				count++;
			}
		}
	}

	public void moveRitual(boolean isLeft, float deltaX) {
		if (!isMoving) {
			if (!isLeft) {
				previous();
				slideRight = true;
			} else {
				next();
				slideLeft = true;
			}
		}
	}

	public void previous() {
		isMoving = true;
		firstIndex--;
		if (firstIndex < 0) {
			firstIndex = count - 1;
		}
		System.out.println("previous");
	}

	public void next() {
		isMoving = true;
		firstIndex++;
		if (firstIndex >= count) {
			firstIndex = 0;
		}
		System.out.println("next");
	}

	private void enableScissor(float clipX, float clipY, float clipWidth, float clipHeight) {
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
		// need to use aspect ratio to properly scissor at resolutions other than the virtual resolution
		Gdx.gl.glScissor((int) ((clipX) * (Gdx.graphics.getWidth() / (double) GameScreen.WIDTH)),
				(int) ((clipY) * (Gdx.graphics.getHeight() / (double) GameScreen.HEIGHT)),
				(int) ((clipWidth) * (Gdx.graphics.getWidth() / (double) GameScreen.WIDTH)),
				(int) ((clipHeight) * (Gdx.graphics.getHeight() / (double) GameScreen.HEIGHT)));
	}

	private void disableScissor() {
		Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
	}

	// to pick up ritual
	public Gem[][] getRitualRecipe(float x, float y) {
		// firstIndex this number restrict the 4 situations being tested on a page
		// only 4 ritualPosition are being tested from index to index +3\
		if (isMoving) {
			return null;
		} else {
			for (int k = firstIndex; k < lastIndex; k++) {
				int index = k % count;
//            System.out.println(index + " " + firstIndex);
				// check for one specific ritual recipe position in the arrayList of recipePositions
				for (int i = 0; i < dynamicRituals.get(index).getRitual().getRecipe().length; i++) {
					for (int h = 0; h < dynamicRituals.get(index).getRitual().getRecipe()[0].length; h++) {
						//if there is a gem in the position
						if (dynamicRituals.get(index).getRitual().getRecipe()[i][h] != null) {
							if (dynamicRituals.get(index).getRecipePositions()[i][h].contains(x, y)) { // if the recipe position contains the mouse
								return getPickUpRitual(index);
							}
						}
					}
				}
			}
		}
		return null;
	}

	// return the recipe of the matched ritual
	private Gem[][] getPickUpRitual(int index) {
		//pickUpRitual has the size of the recipe
		Gem[][] pickUpRitual = new Gem[dynamicRituals.get(index).getRitual().getRecipe().length][dynamicRituals.get(index).getRitual().getRecipe()[0].length];
		for (int i = 0; i < dynamicRituals.get(index).getRitual().getRecipe().length; i++) {
			for (int h = 0; h < dynamicRituals.get(index).getRitual().getRecipe()[0].length; h++) {
				if (dynamicRituals.get(index).getRitual().getRecipe()[i][h] != null) {
					pickUpRitual[i][h] = new Gem(dynamicRituals.get(index).getRitual().getRecipe()[i][h]);
				}
			}
		}
		return pickUpRitual;
	}


	@Override
	public void render(Batch batch) {
		font = Assets.getFont(24);
		batch.draw(texture, position.x, position.y, width, height);
		batch.end(); // drawing doesn't happen until .end() is called
		batch.begin(); // restart batch to not scissor the background (as well as not break everything else that uses the batch);
		enableScissor(620f, 0, 800f, 230f); // if this changes the Rectangle slideArea in InputHandler has to change
		if (isMoving) {
			renderSlidingIndex(batch);
		}
		for (int i = firstIndex; i < lastIndex; i++) {
			dynamicRituals.get((i) % count).render(batch, font,village);
		}
		batch.end(); // must end batch before disabling scissor
		disableScissor();
		batch.begin();
	}

	private void renderSlidingIndex(Batch batch) {
		if (slideLeft) {
			dynamicRituals.get((firstIndex - 1 + count) % count).render(batch, font, village);
		} else if (slideRight) {
			dynamicRituals.get((firstIndex + 4 + count) % count).render(batch, font, village);
		}
	}

	@Override
	public void update(float delta) {
		// this deals with how rituals slide to the right and to the left
		lastIndex = firstIndex + 4; // the last index to be rendered
		if (isMoving) {
			slideTime += delta;
			if (slideLeft) { // everything goes to the left 1 index
				if (!updateInitialPositions) {
					//this update the initial positions of rituals before they start sliding. firstIndex-1 calculate the index disappearing
					for (int k = firstIndex - 1; k < lastIndex; k++) {
						dynamicRituals.get((k + count) % count).update(delta, k - firstIndex + 1); // add count to k to prevent negative results
					}
					updateInitialPositions = true;
				}
				for (int k = firstIndex - 1; k < lastIndex; k++) {
					dynamicRituals.get((k + count) % count).updateMovement(delta, 1);
				}
			} else if (slideRight) { // slidingIndex slides to the right
				if (!updateInitialPositions) {//this update the initial positions of rituals before they start sliding. firstIndex+
					for (int k = firstIndex; k < lastIndex + 1; k++) { // last index +1 calculate the index appearing from the right
						dynamicRituals.get(k % count).update(delta, k - firstIndex - 1);
					}
					updateInitialPositions = true;
				}
				for (int k = firstIndex; k < lastIndex + 1; k++) {
					dynamicRituals.get((k + count) % count).updateMovement(delta, -1);
				}
			}
			if (slideTime >= 0.7 / (DynamicRitual.SLIDING_SPEED)) {
				System.out.println(slideTime);
				slideTime = 0;
				isMoving = false;
				updateInitialPositions = false;
				slideRight = false;
				slideLeft = false;

			}
		} else {
			for (int k = firstIndex; k < lastIndex; k++) {
				dynamicRituals.get(k % count).update(delta, k - firstIndex);
			}
		}
	}

	public void setMoving(boolean moving) {
		isMoving = moving;
	}

}
