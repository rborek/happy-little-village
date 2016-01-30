package game.ritual.village;

import java.util.ArrayList;

public class Village {
	private ArrayList<Villager> villagers;
	
	public Village(ArrayList<Villager> villagers){
		this.villagers= villagers;
	}
	
	public void addVillager(Villager a){
		villagers.add(a);
	}
	
	public void removeVillager(Villager a){
		villagers.remove(a);
	}
	

}

