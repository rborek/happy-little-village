package game.ritual.gems;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.GameObject;

public class GemBag extends GameObject {
    private GemSlots gemSlot;
    private int[] gemAmounts = new int[GemColour.values().length];


    public GemBag(Texture text, float xPos, float yPos) {
        super(text, xPos, yPos);
        gemAmounts = new int[GemColour.values().length];
    }

    public void add(GemColour colour) {
        System.out.println(gemAmounts[0]);
        gemAmounts[colour.ordinal()]++;
    }

    public void remove(GemColour colour) {
        gemAmounts[colour.ordinal()]--;
    }

    @Override
    public void update(float delta) {

    }
}
