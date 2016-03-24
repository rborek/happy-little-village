package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemColour;


public class DynamicRitual {

    private Ritual ritual;
    private Rectangle[][] recipePositions;
    private static final int spaceBetweenGems = 36;
    private static final int gemSize = 36;
    private static final int startY = 150;
    private int index;
    private float posX = 0;
    public static GameObject[] gemTextures = {
            new GameObject(Gem.getArrayOfTextures()[0], 0, 0, gemSize, gemSize),
            new GameObject(Gem.getArrayOfTextures()[1], 0, 0, gemSize, gemSize),
            new GameObject(Gem.getArrayOfTextures()[2], 0, 0, gemSize, gemSize),
            new GameObject(Gem.getArrayOfTextures()[3], 0, 0, gemSize, gemSize),};

    public DynamicRitual(Ritual ritual ) {
        this.ritual = ritual;
        recipePositions = new Rectangle[ritual.getRecipe().length][ritual.getRecipe()[0].length];
        movePosition(posX);

    }

    public void render(Batch batch, BitmapFont font) {

        for (int k = 0; k < ritual.getRecipe().length; k++) {
            for (int h = 0; h < ritual.getRecipe()[0].length; h++) {
                if (ritual.getRecipe()[k][h] != null) {
                    if (ritual.getRecipe()[k][h].equals(GemColour.BLUE)) {
                        gemTextures[1].setPosition(recipePositions[k][h].x, startY - k * spaceBetweenGems);
                        gemTextures[1].render(batch, gemSize, gemSize);
                    } else if (ritual.getRecipe()[k][h].equals(GemColour.GREEN)) {
                        gemTextures[2].setPosition(recipePositions[k][h].x, startY - k * spaceBetweenGems);
                        gemTextures[2].render(batch, gemSize, gemSize);
                    } else if (ritual.getRecipe()[k][h].equals(GemColour.YELLOW)) {
                        gemTextures[3].setPosition(recipePositions[k][h].x, startY - k * spaceBetweenGems);
                        gemTextures[3].render(batch, gemSize, gemSize);
                    } else if (ritual.getRecipe()[k][h].equals(GemColour.RED)) {
                        gemTextures[0].setPosition(recipePositions[k][h].x, startY - k * spaceBetweenGems);
                        gemTextures[0].render(batch, gemSize, gemSize);
                    }
                }
            }
        }
        for (int k = 0; k < ritual.getEffects().length; k++) {
            font.draw(batch, ritual.getEffects()[k].getModifier().name() + ritual.getEffects()[k].getAmount(), posX, startY - ritual.getEffects().length * 35 - k * 25);
        }
    }

    public void update(float delta, int index) {
        if(posX != 200 * (index) + 650){
            posX = 200 *(index) + 650;
            movePosition(posX);
        }
    }

    public void updateMovement(float delta, int sign) {
        //index is the relative position of the ritual
        //sign determines if the ritual goes left or right
        //int indexX = 650 + index * 200 ;
        posX -= 200 * ((float) delta) * sign* 1.5;
        movePosition(posX);
    }

    //this update the position of a ritual when it is slided
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

}
