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
	private int RED;
	private int BLUE;
	private int GREEN;
	private int YELLOW;

	public GemSummary(GameHandler gameHandler) {
		super(null, gameHandler);
		// TODO Auto-generated constructor stub
	}

	public GemSummary(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = " Gem Summary";
	}

	public void gemMined() {
		for (int i = 0; i < village.getNumberOf(VillagerRole.MINER) * 3; i++) {
			GemColour g = gemBag.gainRandomGem();
			if (g.equals(GemColour.RED)) {
				RED += 1;
			}
			if (g.equals(GemColour.YELLOW)) {
				YELLOW += 1;
			}
			if (g.equals(GemColour.BLUE)) {
				BLUE += 1;
			}
			if (g.equals(GemColour.GREEN)) {
				GREEN += 1;
			}
		}
	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, "Red gems mined \n" + RED, position.x + 190, position.y + 430);
		font.draw(batch, "Yellow gems mined \n" + YELLOW, position.x + 190, position.y + 340);
		font.draw(batch, "Green gems mined \n" + GREEN, position.x + 190, position.y + 250);
		font.draw(batch, "Blue gems mined \n" + BLUE, position.x + 190, position.y + 160);

	}

}
