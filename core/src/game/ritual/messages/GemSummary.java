package game.ritual.messages;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.GameHandler;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;
import game.ritual.village.VillagerRole;

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
		font.draw(batch, "Red gems mined \n" + village.getMinedGems()[0], position.x + 190, position.y + 430);
		font.draw(batch, "Yellow gems mined \n" + village.getMinedGems()[1], position.x + 190, position.y + 340);
		font.draw(batch, "Green gems mined \n" + village.getMinedGems()[2], position.x + 190, position.y + 250);
		font.draw(batch, "Blue gems mined \n" + village.getMinedGems()[3], position.x + 190, position.y + 160);

	}

}
