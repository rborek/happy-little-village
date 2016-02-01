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
	public void checkClick(float x, float y ){
		Rectangle r = new Rectangle(continueX, continueY, continueButton.getWidth(), continueButton.getHeight());
        //System.out.println(continueX + ", " + continueY);
        if (r.contains(x, y)) {
            gameHandler.unpause();
        }
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void render( Batch batch){
		super.render(batch);
		//batch.draw(texture,position.x,position.y);
		font.draw(batch, "Food Consumed:"+ village.getConsumedFood(), position.x+100, position.y+440);
		font.draw(batch, "Food Gathered:"+ village.getGatheredFood(), position.x+100, position.y+400);
		font.draw(batch, "Food Total:"+ village.getFood(), position.x+100, position.y+360);
		
		font.draw(batch, "Water Consumed:"+ village.getConsumedWater(), position.x+100, position.y+320);
		font.draw(batch, "Water Gathered:"+ village.getGatheredWater(), position.x+100, position.y+260);
		font.draw(batch, "Water Total:"+ village.getWater(), position.x+100, position.y+220);
		
		font.draw(batch, "Population:"+ village.getPop(), position.x+100, position.y+200);
		font.draw(batch, "Villagers Created:"+ village.getVillagerAdded(), position.x+100, position.y+175);
		font.draw(batch, "Villagers Lost:"+ (village.getPop()-village.getVillagerAdded()), position.x+100, position.y+150);

	}
	
}
