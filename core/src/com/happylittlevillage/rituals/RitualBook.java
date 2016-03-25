package com.happylittlevillage.rituals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.menu.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class RitualBook extends GameObject {
    private int firstIndex = 0;
    private int slidingIndex = 0;
    private int count = 0;
    private Rectangle leftArrow;
    private Rectangle rightArrow;
    //TODO all the recipes, recipePositions, names, effects need to be called from RitualTree
    private ArrayList<DynamicRitual> dynamicRituals = new ArrayList<DynamicRitual>();
    private BitmapFont font;
    private Vector2 touchRitualIndex = new Vector2(0, 0); // this indicates which grid of the ritual the mouse touches.  x coord means the row and y coord means the column
    private Vector2 touchRitualSpecificPosition = new Vector2(0, 0); //this indicates the exact position of the specific grid in contact
    private GameObject ritual_arrow_right = new GameObject(Assets.getTexture("ui/ritual_arrow_right.png"), position.x + 610, position.y + 120, 60, 60);
    private GameObject ritual_arrow_left = new GameObject(Assets.getTexture("ui/ritual_arrow_left.png"), position.x + 610, position.y + 40, 60, 60);
    private boolean isMoving = false;
    private boolean updateIndexForSlidingRitual = false;
    private boolean slideLeft = false;
    private boolean slideRight = false;
    private float slideTime = 0;

    public RitualBook(float xPos, float yPos) {
        super(Assets.getTexture("ui/parchment2.png"), xPos, yPos, 700, 250);
        rightArrow = new Rectangle(ritual_arrow_right.getPosition().x, ritual_arrow_right.getPosition().y, ritual_arrow_right.getWidth(), ritual_arrow_right.getHeight());
        leftArrow = new Rectangle(ritual_arrow_left.getPosition().x, ritual_arrow_left.getPosition().y, ritual_arrow_left.getWidth(), ritual_arrow_left.getHeight());
        addStandardRitual();
    }

    private void addStandardRitual() {
        //TODO change this to only available rituals
        List<String> ritualNames = Ritual.getRitualNames();
        count = 0;
        for (String name : ritualNames) {
            dynamicRituals.add(new DynamicRitual(Ritual.getRitual(name)));
            count++;
        }
    }

    public void turnPage(float x, float y) {
        if (!isMoving) {
            if (leftArrow.contains(x, y)) {
                previous();
            } else if (rightArrow.contains(x, y)) {
                next();
            }
        }
    }

    public void previous() {
        isMoving = true;
        firstIndex--;
        if (firstIndex < 0) {
            firstIndex = count - 1;
        }
        slideRight = true;
        System.out.println("previous");
    }

    public void next() {
        isMoving = true;
        firstIndex++;
        if (firstIndex >= count) {
            firstIndex = 0;
        }
        slideLeft = true;
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
            for (int k = firstIndex; k < firstIndex + 3; k++) {
                int index = k % count;
//            System.out.println(index + " " + firstIndex);
                // check for one specific ritual recipe position in the arrayList of recipePositions
                for (int i = 0; i < dynamicRituals.get(index).getRitual().getRecipe().length; i++) {
                    for (int h = 0; h < dynamicRituals.get(index).getRitual().getRecipe()[0].length; h++) {
                        //if there is a gem in the position
                        if (dynamicRituals.get(index).getRitual().getRecipe()[i][h] != null) {
                            if (dynamicRituals.get(index).getRecipePositions()[i][h].contains(x, y)) { // if the recipe position contains the mouse
                                touchRitualIndex.set(i, h);
                                touchRitualSpecificPosition.set(dynamicRituals.get(index).getRecipePositions()[i][h].x - x, dynamicRituals.get(index).getRecipePositions()[i][h].y - y);
                                System.out.println("touchRitualIndex is " + i + h);
                                return getPickUpRitual(index);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    // get the matched ritual up
    private Gem[][] getPickUpRitual(int index) {
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
        enableScissor(620f, 0, 600f, 220f);
        if (isMoving) {
            renderSlidingIndex(batch);
        }
        for (int i = firstIndex; i < firstIndex + 3; i++) {
            dynamicRituals.get((i) % count).render(batch, font);
        }
        batch.end(); // must end batch before disabling scissor
        disableScissor();
        batch.begin();
        ritual_arrow_left.render(batch);
        ritual_arrow_right.render(batch);
    }

    private void renderSlidingIndex(Batch batch) {
        if (slideLeft) {
            dynamicRituals.get((firstIndex - 1 + count) % count).render(batch, font);
        } else if (slideRight) {
            dynamicRituals.get((firstIndex + 3 + count) % count).render(batch, font);
        }
    }

    @Override
    public void update(float delta) {
        if (isMoving) {
            slideTime += delta;
            if (slideLeft) { // everything goes to the left 1 index
                if (!updateIndexForSlidingRitual) {
                    for (int k = firstIndex - 1; k < firstIndex + 3; k++) {
                        dynamicRituals.get((k + count) % count).update(delta, k - firstIndex + 1); // add count to k to prevent negative results
                    }
                    updateIndexForSlidingRitual = true;
                }
                dynamicRituals.get((firstIndex - 1 + count) % count).updateMovement(delta, 1);
                for (int k = firstIndex; k < firstIndex + 3; k++) {
                    dynamicRituals.get(k % count).updateMovement(delta, 1); // k - firstIndex is the position being drawn,
                    // k - firstIndex is the position that the texture needs to go to
                }
            } else if (slideRight) { // slidingIndex slides to the right
                if (!updateIndexForSlidingRitual) {
                    for (int k = firstIndex; k < firstIndex + 4; k++) {
                        dynamicRituals.get(k % count).update(delta, k - firstIndex - 1);
                    }
                    updateIndexForSlidingRitual = true;
                }
                dynamicRituals.get((firstIndex + 3 + count) % count).updateMovement(delta, -1);
                for (int k = firstIndex; k < firstIndex + 3; k++) {
                    dynamicRituals.get(k % count).updateMovement(delta, -1); // k - firstIndex is the position being drawn
                }
            }
            if (slideTime >= 0.65) {
                System.out.println(firstIndex);
                slideTime = 0;
                isMoving = false;
                updateIndexForSlidingRitual = false;
                slideRight = false;
                slideLeft = false;
            }
        } else {
            for (int k = firstIndex; k < firstIndex + 3; k++) {
                dynamicRituals.get(k % count).update(delta, k - firstIndex);
            }
        }
    }

    public Vector2 getTouchRitualIndex() {
        return touchRitualIndex;
    }

    public Vector2 getTouchRitualSpecificPosition() {
        return touchRitualSpecificPosition;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

}
