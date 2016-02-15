package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.menu.MenuItem;
import com.happylittlevillage.village.Village;

import java.awt.*;
import java.util.ArrayList;

public class RitualAltar extends GameObject implements MenuItem {
    private Gem[] gems;
    private GemBag gemBag;
    private Rectangle[] slots;
    private Texture[] animation = Assets.getTextures("altar/altar1.png", "altar/altar2.png", "altar/altar3.png", "altar/altar2.png", "altar/altar1.png");
    private Texture button = Assets.getTexture("altar/button.png");
    private Rectangle commenceButton;
    private ArrayList<Ritual> rituals = new ArrayList<Ritual>();
    private static final int spacingX = 136;
    private static final int spacingY = 121;
    private static final int paddingX = 60;
    private static final int paddingY = 67;
    private static final int slotSize = 64;
    private boolean animating = false;
    private float timer = 0;
    //var for the grid
    private Gem[][] grid;
    private int[][] bonus;
    private int[][] addToBonus;
    private Rectangle[][] slots2;
    private ArrayList<RitualEffect[]> ritualEffects =new ArrayList<RitualEffect[]>();
    private Village village;
    private ArrayList<ArrayList<Point>> lightUpGrid = new ArrayList<ArrayList<Point>>();


    public RitualAltar(GemBag gemBag, float xPos, float yPos) {
        super(Assets.getTexture("altar/altar1.png"), xPos, yPos);
        // rows then columns
        grid = new Gem[4][4]; // the new ritualAltar to work with
        bonus = new int[4][4];
        addToBonus = new int[4][4];
        slots2 = new Rectangle[4][4]; // new rectangle to work with
        //
        gems = new Gem[4];
        slots = new Rectangle[4];
        this.gemBag = gemBag;
        slots[0] = new Rectangle(paddingX, paddingY + 64 + spacingY, 64, 64);
        slots[1] = new Rectangle(paddingX + 64 + spacingX, paddingY + 64 + spacingY, 64, 64);
        slots[2] = new Rectangle(paddingX, paddingY, 64, 64);
        slots[3] = new Rectangle(paddingX + 64 + spacingX, paddingY, 64, 64);
        for (int i = 0; i < slots.length; i++) {
            slots[i].x += position.x;
            slots[i].y += position.y;
        }
        commenceButton = new Rectangle(position.x + (width / 2) - (button.getWidth() / 2), position.y, button.getWidth(), button.getHeight() + 30);
        gainStartingRituals();
    }

    private void gainStartingRituals() {
        gainRitual(new AddFoodRitual());
        gainRitual(new AddFoodLoseWaterRitual());
        gainRitual(new AddFoodLoseWaterRitual());
        gainRitual(new AddVillagerRitual());
        gainRitual(new AddWaterRemoveFoodRitual());
        gainRitual(new AddWaterRitual());
        gainRitual(new ToExplorerRitual());
        gainRitual(new ToFarmerRitual());
        gainRitual(new ToMinerRitual());
        gainRitual(new RemoveVillagerRitual());
    }


    public boolean gainRitual(Ritual ritual) {
//		String ritualName = ritual.getName();
//		for (Ritual ritualToCheck : rituals) {
//			if (ritualName.equals(ritualToCheck.getName())) {
//				return false;
//			}
//		}
        rituals.add(ritual);
        return true;

    }

    public void setGemBag(GemBag gemBag) {
        this.gemBag = gemBag;
    }

    @Override
    public void update(float delta) {
        if (animating) {
            int frame = (int) ((timer * 8) % animation.length);
            texture = animation[frame];
            timer += delta;
            if (timer * 8 >= 5) {
                animating = false;
                timer = 0;
                texture = animation[0];
            }
        }
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y);
        batch.draw(button, position.x + (width / 2) - (button.getWidth() / 2), position.y + 30);
        for (int i = 0; i < gems.length; i++) {
            if (gems[i] != null) {
                batch.draw(gems[i].getTexture(), slots[i].x + 8, slots[i].y + 8);
            }
        }
    }

    public void removeRitual(Ritual ritual) {
        for (int i = 0; i < rituals.size(); i++) {
            if (ritual.getName().equals(rituals.get(i).getName())) {
                rituals.remove(i);
            }
        }
    }

    public Gem pickUpGem(float x, float y) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].contains(x, y)) {
                if (gems[i] != null) {
                    Gem gemToReturn = gems[i];
                    gems[i] = null;
                    return gemToReturn;
                }
            }
        }
        return null;
    }

    public void startAnimating() {
        animating = true;
    }

    // TODO Duke - use ritual.getRecipe() and ritual.getEffects()
    // add each successful Ritual's effects to an arrayList of RitualEffects
    // once it is done checking, call affectVillage(village) from every RitualEffect
    //the rule is to check from left to right, downward.
    public void useGems2() {
        for (int gridRow = 0; gridRow < grid.length; gridRow++) { //row gridRow
            for (int gridColumn = 0; gridColumn < grid[0].length; gridColumn++) { //column gridColumn
                if (grid[gridRow][gridColumn] != null) { // change from null to 0
                    //iterate through all known grid recipe
                    for (int ritualNumber = 0; ritualNumber < rituals.size(); ritualNumber++) {
                        // iterate through the first row of a recipe
                        for (int firstRecipePosition = 0; firstRecipePosition < rituals.get(ritualNumber).getRecipe()[0].length; firstRecipePosition++) {
                            // get the first non-null colour
                            if (rituals.get(ritualNumber).getRecipe()[0][firstRecipePosition] != null) {
                                //check if the first row's non-null colour matches with the grid
                                if (grid[gridRow][gridColumn].getColour().equals(rituals.get(ritualNumber).getRecipe()[0][firstRecipePosition])) {
                                    //start to specifically check one recipe
                                    compareRecipe(ritualNumber, gridRow, gridColumn, firstRecipePosition);
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }//end checking for one specific recipe
                    }
                }
            }
        }
        //call affectVillage for all rituals
        for(int count2 = 0; count2 < ritualEffects.size(); count2++) {
            for (int count = 0; count < ritualEffects.get(count2).length; count++) {
                ritualEffects.get(count2)[count].affectVillage(village);
            }
        }
        //TODO figure out something to do with bonuses
        //reset bonus
        for (int a = 0; a < bonus.length; a++) {
            for (int b = 0; b < bonus[0].length; b++) {
                bonus[a][b] = 0;
            }
        }
    }

    // compareRecipe compare the recipe according to @param ritualNumber,
    // having the position of @param gridRow and @param gridColumn
    // and the @param firstRecipePosition that the grid encounters
    private void compareRecipe(int ritualNumber, int gridRow, int gridColumn, int firstRecipePosition) {
        GemColour[][] check = rituals.get(ritualNumber).getRecipe(); // check: just to shorten the path
        boolean match = true;
        ArrayList<Point> addToLightUpGrid = new ArrayList<Point>();
        Point addPoint = new Point(0,0);
        //reset addToBonus
        for (int a = 0; a < bonus.length; a++) {
            for (int b = 0; b < bonus[0].length; b++) {
                addToBonus[a][b] = 0;
            }
        }
        //reset addToLightUpGrid
        addToLightUpGrid.clear();

        checkMatch:
        {
            for (int recipeRow = 0; recipeRow < check.length; recipeRow++) { // the length of recipe-row
                for (int recipeColumn = 0; recipeColumn < check[0].length; recipeColumn++) { // the width of recipe-column
                    if (check[recipeRow][recipeColumn] != null) { // for every non-void value of recipe.
                        if ((gridRow + recipeRow) > 3 || (gridColumn + recipeColumn - firstRecipePosition) > 3 || (gridColumn + recipeColumn - firstRecipePosition) < 0) {
                            match = false;
                            break checkMatch;
                        } else if (grid[gridRow + recipeRow][gridColumn + recipeColumn - firstRecipePosition].getColour().equals(check[recipeRow][recipeColumn])) {
                            match = false;
                            break checkMatch;
                        } else {//if the position passes these 2 conditions +1 for the use of that matched position
                            addToBonus[gridRow + recipeRow][gridColumn + recipeColumn - firstRecipePosition]++;
                        }
                    }
                }
            }
        }
        if (match) {
            // add bonus position to bonus
            for (int c = 0; c < bonus.length; c++) {
                for (int d = 0; d < bonus[0].length; d++) {
                    if (addToBonus[c][d] != 0) {
                        bonus[c][d] += addToBonus[c][d];
                        //set x y coordinates for addPoint then add it to addToLightUpGrid which will then be added to the final LightUpGrid
                        addPoint.setLocation(c,d);
                        addToLightUpGrid.add(addPoint);
                    }
                }
            }
            //add used position to lightUpGrid for rendering
            lightUpGrid.add(addToLightUpGrid);
            //add each effect to the arrayList of ritualEffects
            ritualEffects.add(rituals.get(ritualNumber).getEffects());
        }
    }


    public void useGems() {
        for (Ritual ritual : rituals) {
            if (ritual.attempt(gems)) {
                startAnimating();
                break;
            }
        }
        removeAllGems();

    }

    public boolean add(Gem gem, float x, float y) {
        Rectangle gemBounds = new Rectangle(x - 32, y - 32, 64, 64);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].overlaps(gemBounds)) {
                if (gems[i] != null) {
                    gemBag.add(gems[i].getColour());
                }
                gems[i] = gem;
                return true;
            }
        }
        return false;
    }


    public GemColour getColour(int index) {
        if (gems[index] != null) {
            return gems[index].getColour();
        }
        return null;
    }

    private void removeAllGems() {
        for (int i = 0; i < gems.length; i++) {
            gems[i] = null;
        }
    }

    @Override
    public boolean interact(float mouseX, float mouseY) {
        if (commenceButton.contains(mouseX, mouseY)) {
            useGems();
            return true;
        }
        return false;
    }
}

