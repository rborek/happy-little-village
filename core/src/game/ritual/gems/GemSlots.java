package game.ritual.gems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.GameObject;
import game.ritual.rituals.Ritual;

import java.util.ArrayList;

public class GemSlots extends GameObject {
    private Gem[] gems;
    private Rectangle[] slots;
    private int count;
    private ArrayList<Ritual> rituals;
    private Texture slotTexture = new Texture("gems/slot.png");
    private static final int spacing = 40;
    private static final int internalPadding = 2;
    private static final int slotSize = 68;

    public GemSlots(float xPos, float yPos, int rows, int cols) {
        super(new Texture("gems/slot.png"), xPos, yPos);
        height *= rows;
        height += spacing * rows - 1;
        width *= cols;
        width += spacing * cols - 1;
        gems = new Gem[rows * cols];
        slots = new Rectangle[rows * cols];
        for (int i = 0; i < gems.length / 2; i++) {
            float drawX = position.x + (i * slotSize);
            if (i != 0) {
                drawX += spacing;
            }
            slots[i] = new Rectangle(drawX, position.y, slotSize, slotSize);
        }
        for (int i = gems.length / 2; i < gems.length; i++) {
            float drawX = position.x - ((gems.length / 2) * slotSize) + (i * slotSize) + internalPadding;
            if (i != gems.length / 2) {
                drawX += spacing;
            }
            float drawY = position.y - slotSize - spacing + internalPadding;
            slots[i] = new Rectangle(drawX, drawY, slotSize, slotSize);
        }
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            useGems();
        }
    }

    @Override
    public void render(Batch batch) {
        for (Rectangle rect : slots) {
            batch.draw(slotTexture, rect.x, rect.y);
        }
        for (int i = 0; i < count; i++) {
            batch.draw(gems[i].getTexture(), position.x + (i * 64) + 8, position.y + 8, 48, 48);
        }
    }



    public int removeGem(float x, float y) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].contains(x, y)) {
                if (gems[i] != null) {
                    gems[i] = null;
                    count--;
                    return i;
                }
            }
        }
        return -1;
    }

    private void useGems() {
        for (Ritual ritual : rituals) {
            if (ritual.attempt(gems)) {
               removeAllGems();
            }
        }
    }

    public void add(Gem gem) {
        if (count < gems.length) {
            gems[count++] = gem;
        }
    }

    public GemColour getColour(int index) {
        if (gems[index] != null) {
            return gems[index].getColour();
        }
        return null;

    }

    private void removeAllGems() {
        for (int i = 0; i < gems.length; i++) {
            gems[i] = null;
        }
    }

}
