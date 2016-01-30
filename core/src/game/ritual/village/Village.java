package game.ritual.village;

import java.util.ArrayList;

public class Village {
	ArrayList<Villager> villagers;
	
	Village(ArrayList<Villager> villagers){
		this.villagers= villagers;
	}
	
	public void addVillager(Villager a){
		villagers.add(a);
	}
	
	public void removeVillager(Villager a){
		villagers.remove(a);
	}
	

}

