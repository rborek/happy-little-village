package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.GemColour;

import java.util.ArrayList;

public class RitualBook extends GameObject {
    private final Texture[] pages = {Assets.getTexture("ui/book.png"),
            Assets.getTexture(("ui/book.png")), Assets.getTexture("ui/book.png")};
    private int pageNumber = 1;
    private Rectangle leftArrow;
    private Rectangle rightArrow;
    private ArrayList<Ritual> rituals = new ArrayList<Ritual>();
    private ArrayList<GemColour[][]> recipes = new ArrayList<GemColour[][]>();
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String[]> effects = new ArrayList<String[]>();
    private static GameObject redGem = new GameObject(Assets.getTexture("gems/gem_red.png"), 12, 12);
    private static GameObject greenGem = new GameObject(Assets.getTexture("gems/gem_green.png"), 12, 12);
    private static GameObject yellowGem = new GameObject(Assets.getTexture("gems/gem_yellow.png"), 12, 12);
    private static GameObject blueGem = new GameObject(Assets.getTexture("gems/gem_blue.png"), 12, 12);
    private BitmapFont font;

    public RitualBook(float xPos, float yPos) {
        super(Assets.getTexture("ui/book1.png"), xPos, yPos);
        leftArrow = new Rectangle(xPos + 30, yPos + 20, 70, 45);
        rightArrow = new Rectangle(xPos + 495, yPos + 20, 70, 45);
        addStandardRitual();
        synchronizeRitualInfo();
    }

    private void addStandardRitual() {
        rituals.add(Ritual.getRitual("addWater"));
        rituals.add(Ritual.getRitual("addFood"));
        rituals.add(Ritual.getRitual("addVillager"));
        rituals.add(Ritual.getRitual("addResourcesMakeVillagersAngry"));
        rituals.add(Ritual.getRitual("convertExplorer"));
        rituals.add(Ritual.getRitual("convertFarmer"));
        rituals.add(Ritual.getRitual("convertMiner"));
        rituals.add(Ritual.getRitual("killAVillager"));


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
    }

    public ArrayList<Ritual> getUnlockedRitual() {
        return rituals;
    }

    public void
    click(float x, float y) {
        if (leftArrow.contains(x, y)) {
            turnLeft();
        } else if (rightArrow.contains(x, y)) {
            turnRight();
        }
        updateTexture();
    }

    public void turnLeft() {
        if (pageNumber == 1) {
            pageNumber = 3;
        } else {
            pageNumber--;
        }
    }

    public void turnRight() {
        if (pageNumber == 3) {
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

    private void drawRecipes(int k, Batch batch) {
        // k is the range of 4 recipes needed to be drawn. It it also associated with the page number
        int xLeft = 140;
        int xRight = 460;
        int yTop = 550;
        int yBot = 300;
        for (int indexOfRecipe = k - 3; indexOfRecipe <= k; indexOfRecipe++) {
            if (indexOfRecipe < recipes.size()) {
                if (indexOfRecipe % 4 == 0) {
                    drawOneRecipe(recipes.get(indexOfRecipe), xLeft, yTop, batch, indexOfRecipe);
                } else if (indexOfRecipe % 4 == 1) {
                    drawOneRecipe(recipes.get(indexOfRecipe), xRight, yTop, batch, indexOfRecipe);
                } else if (indexOfRecipe % 4 == 2) {
                    drawOneRecipe(recipes.get(indexOfRecipe), xLeft, yBot, batch, indexOfRecipe);
                } else if (indexOfRecipe % 4 == 3) {
                    drawOneRecipe(recipes.get(indexOfRecipe), xRight, yBot, batch, indexOfRecipe);
                }
            }
        }
    }

    private void drawOneRecipe(GemColour[][] oneRecipe, int startX, int startY, Batch batch, int indexOfRecipe) {
        int spaceBetweenGems = 50;
        int gemSize = 48;
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
}
