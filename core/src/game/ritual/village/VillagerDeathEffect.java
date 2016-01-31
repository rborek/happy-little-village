package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;

public class VillagerDeathEffect extends VillagerEffect {
    public VillagerDeathEffect(Villager villager) {
        super(villager);
    }

    @Override
    protected Texture[] getFrames() {
        return new Texture[] {
                new Texture("villagers/death/death_sprite_1.png"),
                new Texture("villagers/death/death_sprite_2.png"),
                new Texture("villagers/death/death_sprite_3.png"),
                new Texture("villagers/death/death_sprite_4.png"),
                new Texture("villagers/death/death_sprite_5.png"),
                new Texture("villagers/death/death_sprite_6.png"),
                new Texture("villagers/death/death_sprite_7.png")
        };
    }


}
