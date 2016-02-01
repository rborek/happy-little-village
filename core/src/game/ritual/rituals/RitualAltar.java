package game.ritual.rituals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.GameObject;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.rituals.Ritual;

import java.util.ArrayList;

public class RitualAltar extends GameObject {
    private Gem[] gems;
    private GemBag gemBag;
    private Rectangle[] slots;
    private Texture[] animation = {new Texture("altar/altar2.png"), new Texture("altar/altar3.png"),
            new Texture("altar/altar4.png"), new Texture("altar/altar3.png"), new Texture("altar/altar2.png")};
    private Texture button = new Texture("scroll/button.png");
    private ArrayList<Ritual> rituals = new ArrayList<Ritual>();
    private static final int spacingX = 136;
    private static final int spacingY = 121;
    private static final int paddingX = 60;
    private static final int paddingY = 67;
    private static final int slotSize = 64;
    private boolean animating = false;
    private float timer = 0;

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
        gainStartingRituals();
    }

    private void gainStartingRituals() {
        gainRitual(new AddFoodRitual());
        gainRitual(new AddFoodLoseWaterRitual());
        gainRitual(new AddFoodLoseWaterRitual());
        gainRitual(new AddVillagerRitual());
        gainRitual(new AddWaterRemoveFoodRitual());
        gainRitual(new AddWaterRitual());
        gainRitual(new ToExplorerRitual());
        gainRitual(new ToFarmerRitual());
        gainRitual(new ToMinerRitual());
        gainRitual(new RemoveVillagerRitual());
    }


    public boolean gainRitual(Ritual ritual) {
        int ritualID = ritual.getID();
        for (Ritual ritualToCheck : rituals) {
            if (ritualID == ritualToCheck.getID()) {
                return false;
            }
        }
        rituals.add(ritual);
        return true;

    }

    public void setGemBag(GemBag gemBag) {
        this.gemBag = gemBag;
    }

    @Override
    public void update(float delta) {
        if (animating) {;
            int frame = (int) ((timer * 8) % animation.length);
            texture = animation[frame];
            timer += delta;
            if (timer * 8 >= 5) {
                animating = false;
                timer = 0;
                texture = new Texture("altar/altar1.png");
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            useGems();
        }
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y);
        batch.draw(button, position.x + (width / 2) - (button.getWidth() / 2), position.y + 30);
        for (int i = 0; i < gems.length; i++) {
            if (gems[i] != null) {
                batch.draw(gems[i].getTexture(), slots[i].x+ 8, slots[i].y + 8);
            }
        }
    }

    public void removeRitual(Ritual ritual) {
        for (int i = 0; i < rituals.size(); i++) {
            if (ritual.getID() == rituals.get(i).getID()) {
                rituals.remove(i);
            }
        }
    }

    public Gem pickUpGem(float x, float y) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].contains(x, y)) {
                if (gems[i] != null) {
                    Gem gemToReturn = gems[i];
                    gems[i] = null;
                    return gemToReturn;
                }
            }
        }
        return null;
    }

    public void startAnimating() {
        animating = true;
    }

    public void useGems() {
        for (Ritual ritual : rituals) {
            if (ritual.attempt(gems)) {
                System.out.println(ritual.getID());
                startAnimating();
                break;
            }
        }
        removeAllGems();
    }

    public boolean add(Gem gem, float x, float y) {
        Rectangle gemBounds = new Rectangle(x - 32, y - 32, 64, 64);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].overlaps(gemBounds)) {
                if (gems[i] != null) {
                    gemBag.add(gems[i].getColour());
                }
                gems[i] = gem;
                return true;
            }
        }
        return false;
    }


    public GemColour getColour(int index) {
        if (gems[index] != null) {
            System.out.println("not null");
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

