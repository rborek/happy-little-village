package game.ritual.rituals;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.GameObject;

public class RitualBookEntry extends GameObject {

    protected RitualBookEntry(Texture texture, float xPos, float yPos) {
        super(new Texture("scroll/sun.png"), xPos, yPos);
        width = 300;
        height = 300;
    }

    @Override
    public void update(float delta) {

    }
}
