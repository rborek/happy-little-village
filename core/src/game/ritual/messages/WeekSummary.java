package game.ritual.messages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.GameHandler;
import game.ritual.village.Village;

public class WeekSummary extends MessageBox {
	private Village village;


	public WeekSummary(Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.village = village;
		title = "Resource Summary";
	}


	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}


	@Override
	public void render(Batch batch) {
		super.render(batch);
		//batch.draw(texture,position.x,position.y);
		position.y -= 20;
		font.draw(batch, "Food Consumed: " + village.getConsumedFood(), position.x + 200, position.y + 430);
		font.draw(batch, "Food Gathered: " + village.getGatheredFood(), position.x + 200, position.y + 390);
		font.draw(batch, "Food Total: " + village.getFood(), position.x + 200, position.y + 350);

		font.draw(batch, "Water Consumed: " + village.getConsumedWater(), position.x + 200, position.y + 310);
		font.draw(batch, "Water Gathered: " + village.getGatheredWater(), position.x + 200, position.y + 270);
		font.draw(batch, "Water Total: " + village.getWater(), position.x + 200, position.y + 230);

		font.draw(batch, "Population: " + village.getPop(), position.x + 200, position.y + 190);
		position.y += 20;
//		font.draw(batch, "Villagers Created: "+ village.getVillagerAdded(), position.x+200, position.y+150);
//		font.draw(batch, "Villagers Lost: "+ (village.getPop()-village.getVillagerAdded()), position.x+200, position.y+110);


	}

}
