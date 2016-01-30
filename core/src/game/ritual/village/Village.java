package game.ritual.village;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

public class Village {
	private ArrayList<Villager> villagers;
	
	public Village(){
		this.villagers=  new ArrayList<Villager>() ;
	}

	public void update(float delta) {
		for (Villager villager : villagers) {
			villager.update(delta);
		}

	}

	private void updateResources(float delta) {
		// TODO
		// update resources here (e.g. food being eaten)
	}

	public void render(Batch batch) {
		for (Villager villager : villagers) {
			villager.render(batch);
		}
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

