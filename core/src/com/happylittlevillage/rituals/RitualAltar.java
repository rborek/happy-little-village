package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.menu.MenuItem;
import com.happylittlevillage.village.Village;

import java.util.ArrayList;

public class RitualAltar extends GameObject implements MenuItem {
    private GemBag gemBag;
    // gonna replace animation with different picture
    private Texture[] animation = Assets.getTextures("altar/altar1.png", "altar/altar2.png", "altar/altar3.png", "altar/altar2.png", "altar/altar1.png");
    private Texture button = Assets.getTexture("altar/button.png");
    private Rectangle commenceButton;
    private ArrayList<Ritual> rituals = new ArrayList<Ritual>();
    private static final int spacingX = 136;
    private static final int spacingY = 121;
    private static final int paddingX = 60;
    private static final int paddingY = 67;
    private static final int slotSize = 64;
    //new measurements
    private static final int slotSize2 = 80;
    private static final int spacing = 40;

    private boolean animating = false;
    private float timer = 0;
    //var for the grid
    private Gem[][] grid; // background stuff
    private int[][] bonus;
    private int[][] addToBonus;
    private Rectangle[][] slots; // render and UI stuff
    private ArrayList<RitualEffect[]> ritualEffects = new ArrayList<RitualEffect[]>();
    private Village village;
    private ArrayList<ArrayList<GridPoint2>> lightUpGrid = new ArrayList<ArrayList<GridPoint2>>();


    public RitualAltar(GemBag gemBag, float xPos, float yPos, Village village, RitualBook ritualBook) {
        super(Assets.getTexture("altar/altar.png"), xPos, yPos, 400, 400);
        this.village = village;
        this.gemBag = gemBag;
        bonus = new int[4][4];
        addToBonus = new int[4][4];
        grid = new Gem[4][4]; // the new ritualAltar, background stuff
        slots = new Rectangle[4][4]; // only for UI. What goes on in the background is handled by grid
        int paddingColumn = 240;
        for (int i = 0; i < 4; i++) { // row
            for (int j = 0; j < 4; j++) { //column
                slots[i][j] = new Rectangle(position.x + 37 + 87 * j, spacing + paddingColumn + 280, slotSize2, slotSize2);
            }
            paddingColumn -= 83;
        }
        commenceButton = new Rectangle(position.x + (width / 2) - (button.getWidth() / 2), position.y - 50, button.getWidth(), button.getHeight() + 30);
        rituals = ritualBook.getUnlockedRitual();
    }


    public boolean gainRitual(Ritual ritual) {
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
        super.render(batch);
        batch.draw(button, position.x + (width / 2) - (button.getWidth() / 2), position.y + 30 - 50);
        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[0].length; k++) {
                if (grid[i][k] != null) {
                    batch.draw(grid[i][k].getTexture(), slots[i][k].x, +slots[i][k].y, 64, 64);
                }
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
            for (int k = 0; k < slots[0].length; k++) {
                if (slots[i][k].contains(x, y)) {
                    if (grid[i][k] != null) {
                        Gem gemToReturn = grid[i][k];
                        grid[i][k] = null;
                        return gemToReturn;
                    }
                }
            }

        }
        return null;
    }

    public void startAnimating() {
        animating = true;
    }

    public void useGems2() {
        //print out grid first
        for (int gridRow = 0; gridRow < grid.length; gridRow++) { //row gridRow
            for (int gridColumn = 0; gridColumn < grid[0].length; gridColumn++) { //column gridColumn
                if (grid[gridRow][gridColumn] != null) {
                    //iterate through all known grid recipe
                    for (int ritualNumber = 0; ritualNumber < rituals.size(); ritualNumber++) {
                        // iterate through the first row of a recipe
                        for (int firstRecipePosition = 0; firstRecipePosition < rituals.get(ritualNumber).getRecipe()[0].length; firstRecipePosition++) {
                            // get the first non-null colour
                            if (rituals.get(ritualNumber).getRecipe()[0][firstRecipePosition] != null) {
                                //check if the first row's non-null colour matches with the grid
                                if (grid[gridRow][gridColumn].getColour().equals(rituals.get(ritualNumber).getRecipe()[0][firstRecipePosition])) {
                                    //start specifically checking one recipe
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
        //make rituals affect the village
        for (RitualEffect effects[] : ritualEffects) {
            if (effects != null) {
                for (RitualEffect effect : effects) {
                    effect.affectVillage(village);
                }
            }
        }
        ritualEffects.clear();
        //TODO figure out something to do with bonuses
        //reset bonus
        for (int a = 0; a < bonus.length; a++) {
            for (int b = 0; b < bonus[0].length; b++) {
                bonus[a][b] = 0;
            }
        }
        removeAllGems();
    }

    // compareRecipe compare the recipe according to ritualNumber,
    // having the position of gridRow and  gridColumn
    // and the  firstRecipePosition that the grid encounters
    private void compareRecipe(int ritualNumber, int gridRow, int gridColumn, int firstRecipePosition) {
        GemColour[][] check = rituals.get(ritualNumber).getRecipe(); // check: just to shorten the path
        boolean match = true;
        ArrayList<GridPoint2> addToLightUpGrid = new ArrayList<GridPoint2>();
        //reset addToBonus
        for (int a = 0; a < bonus.length; a++) {
            for (int b = 0; b < bonus[0].length; b++) {
                addToBonus[a][b] = 0;
            }
        }
        //reset addToLightUpGrid
        addToLightUpGrid.clear();
//        System.out.println("Start checkMatch with recipe:" + rituals.get(ritualNumber).getName());
        checkMatch:
        {
            for (int recipeRow = 0; recipeRow < check.length; recipeRow++) { // the length of recipe-row
                for (int recipeColumn = 0; recipeColumn < check[0].length; recipeColumn++) { // the width of recipe-column
                    if (check[recipeRow][recipeColumn] != null) { // for every non-void value of recipe.
                        //Recipe is out of bound
                        if ((gridRow + recipeRow) > 3 || (gridColumn + recipeColumn - firstRecipePosition) > 3 || (gridColumn + recipeColumn - firstRecipePosition) < 0) {
                            match = false;
                            System.out.println("OUT OF BOUND");
                            break checkMatch;
                        }
                        //Grid does not match the recipe
                        else if (grid[gridRow + recipeRow][gridColumn + recipeColumn - firstRecipePosition] == null || !grid[gridRow + recipeRow][gridColumn + recipeColumn - firstRecipePosition].getColour().equals(check[recipeRow][recipeColumn])) {
                            match = false;
                            if (grid[gridRow + recipeRow][gridColumn + recipeColumn - firstRecipePosition] == null) {
                                System.out.println("DOES NOT MATCH AT" + recipeRow + recipeColumn);
                            } else {
                                System.out.println("DOES NOT MATCH AT" + recipeRow + recipeColumn +
                                        "colour check is:" + check[recipeRow][recipeColumn] + " colour grid is:" + grid[gridRow + recipeRow][gridColumn + recipeColumn - firstRecipePosition].getColour()
                                        + " At " + (gridRow + recipeRow) + (gridColumn + recipeColumn - firstRecipePosition));
                            }
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
                        addToLightUpGrid.add(new GridPoint2(c, d));
                    }
                }
            }
            //add used position to lightUpGrid for rendering
            lightUpGrid.add(addToLightUpGrid);
            //add each effect to the arrayList of ritualEffects
            ritualEffects.add(rituals.get(ritualNumber).getEffects());
        }
    }

    public boolean place(Gem gem, float x, float y) {
        //x and y is the mouse's position but also the center of gem
        Rectangle gemBounds = new Rectangle(x - spacing, y - spacing, slotSize2, slotSize2);
        //centerGrid contains center of overlapped grids
        double distance;
        double minDistance = 100; // very arbitrary number
        int gridRow = 0;
        int gridCol = 0;
        Vector2 center = new Vector2();
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                if (slots[i][k].overlaps(gemBounds)) {
                    slots[i][k].getCenter(center);
                    distance = Math.sqrt(Math.pow(center.x - x, 2) + Math.pow(center.y - y, 2));
                    if (minDistance > distance) {
                        minDistance = distance;
                        gridRow = i;
                        gridCol = k;
                    }
                }
            }
        }
        //if the minDistance is changed- something overlap
        if (minDistance != 100) {
            if (grid[gridRow][gridCol] != null) {
                System.out.println("Colour is" + grid[gridRow][gridCol].getColour());
                gemBag.add(grid[gridRow][gridCol].getColour());

            }
            grid[gridRow][gridCol] = gem;
            return true;
        }
        return false;
    }


    public GemColour getColour(int row, int col) {
        if (grid[row][col] != null) {
            return grid[row][col].getColour();
        }
        return null;
    }

    private void removeAllGems() {
        for (int i = 0; i < grid.length; i++)
            for (int k = 0; k < grid[0].length; k++) grid[i][k] = null;
    }

    @Override
    public boolean interact(float mouseX, float mouseY) {
        pickUpGem(mouseX, mouseY);
        if (commenceButton.contains(mouseX, mouseY)) {
            useGems2();
            return true;
        }
        return false;
    }
}

