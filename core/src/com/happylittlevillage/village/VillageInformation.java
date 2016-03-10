package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;

public class VillageInformation extends GameObject {

	// add file to constructors
	private Texture foodTexture = Assets.getTexture("ui/food.png");
	private Texture waterTexture = Assets.getTexture("ui/water.png");
	private Texture popTexture;
	private Village village;
	private BitmapFont font = new BitmapFont();
	private String[] addedResource = new String[3];
	protected VillageInformation(Village village,float xPos, float yPos) {
		super(Assets.getTexture("ui/info_menu.png"), xPos, yPos);
		this.village = village;
	}
	float alpha = 0;
	float moveToY = 200;
	public void getAddedResource(String resource, float amount){

		if(resource.equals("food")){
			addedResource[0] = amount+"    food";
		}
		else if(resource.equals("water")){
			addedResource[1] = amount+"    water";
		}
		else if(resource.equals("happiness")){
			addedResource[2] = amount+"    happiness";
		}

	}


	@Override
	public void update(float delta) {
		for(String resource : addedResource){
			if(resource!=null){
				updateMotion(delta);
			}
		}
	}

	@Override
	public void render(Batch batch) {
		moveAndFade(batch);
		batch.draw(texture, position.x, position.y);
		batch.draw(foodTexture, position.x + 20, 65);
		batch.draw(waterTexture, position.x + 140, 70);
		Assets.getFont(36).draw(batch, "" + village.getFood(), position.x + 80, position.y + 90);
		Assets.getFont(36).draw(batch, "" + village.getWater(), position.x + 190, position.y + 90);
		Assets.getFont(36).draw(batch, "" + village.getHappiness(), position.x + 270, position.y + 90);
		Assets.getFont(30).draw(batch, "Pop: " + village.getPop(), position.x + 300, 120);
		Assets.getFont(30).draw(batch, "Hours: " + (int) Math.ceil(village.getHoursLeft()), position.x + 300, 80);
		Assets.getFont(30).draw(batch, "Days elapsed: " + (int) Math.ceil(village.getDay()), position.x + 405, 120);
		Assets.getFont(30).draw(batch, "Days left: " + village.getDaysLeft(), position.x + 405, 80);
	}

	private void moveAndFade(Batch batch){
		Assets.getFont(40).setColor(1,1,1,alpha);
		for(int index = 0; index < addedResource.length; index++){
			if(addedResource[index]!= null){
				Assets.getFont(30).draw(batch, addedResource[index], 400+index*50, moveToY);
			}
		}

	}
	private void updateMotion(float delta) {
		int startY =200;
		float endY = 600;
		float moveY = (endY - startY) * delta / 3;
		moveToY += moveY;
		alpha += delta;
		if(alpha > 0.99){
			alpha = 0;
		}
	}
}
