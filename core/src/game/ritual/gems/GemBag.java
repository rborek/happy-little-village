package game.ritual.gems;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.GameObject;

public class GemBag extends GameObject {
    int[] gemAmounts;

    protected GemBag(Texture text, float xPos, float yPos) {
        super(text, xPos, yPos);
        gemAmounts = new int[GemColour.values().length];
    }

    public void add(GemColour colour) {
        gemAmounts[colour.ordinal()]++;
    }

    @Override
    public void update(float delta) {

    }
}
