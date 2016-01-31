package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import game.ritual.village.VillageInformation;

public class Summary extends GameObject {
	private BitmapFont font;
	private VillageInformation info;
	private int food;
	private int water;
	private int pop;
	
	protected Summary(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		//info = new VillageInformation;
		// TODO Auto-generated constructor stub
		
	}
	
	public void getInfo(int food, int water, int pop){
		this.food = food;
		this.water = water;
		this.pop = pop;
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}
	
	

}
