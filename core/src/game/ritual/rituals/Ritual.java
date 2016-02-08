package game.ritual.rituals;

import game.ritual.gems.Gem;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Ritual {
	public int id;
	protected static Village village;
	protected GemColour[] gemCombination;
	GemColour[][] recipe = new GemColour[3][3];
	protected  static ArrayList<Ritual> a ;

	public Ritual() {
		gemCombination = getCombination();
	}

	//Call file from Assests.getRitual. Read the first line and return positions of gem to recipe
	public Ritual(BufferedReader a){
		String text ="";
		try {
			text = a.readLine();
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file with path");
		}
		String[] parse = text.split(" ");
		int x=0;
		int y=0;
		int i=0;
		while(i<parse.length){
			if (i%2==0){
				x=(int)parse[i].charAt(0);
				y=(int)parse[i].charAt(1);
				i++;
			}
			else{
				recipe[x][y]= GemColour.valueOf(parse[i]);
				i++;
			}
		}
	}
	public boolean attempt(Gem[] gems) {
		GemColour[] gemsToUse = new GemColour[gems.length];
		for (int i = 0; i < gems.length; i++) {
			if (gems[i] != null) {
				gemsToUse[i] = gems[i].getColour();
			}
		}
		if (gemCombination.length != gemsToUse.length) {
			return false;
		}
		boolean working = true;
		for (int i = 0; i < gemCombination.length; i++) {
			if (gemCombination[i] != gemsToUse[i]) {
				working = false;
			}
			if (!working) {
				return false;
			}
		}
		commence();
		return true;
	}

	protected abstract GemColour[] getCombination();

	protected abstract void commence();

	public static void setVillage(Village village) {
		Ritual.village = village;
	}

	public int getID() {
		return id;
	}

}
