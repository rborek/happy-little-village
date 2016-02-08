package com.happylittlevillage.village;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;

public class VillageInformation extends GameObject {
	private BitmapFont resourceFont;
	private BitmapFont statusFont;
	private int food;
	private int water;
	private int pop;
	private int hour;
	private int dayLeft;
	private int day;
	// add file to constructors
	private Texture foodTexture = Assets.getTexture("ui/food.png");
	private Texture waterTexture = Assets.getTexture("ui/water.png");
	private Texture popTexture;
	private Village villagers;


	protected VillageInformation(Village villagers,float xPos, float yPos) {
		super(Assets.getTexture("villagers/info_menu.png"), xPos, yPos);
		setResources(villagers);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		parameter.size = 36;
		resourceFont = generator.generateFont(parameter);
		parameter.size = 30;
		statusFont = generator.generateFont(parameter);
		this.villagers = villagers;
	}

	public void setResources(Village villagers) {
		this.food = (int) Math.ceil(villagers.getFood());
		this.water = (int) Math.ceil(villagers.getWater());
		this.pop = villagers.getPop();
		this.hour = (int) Math.ceil(villagers.getHoursLeft());
		this.day = (int) Math.ceil(villagers.getDay());
		this.dayLeft = villagers.getDaysLeft();
		//System.out.print(""+ food + water + pop);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
		batch.draw(foodTexture, position.x + 20, 65);
		batch.draw(waterTexture, position.x + 140, 70);
		resourceFont.draw(batch, "" + villagers.getFood(), position.x + 80, position.y + 90);
		resourceFont.draw(batch, "" + villagers.getWater(), position.x + 190, position.y + 90);
		statusFont.draw(batch, "Pop: " + villagers.getPop(), position.x + 300, 120);
		statusFont.draw(batch, "Hours: " + (int) Math.ceil(villagers.getHoursLeft()), position.x + 300, 80);
		statusFont.draw(batch, "Days elapsed: " + (int) Math.ceil(villagers.getDay()), position.x + 405, 120);
		statusFont.draw(batch, "Days left: " + villagers.getDaysLeft(), position.x + 405, 80);
	}

}
