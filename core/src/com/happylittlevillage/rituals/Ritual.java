package com.happylittlevillage.rituals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.Village;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ritual {
    private String name;
    protected static Village village;
    protected GemColour[] gemCombination; // to be deleted
    protected GemColour[][] recipe;
	private RitualEffect[] effects;
	private static List<String> ritualNames = new ArrayList<String>();
	private static HashMap<String, Ritual> rituals = new HashMap<String, Ritual>();

    Ritual() {

    }

	public static List<String> getRitualNames() {
		return ritualNames;
	}

    private Ritual(String[] file) {
        // the first line of the file contains the ritual's name
        name = file[0];
	    ritualNames.add(name);
	    // the second line of the file contains all of the gem combinations
        // they are stored in an alternating pattern of ROW/COL COLOUR
        // e.g. 01 RED
        String[] combinationLine = file[1].split(" ");

        // finds the width and height of the recipe
        int width = 0;
        int height = 0;
        for (int i = 0; i < combinationLine.length; i += 2) {
            if (Character.getNumericValue(combinationLine[i].charAt(0)) > height) {
                height = Character.getNumericValue(combinationLine[i].charAt(0));
            }
            if (Character.getNumericValue(combinationLine[i].charAt(1)) > width) {
                width = Character.getNumericValue(combinationLine[i].charAt(1));
            }
        }
        recipe = new GemColour[height + 1][width + 1];
        // places each GemColour into the appropriate row/col in the recipe

        for (int i = 0; i < combinationLine.length; i += 2) {
            int row = Character.getNumericValue(combinationLine[i].charAt(0));
            int col = Character.getNumericValue(combinationLine[i].charAt(1));
            recipe[row][col] = GemColour.valueOf(combinationLine[i + 1].trim());
        }
        int numEffects = 0;

        // the third line of the file and all following lines represent ritual effects
        // counts the number of effects
        for (int i = 2; i < file.length; i++) {
            numEffects++;
        }
        effects = new RitualEffect[numEffects];

        // adds each effect to the effects array
        for (int i = 2; i < file.length; i++) {
            String[] effect = file[i].split(" ");
            int modifier = Integer.parseInt(effect[1].substring(1));
			if (effect[1].charAt(0) == '-') {
				modifier *= -1;
			}
            effects[i - 2] = new RitualEffect(VillageModifier.valueOf(effect[0]), modifier);
        }
    }

    public static void load() {
        FileHandle dir = Gdx.files.internal("data/ritual");
        for (FileHandle file : dir.list()) {
            addRitual(file);
        }

    }

    public static void addRitual(FileHandle fileHandle) {
        Ritual ritual = new Ritual(fileHandle.readString().split("\r\n"));
        rituals.put(ritual.getName(), ritual);
        System.out.println(ritual.getName() + ritual.getEffects()[0].getModifier().toString() + ritual.getEffects()[0].getAmount());


//		for(int k = 0; k < ritual.getRecipe().length;k++){
//			for(int i = 0; i < ritual.getRecipe()[0].length;i++){
//				System.out.println(""+k+i+ritual.getRecipe()[k][i]);
//			}
//		}

    }

    public static Ritual getRitual(String ritualName) {
        if (rituals.containsKey(ritualName)) {
            return rituals.get(ritualName);
        }
        return null;
    }



    /**
     * Returns the recipe of GemColours for the ritual in a 2D array
     */
    public GemColour[][] getRecipe() {
        return recipe;
    }

    /**
     * Returns the effects of the ritual in an array
     */
    public RitualEffect[] getEffects() {
        return effects;
    }

    protected void commence() {

    }

    public static void setVillage(Village village) {
        Ritual.village = village;
    }

    public String getName() {
        return name;
    }

}
