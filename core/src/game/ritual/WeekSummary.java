package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import game.ritual.village.Village;

public class WeekSummary extends MessageBox {
	private Village village;
	
	protected WeekSummary(Texture texture, float xPos, float yPos, Village village) {
		super(texture, xPos, yPos);
		this.village = village;
	}
	

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean checkClick(float x, float y){
		Rectangle r = new Rectangle(continueX,continueY,continueButton.getWidth(),continueButton.getHeight());
		if(r.contains(x, y)){
			click = true; 
		}
		return false;
	}
	
	@Override
	public void render( Batch batch){
		super.render(batch);
		//batch.draw(texture,position.x,position.y);
		font.draw(batch, "The amount of consumed food is:"+ village.getConsumedFood(), position.x+20, position.y+200);
		font.draw(batch, "The amount of gathered food is:"+ village.getGatheredFood(), position.x+20, position.y+300);		
		font.draw(batch, "The remaining amount of food is:"+ village.getFood(), position.x+20, position.y+100);
		
		font.draw(batch, "The amount of consumed water is:"+ village.getConsumedWater(), position.x+260, position.y+200);
		font.draw(batch, "The amount of gathered water is:"+ village.getGatheredWater(), position.x+260, position.y+300);
		font.draw(batch, "The remaining amount of water is:"+ village.getWater(), position.x+260, position.y+100);
		
		font.draw(batch, "Population of the village:"+ village.getPop(), position.x+600, position.y+200);
		font.draw(batch, "Number of village created:"+ village.getVillagerAdded(), position.x+600, position.y+300);
		font.draw(batch, "Number of village lost:"+ (village.getPop()-village.getVillagerAdded()), position.x+600, position.y+100);

	}
	
}
