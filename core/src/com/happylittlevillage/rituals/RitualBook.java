package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.Gem;

import java.util.ArrayList;
import java.util.List;

public class RitualBook extends GameObject {
    private int firstIndex = 0;
    private int count = 0;
    private Rectangle leftArrow;
    private Rectangle rightArrow;
    //TODO all the recipes, recipePositions, names, effects need to be called from RitualTree
    private ArrayList<DynamicRitual> dynamicRituals = new ArrayList<DynamicRitual>();
    private BitmapFont font;
    private Vector2 touchRitualIndex = new Vector2(0, 0); // this indicates which grid of the ritual the mouse touches.  x coord means the row and y coord means the column
    private Vector2 touchRitualSpecificPosition = new Vector2(0, 0); //this indicates the exact position of the specific grid in contact
    private GameObject ritual_arrow_right = new GameObject(Assets.getTexture("ui/ritual_arrow_right.png"), position.x + 630, position.y + 40, 60, 60);
    private GameObject ritual_arrow_left = new GameObject(Assets.getTexture("ui/ritual_arrow_left.png"), position.x + 630, position.y + 120, 60, 60);

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
        if (leftArrow.contains(x, y)) {
            previous();
        } else if (rightArrow.contains(x, y)) {
            next();
        }
    }

    public void previous() {
        firstIndex--;
        if (firstIndex < 0) {
            firstIndex = count;
        }
        System.out.println("previous");
    }

    public void next() {
        firstIndex++;
        if (firstIndex >= count) {
            firstIndex = 0;
        }
        System.out.println("next");

    }

    // to pick up ritual
    public Gem[][] getRitualRecipe(float x, float y) {
        // firstIndex this number restrict the 4 situations being tested on a page
        // only 4 ritualPosition are being tested from index to index +3
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
        ritual_arrow_right.render(batch);
        ritual_arrow_left.render(batch);
        for (int k = firstIndex; k < firstIndex + 3; k++) {
            dynamicRituals.get(k % count).render(batch, font, k - firstIndex);
        }

    }


    @Override
    public void update(float delta) {
        dynamicRituals.get(0).update(delta);
    }

    public Vector2 getTouchRitualIndex() {
        return touchRitualIndex;
    }

    public Vector2 getTouchRitualSpecificPosition() {
        return touchRitualSpecificPosition;
    }


}
