package com.happylittlevillage.rituals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.messages.MessageBox;

import java.util.ArrayList;
import java.util.List;

public class RitualTree extends GameObject {

    private int skillPoints = 1;
    private ArrayList<Ritual> rituals = new ArrayList<Ritual>();
    private ArrayList<GemColour[][]> recipes = new ArrayList<GemColour[][]>();
    private ArrayList<Rectangle[][]> recipePositions = new ArrayList<Rectangle[][]>(); // arrayList which elements are arrayLists of rectangles of each ritual
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String[]> effects = new ArrayList<String[]>();
    //TODO make each skill upgradable ?
    private Texture continueButton = Assets.getTexture("ui/continue_button.png");
    private Rectangle continueButtonPosiiton = new Rectangle(0, 0, continueButton.getWidth(), continueButton.getHeight());
    private GameHandler gameHandler;
    public RitualTree(GameHandler gameHandler, float xPos, float yPos) {
        super(Assets.getTexture("ui/ritual_tree.png"), xPos, yPos);
        this.gameHandler = gameHandler;
        addStandardRitual();
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


    private void addStandardRitual() {
        List<String> ritualNames = Ritual.getRitualNames();
        for (String name : ritualNames) {
            rituals.add(Ritual.getRitual(name));
        }
    }

    public ArrayList<Ritual> getRituals() {
        return rituals;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public boolean interact(float mouseX, float mouseY) {
        System.out.println("interacted with continue button");
        if (continueButtonPosiiton.contains(mouseX, mouseY)) {
            gameHandler.unpause();
            return true;
        }
        return false;
    }
}
