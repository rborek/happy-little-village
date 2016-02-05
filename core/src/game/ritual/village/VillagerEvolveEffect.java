package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.Assets;

public class VillagerEvolveEffect extends VillagerEffect {
	public VillagerEvolveEffect(Villager villager) {
		super(villager);
	}

	@Override
	protected Texture[] getFrames() {
		String[] files = new String[7];
		for (int i = 1; i < 8; i++) {
			files[i - 1] = "villagers/evolve/evolve_sprite_" + i + ".png";
		}
		return Assets.getTextures(files);
	}


}
