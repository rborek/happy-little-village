package game.ritual;

import com.badlogic.gdx.graphics.g2d.Batch;

import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.messages.MessageBox;
import game.ritual.village.Village;
import game.ritual.village.VillagerRole;

public class GemSummary extends MessageBox {
	private GemBag gemBag;
	private Village village;
	private int RED;
	private int BLUE;
	private int GREEN;
	private int YELLOW;

	protected GemSummary(GameHandler gameHandler) {
		super(null, gameHandler);
		// TODO Auto-generated constructor stub
	}

	protected GemSummary(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = " Gem Summary";
	}

	public void gemMined() {
		for (int i = 0; i < village.getNumberOf(VillagerRole.MINER); i++) {
			if (gemBag.gainRandomGem().equals(GemColour.RED)) {
				RED += 3;
			}
			if (gemBag.gainRandomGem().equals(GemColour.YELLOW)) {
				YELLOW += 3;
			}
			if (gemBag.gainRandomGem().equals(GemColour.BLUE)) {
				BLUE += 3;
			}
			if (gemBag.gainRandomGem().equals(GemColour.GREEN)) {
				GREEN += 3;
			}
		}
	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, "The amount of mined red gem is:" + RED, position.x + 70, position.y + 250);
		font.draw(batch, "The amount of mined blue gem is:" + YELLOW, position.x + 70, position.y + 280);
		font.draw(batch, "The amount of mined green gem is:" + GREEN, position.x + 70, position.y + 310);
		font.draw(batch, "The amount of mined yellow gem is:" + BLUE, position.x + 70, position.y + 340);

	}

}
