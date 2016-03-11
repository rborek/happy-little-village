package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;

import java.util.ArrayList;

public class VillageInformation extends GameObject {

	// add file to constructors
	private Texture foodTexture = Assets.getTexture("ui/food.png");
	private Texture waterTexture = Assets.getTexture("ui/water.png");
	private Texture popTexture;
	private Village village;
	private BitmapFont font = new BitmapFont();
	private ArrayList<InformationFlash> addedResource = new ArrayList<InformationFlash>();
	protected VillageInformation(Village village,float xPos, float yPos) {
		super(Assets.getTexture("ui/info_menu.png"), xPos, yPos);
		this.village = village;
	}
	public void getAddedResource(String resource, float amount){
		if(resource.equals("food")){
			addedResource.add(new InformationFlash(amount+" food", 0));
		}
		else if(resource.equals("water")){
			addedResource.add(new InformationFlash(amount+" water", 1));
		}
		else if(resource.equals("happiness")){
			addedResource.add(new InformationFlash(amount+" happiness",2));
		}
		System.out.println("SIZE"+addedResource.size());
	}


	@Override
	public void update(float delta) {
		for(int index = 0; index < addedResource.size();index ++){
			if(addedResource.get(index)!=null){
				addedResource.get(index).updateMotion(delta,true);
				if(addedResource.get(index).getAlpha()<=0){
					addedResource.remove(addedResource.get(index));
				}
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
		for(InformationFlash resource : addedResource){
			if(resource!= null){
				Assets.getFont(40).setColor(1,1,1,resource.getAlpha());
				Assets.getFont(40).draw(batch, resource.getInfo(), 100+resource.getRelativePosition()*50, resource.getmoveToY());
			}
		}
	}

}
