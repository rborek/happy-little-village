package game.ritual.village;

import java.util.ArrayList;

public class Village {
	private ArrayList<Villager> villagers;
	
	public Village(){
		this.villagers=  new ArrayList<Villager>() ;
	}
	
	public void addVillager(Villager a){
		villagers.add(a);
	}
	
	public void removeVillager(Villager a){
		villagers.remove(a);
	}
	
	public Villager getVillager(int a){
		return villagers.get(a);
	}
	
	public int getSize(){
		return villagers.size();
	}
}

