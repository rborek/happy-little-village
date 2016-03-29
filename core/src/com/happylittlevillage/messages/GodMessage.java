package com.happylittlevillage.messages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.village.Village;

public class GodMessage extends MessageBox {
	private GemBag gemBag;
	private Village village;



	public GodMessage(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = "God Message";
	}


	@Override
	public void render(Batch batch) {
		super.render(batch);

	}

}
