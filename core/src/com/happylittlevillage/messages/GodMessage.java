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
	private int timesPerformed;
	private int timesToDo;
	private Texture[][] gems;


	public GodMessage(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = "God Message";
	}

	public boolean checkRitual() {
		if (village.getWeeklyRitual().isComplete()) {
			village.setDaysLeft(7);
			return true;
		} else {
			timesToDo = village.getWeeklyRitual().getTimesToDo();
			timesPerformed = village.getWeeklyRitual().getTimesPerformed();
			return false;
		}
	}

	public void stateRitual() {
		text += " \n\n              is the ritual";
		GemColour[][] colours = village.getWeeklyRitual().getRecipe();
		Texture[] textures = Gem.getArrayOfTextures();
		gems = new Texture[colours.length][colours[0].length];
		for (int i = 0; i < gems.length; i++) {
			for (int j = 0; j < gems[0].length; j++) {
				if (colours[i][j] != null) {
					System.out.println(colours[i][j]);
					gems[i][j] = textures[colours[i][j].ordinal()];
				}
			}
		}

	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, text, position.x + 70, position.y + 275);
		for (int i = 0; i < gems.length; i++) {
			for (int j = 0; j < gems[0].length; j++) {
				if (gems[i][j] != null) batch.draw(gems[i][j], 156 + 64 * j, 300 - 64 * i, 64, 64);
			}
		}
		int howMany = village.getWeeklyRitual().getTimesToDo() - village.getWeeklyRitual().getTimesPerformed();
		font.draw(batch, "Number of times to complete: " + howMany, position.x + 70, position.y + 335);
		font.draw(batch, "Days left to complete weekly ritual: " + village.getDaysLeft(), position.x + 70, position.y + 390);

	}

}
