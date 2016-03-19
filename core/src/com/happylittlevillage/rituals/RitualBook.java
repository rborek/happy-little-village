package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemColour;

import java.util.ArrayList;
import java.util.List;

public class RitualBook extends GameObject {
    private final Texture[] pages = {Assets.getTexture("ui/book.png"),
            Assets.getTexture(("ui/book.png")), Assets.getTexture("ui/book.png")};
    private int pageNumber = 1;
    private Rectangle leftArrow;
    private Rectangle rightArrow;
    private ArrayList<Ritual> rituals = new ArrayList<Ritual>();
    private ArrayList<GemColour[][]> recipes = new ArrayList<GemColour[][]>();
    private ArrayList<Rectangle[][]> recipePositions = new ArrayList<Rectangle[][]>(); // arraylist which elements are arraylists of rectangles of each ritual
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String[]> effects = new ArrayList<String[]>();
    private static GameObject redGem = new GameObject(Assets.getTexture("gems/gem_red.png"), 12, 12);
    private static GameObject greenGem = new GameObject(Assets.getTexture("gems/gem_green.png"), 12, 12);
    private static GameObject yellowGem = new GameObject(Assets.getTexture("gems/gem_yellow.png"), 12, 12);
    private static GameObject blueGem = new GameObject(Assets.getTexture("gems/gem_blue.png"), 12, 12);
    private BitmapFont font;
    private int xLeft = 140;
    private int xRight = 460;
    private int yTop = 550;
    private int yBot = 300;
    private int spaceBetweenGems = 50;
    private int gemSize = 48;
    private Vector2 touchRitualIndex = new Vector2(0, 0);

    public RitualBook(float xPos, float yPos) {
        super(Assets.getTexture("ui/book.png"), xPos, yPos);
        leftArrow = new Rectangle(xPos + 30, yPos + 20, 90, 55);
        rightArrow = new Rectangle(xPos + 580, yPos + 20, 90, 55);
        addStandardRitual();
        synchronizeRitualInfo();
    }

    private void addStandardRitual() {
        List<String> ritualNames = Ritual.getRitualNames();
        for (String name : ritualNames) {
            rituals.add(Ritual.getRitual(name));
        }
    }

    private void synchronizeRitualInfo() {
        for (Ritual ritual : rituals) {
            recipes.add(ritual.getRecipe()); // get the recipes of a ritual
            names.add(ritual.getName()); // get the names of a ritual
            String[] effectsOfOneRitual = new String[ritual.getEffects().length];
            int count = 0;
            String oneEffect = "";
            //get the effects of all the rituals
            for (RitualEffect anEffect : ritual.getEffects()) {
                if (anEffect.getAmount() > 0) { // explicitly put a positive sign in front of a positive value
                    oneEffect = oneEffect.concat("+" + anEffect.getAmount() + " ");
                } else {
                    oneEffect = oneEffect.concat(anEffect.getAmount() + " ");
                }
                oneEffect = oneEffect.concat(anEffect.getModifier().name()); // get the modifier's name
                effectsOfOneRitual[count] = oneEffect;
                oneEffect = "";
                count++;
            }
            effects.add(effectsOfOneRitual); // get the effects of a ritual
        }
        //addRecipePosition based on their index and position on page
        for (int index = 0; index < recipes.size(); index++) {
            if (index % 4 == 0) {
                addRecipePosition(xLeft, yTop, recipes.get(index));
            } else if (index % 4 == 1) {
                addRecipePosition(xRight, yTop, recipes.get(index));
            } else if (index % 4 == 2) {
                addRecipePosition(xLeft, yBot, recipes.get(index));
            } else if (index % 4 == 3) {
                addRecipePosition(xRight, yBot, recipes.get(index));
            }
        }
    }

    public ArrayList<Ritual> getUnlockedRitual() {
        return rituals;
    }

    private void addRecipePosition(float startX, float startY, GemColour[][] recipe) {
        Rectangle[][] ritualRecipePosition = new Rectangle[4][4];
        for (int k = 0; k < recipe.length; k++) {
            for (int h = 0; h < recipe[0].length; h++) {
                if (recipe[k][h] != null) {
                    // set the position of each gem in a ritual
                    ritualRecipePosition[k][h] = new Rectangle(startX + h * gemSize + h * spaceBetweenGems,
                            startY + k * gemSize + k * spaceBetweenGems, gemSize, gemSize);
                }
            }
        }
        recipePositions.add(ritualRecipePosition);
    }

    public void turnPage(float x, float y) {

        if (leftArrow.contains(x, y)) {
            turnLeft();
            return;
        } else if (rightArrow.contains(x, y)) {
            turnRight();
            return;
        }
        updateTexture();

    }

    public Gem[][] getRitualRecipe(float x, float y) {
        int k = 4 * pageNumber - 1; // this number restrict the 4 situations being tested on a page
        for (int indexOfRecipePosition = k - 3; indexOfRecipePosition <= k; indexOfRecipePosition++) { // only 4 ritualPosition are being tested from k-3 to k
            // check for one specific ritual recipe position
            for (int i = 0; i < recipePositions.get(indexOfRecipePosition).length; i++) {
                for (int h = 0; h < recipePositions.get(indexOfRecipePosition)[0].length; h++) {
                    //if there is a gem in the position
                    if (recipePositions.get(indexOfRecipePosition)[i][h] != null) {
                        System.out.println("i and h is "+ i + h);
                        if (recipePositions.get(indexOfRecipePosition)[i][h].contains(x, y)) { // if the recipe position contains the mouse
                            touchRitualIndex.set(i,h);
                            System.out.println("touchRitualIndex is "+ i + h);
                            return getPickUpRitual(indexOfRecipePosition);
                        }
                    }
                }
            }
        }

        return null;
    }


    private Gem[][] getPickUpRitual(int indexOfRecipePosition) {
        Gem[][] pickUpRitual = new Gem[recipes.get(indexOfRecipePosition).length][recipes.get(indexOfRecipePosition)[0].length];
        for (int i = 0; i < recipes.get(indexOfRecipePosition).length; i++) {
            for (int h = 0; h < recipes.get(indexOfRecipePosition)[0].length; h++) {
                if (recipes.get(indexOfRecipePosition)[i][h] != null) {
                    pickUpRitual[i][h] = new Gem(recipes.get(indexOfRecipePosition)[i][h]);
                }
            }
        }
        return pickUpRitual;
    }

    public void turnLeft() {
        if (pageNumber == 1) {
            pageNumber = 2;
        } else {
            pageNumber--;
        }
    }

    public void turnRight() {
        if (pageNumber == 2) {
            pageNumber = 1;
        } else {
            pageNumber++;
        }
    }

    public void updateTexture() {
        texture = pages[pageNumber - 1];
    }

    @Override
    public void render(Batch batch) {
        font = Assets.getFont(24);
        batch.draw(texture, position.x, position.y, width, height);
        drawRecipes(4 * pageNumber - 1, batch);
    }

    //TODO put this in update
    private void drawRecipes(int k, Batch batch) {
        // k is the max index of 4 recipes needed to be drawn. It it also linked to the page number
        for (int indexOfRecipe = k - 3; indexOfRecipe <= k; indexOfRecipe++) {
//            if (indexOfRecipe < recipes.size()) { // not sure what this if statement does really..
            if (indexOfRecipe % 4 == 0) {
                drawOneRecipe(recipes.get(indexOfRecipe), xLeft, yTop, batch, indexOfRecipe);
            } else if (indexOfRecipe % 4 == 1) {
                drawOneRecipe(recipes.get(indexOfRecipe), xRight, yTop, batch, indexOfRecipe);
            } else if (indexOfRecipe % 4 == 2) {
                drawOneRecipe(recipes.get(indexOfRecipe), xLeft, yBot, batch, indexOfRecipe);
            } else if (indexOfRecipe % 4 == 3) {
                drawOneRecipe(recipes.get(indexOfRecipe), xRight, yBot, batch, indexOfRecipe);
            }
//            }
        }
    }

    private void drawOneRecipe(GemColour[][] oneRecipe, int startX, int startY, Batch batch, int indexOfRecipe) {

        for (int k = 0; k < oneRecipe.length; k++) {
            for (int h = 0; h < oneRecipe[0].length; h++) {
                if (oneRecipe[k][h] != null) {
                    if (oneRecipe[k][h].equals(GemColour.BLUE)) {
                        blueGem.setPosition(startX + h * spaceBetweenGems, startY - k * spaceBetweenGems);
                        blueGem.render(batch, gemSize, gemSize);
                    } else if (oneRecipe[k][h].equals(GemColour.GREEN)) {
                        greenGem.setPosition(startX + h * spaceBetweenGems, startY - k * spaceBetweenGems);
                        greenGem.render(batch, gemSize, gemSize);
                    } else if (oneRecipe[k][h].equals(GemColour.YELLOW)) {
                        yellowGem.setPosition(startX + h * spaceBetweenGems, startY - k * spaceBetweenGems);
                        yellowGem.render(batch, gemSize, gemSize);
                    } else if (oneRecipe[k][h].equals(GemColour.RED)) {
                        redGem.setPosition(startX + h * spaceBetweenGems, startY - k * spaceBetweenGems);
                        redGem.render(batch, gemSize, gemSize);
                    }
                }
            }
        }
        font.draw(batch, names.get(indexOfRecipe), startX, startY + 75);
        for (int k = 0; k < effects.get(indexOfRecipe).length; k++) {
            font.draw(batch, effects.get(indexOfRecipe)[k], startX, startY - oneRecipe.length * 35 - k * 25);
        }
    }

    @Override
    public void update(float delta) {

    }

    public Vector2 getTouchRitualIndex() {
        return touchRitualIndex;
    }

}
