package com.happylittlevillage.messages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.village.Village;

public class GemSummary extends MessageBox {
	private GemBag gemBag;
	private Village village;


	public GemSummary(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = " Gem Summary";
	}


	@Override
	public void render(Batch batch) {
		super.render(batch);

	}


}
