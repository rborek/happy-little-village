package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;
import game.ritual.village.VillagerRole;

public class NewGem extends MessageBox {
	private GemBag gemBag;
	private Village village;
	private int RED;
	private int BLUE;
	private int GREEN;
	private int YELLOW;

	protected NewGem(GameHandler gameHandler) {
		super(null, gameHandler);
		// TODO Auto-generated constructor stub
	}

	protected NewGem(GemBag gemBag, Village village, GameHandler gameHandler) {
		super("", gameHandler);
		this.gemBag = gemBag;
		this.village = village;
		title = " Gem Summary";
	}

	public void gemMined() {
		for (int i = 0; i < village.getNumberOf(VillagerRole.MINER); i++) {
			if (gemBag.gainRandomGem().equals(GemColour.RED)) {
				RED += 5;
			}
			if (gemBag.gainRandomGem().equals(GemColour.YELLOW)) {
				YELLOW += 5;
			}
			if (gemBag.gainRandomGem().equals(GemColour.BLUE)) {
				BLUE += 5;
			}
			if (gemBag.gainRandomGem().equals(GemColour.GREEN)) {
				GREEN += 5;
			}
		}
	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, "The amount of mined red gem is:" + RED, position.x + 20, position.y + 200);
		font.draw(batch, "The amount of mined blue gem is:" + YELLOW, position.x + 20, position.y + 250);
		font.draw(batch, "The amount of mined green gem is:" + GREEN, position.x + 20, position.y + 150);
		font.draw(batch, "The amount of mined yellow gem is:" + BLUE, position.x + 20, position.y + 100);

	}

}
