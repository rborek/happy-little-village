package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.ritual.gems.GemBag;
import game.ritual.village.Village;

public class NewGem extends MessageBox {
	private GemBag gemBag;
	private Village village;
	protected NewGem(GameHandler gameHandler) {
		super(null, gameHandler);
		// TODO Auto-generated constructor stub
	}
	
	protected NewGem(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
	}
	
	@Override
	public void render(Batch batch){
		super.render(batch);
	}
	

}
