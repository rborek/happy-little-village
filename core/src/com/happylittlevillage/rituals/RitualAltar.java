package com.happylittlevillage.rituals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private Gem[][] grid;
    private int[][] bonus;
    private int[][] bonusAdd;
    private Rectangle[][] slots2;
    private ArrayList<RitualEffect[]> ritualEffects =new ArrayList<RitualEffect[]>();
    private Village village;

    public RitualAltar(GemBag gemBag, float xPos, float yPos) {
        super(Assets.getTexture("altar/altar1.png"), xPos, yPos);
        // rows then columns
        grid = new Gem[4][4]; // the new ritualAltar to work with
        bonus = new int[4][4];
        bonusAdd = new int[4][4];
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
    //
    // once it is done checking, call affectVillage(village) from every RitualEffect
    //the rule is to check from left to right, downward.
    public void useGems2() {
        for (int i = 0; i < grid.length; i++) { //row i
            for (int j = 0; j < grid[0].length; j++) { //column j
                if (grid[i][j] != null) { // change from null to 0
                    //iterate through all known grid recipe
                    for (int z = 0; z < rituals.size(); z++) {
                        // iterate through the first row of a recipe
                        for (int w = 0; w < rituals.get(z).getRecipe()[0].length; w++) {
                            // get the first non-null colour
                            if (rituals.get(z).getRecipe()[0][w] != null) {
                                //check if the first row's non-null colour matches with the grid
                                if (grid[i][j].getColour().equals(rituals.get(z).getRecipe()[0][w])) {
                                    //start to specifically check one recipe
                                    compareRecipe(z, i, j, w);
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

    private void compareRecipe(int z, int i, int j, int w) {
        GemColour[][] check = rituals.get(z).getRecipe(); // param @check just to shorten the path
        boolean match = true;
        //reset the bonusAdd
        for (int a = 0; a < bonus.length; a++) {
            for (int b = 0; b < bonus[0].length; b++) {
                bonusAdd[a][b] = 0;
            }
        }
        checkMatch:
        {
            for (int a = 0; a < check.length; a++) { // the length of recipe-row
                for (int b = 0; b < check[0].length; b++) { // the width of recipe-column
                    if (check[a][b] != null) { // for every non-void value of recipe.
                        if ((i + a) > 3 || (j + b - w) > 3 || (j + b - w) < 0) {
                            match = false;
                            break checkMatch;
                        } else if (grid[i + a][j + b - w].getColour().equals(check[a][b])) {
                            match = false;
                            break checkMatch;
                        } else {//if the position passes these 2 conditions +1 for the use of that matched position
                            bonusAdd[i + a][j + b - w]++;
                        }
                    }
                }
            }
        }
        if (match) {
            for (int c = 0; c < bonus.length; c++) { // add bonus position to bonus
                for (int d = 0; d < bonus[0].length; d++) {
                    if (bonusAdd[c][d] != 0) {
                        bonus[c][d] += bonusAdd[c][d];
                    }
                }
            }
            //add each effect to the arrayList of ritualEffects
            ritualEffects.add(rituals.get(z).getEffects());
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

