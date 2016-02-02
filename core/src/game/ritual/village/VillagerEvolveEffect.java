package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;

public class VillagerEvolveEffect extends VillagerEffect {
	public VillagerEvolveEffect(Villager villager) {
		super(villager);
	}

	@Override
	protected Texture[] getFrames() {
		return new Texture[]{
				new Texture("villagers/evolve/evolve_sprite_1.png"),
				new Texture("villagers/evolve/evolve_sprite_2.png"),
				new Texture("villagers/evolve/evolve_sprite_3.png"),
				new Texture("villagers/evolve/evolve_sprite_4.png"),
				new Texture("villagers/evolve/evolve_sprite_5.png"),
				new Texture("villagers/evolve/evolve_sprite_6.png"),
				new Texture("villagers/evolve/evolve_sprite_7.png")
		};
	}


}
