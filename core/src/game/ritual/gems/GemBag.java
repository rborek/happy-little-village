package game.ritual.gems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.GameObject;

public class GemBag extends GameObject {
    private GemSlots gemSlot;
    private Texture[] gemTextures = Gem.getArrayOfTextures();
    private static final int slotSize = 64;
    private Rectangle[] slots = new Rectangle[4];
    private int[] gemAmounts = new int[GemColour.values().length];


    public GemBag(float xPos, float yPos) {
        super(new Texture("gems/gem_bag.png"), xPos, yPos);
        gemAmounts = new int[GemColour.values().length];
        slots[0] = new Rectangle(42, 54, slotSize, slotSize);
        slots[1] = new Rectangle(135, 54, slotSize, slotSize);
        slots[2] = new Rectangle(231, 54, slotSize, slotSize);
        slots[3] = new Rectangle(322, 54, slotSize, slotSize);
        for (int i = 0; i < slots.length; i++) {
            slots[i].x += position.x;
            slots[i].y += position.y;
        }
    }

    public void add(GemColour colour) {
        System.out.println(gemAmounts[0]);
        gemAmounts[colour.ordinal()]++;
    }

    public Gem pickUpGem(float x, float y) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].contains(x, y)) {
                return new Gem(GemColour.values()[i]);
            }
        }
        return null;
    }


    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y);
        for (int i = 0; i < gemTextures.length; i++) {
            batch.draw(gemTextures[i], slots[i].x, slots[i].y);
        }
    }



    public void remove(GemColour colour) {
        gemAmounts[colour.ordinal()]--;
    }

    @Override
    public void update(float delta) {

    }
}
