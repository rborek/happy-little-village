package game.ritual;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.ritual.gems.Gem;
import game.ritual.gems.GemColour;
import game.ritual.village.Village;
import game.ritual.village.Villager;
import game.ritual.village.VillagerRole;

public class GameHandler {
    private Village village;
    private Gem gem;

    public GameHandler() {
        init();
    }


    public void init() {
        gem = new Gem(500, 300, GemColour.RED);
        village = new Village();
        village.addVillager(new Villager(VillagerRole.CITIZEN));
    }

    // game logic goes here
    public void update(float delta) {
        village.update(delta);
    }

    // rendering goes here
    public void render(Batch batch) {
        village.render(batch);
        gem.render(batch, 250, 250);
    }


}
