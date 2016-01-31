package game.ritual.gems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.GameObject;
import game.ritual.rituals.Ritual;

import java.util.ArrayList;

public class RitualAltar extends GameObject {
    private Gem[] gems;
    private GemBag gemBag;
    private Rectangle[] slots;
    private ArrayList<Ritual> rituals;
    private static final int spacingX = 136;
    private static final int spacingY = 121;
    private static final int paddingX = 60;
    private static final int paddingY = 67;
    private static final int slotSize = 64;

    public RitualAltar(GemBag gemBag, float xPos, float yPos, int rows, int cols) {
        super(new Texture("altar/altar1.png"), xPos, yPos);
//        height *= rows;
//        height += spacing * rows - 1;
//        width *= cols;
//        width += spacing * cols - 1;
        gems = new Gem[rows * cols];
        slots = new Rectangle[rows * cols];
        this.gemBag = gemBag;
        slots[0] = new Rectangle(paddingX, paddingY + 64 + spacingY, 64, 64);
        slots[1] = new Rectangle(paddingX + 64 + spacingX, paddingY + 64 + spacingY, 64, 64);
        slots[2] = new Rectangle(paddingX, paddingY, 64, 64);
        slots[3] = new Rectangle(paddingX + 64 + spacingX, paddingY, 64, 64);
        for (int i = 0; i < slots.length; i++) {
            slots[i].x += position.x;
            slots[i].y += position.y;
        }
    }

    public void setGemBag(GemBag gemBag) {
        this.gemBag = gemBag;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            useGems();
        }
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y);
        for (int i = 0; i < gems.length; i++) {
            if (gems[i] != null) {
                batch.draw(gems[i].getTexture(), slots[i].x+ 8, slots[i].y + 8);
            }
        }
    }



    public int removeGem(float x, float y) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].contains(x, y)) {
                if (gems[i] != null) {
                    gems[i] = null;
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
        for (int i = 0; i < gems.length; i++) {
            if (gems[i] == null) {
                gems[i] = gem;
                return;
            }
        }
    }

    public void add(Gem gem, float x, float y) {
        Rectangle gemBounds = new Rectangle(x - 32, y - 32, 64, 64);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].overlaps(gemBounds)) {
                if (gems[i] != null) {
                    gemBag.add(gems[i].getColour());
                }
                gems[i] = gem;
            }
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

