package com.happylittlevillage.gems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.rituals.RitualAltar;

import java.util.Random;

public class GemBag extends GameObject {
    private RitualAltar gemSlot;
    private static final int slotSize = 64;
    private static int PosX = 740;
    public static GameObject[] gemTextures = {
            new GameObject(Gem.getArrayOfTextures()[0], PosX, 310, slotSize, slotSize),
            new GameObject(Gem.getArrayOfTextures()[1], PosX, 390, slotSize, slotSize),
            new GameObject(Gem.getArrayOfTextures()[2], PosX, 470, slotSize, slotSize),
            new GameObject(Gem.getArrayOfTextures()[3], PosX, 550, slotSize, slotSize),};
    private Texture inactiveGem = Assets.getTexture("gems/gem_grey.png");
    private Rectangle[] slots = new Rectangle[4];
    private int[] gemAmounts = new int[GemColour.values().length];

    public GemBag(float xPos, float yPos) {
        super(Assets.getTexture("ui/gem_bag.png"), xPos, yPos);
        for (int i = 0; i < gemAmounts.length; i++) {
            gemAmounts[i] = 100;
        }
        slots[0] = new Rectangle(gemTextures[0].getPosition().x, gemTextures[0].getPosition().y, slotSize, slotSize);
        slots[1] = new Rectangle(gemTextures[1].getPosition().x, gemTextures[1].getPosition().y, slotSize, slotSize);
        slots[2] = new Rectangle(gemTextures[2].getPosition().x, gemTextures[2].getPosition().y, slotSize, slotSize);
        slots[3] = new Rectangle(gemTextures[3].getPosition().x, gemTextures[3].getPosition().y, slotSize, slotSize);
    }

    public void add(GemColour colour) {
        gemAmounts[colour.ordinal()]++;
    }

    public void add(Gem[][] colour) {
        for (int k = 0; k < colour.length; k++) {
            for (int i = 0; i < colour[0].length; i++) {
                if (colour[k][i] != null) {
                    gemAmounts[colour[k][i].getColour().ordinal()]++;
                }
            }
        }
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
//        batch.draw(texture, position.x, position.y);
        for (int i = 0; i < gemTextures.length; i++) {
            if (gemAmounts[i] != 0) {
                gemTextures[i].render(batch);

            } else {
                batch.draw(inactiveGem, slots[i].x, slots[i].y);
            }
        }
        for (int i = 0; i < gemTextures.length; i++) {
            Assets.getFont(24).draw(batch, "" + gemAmounts[i], slots[i].x + 30, slots[i].y + 16);
        }
    }

    public int getAmount(GemColour colour) {
        return gemAmounts[colour.ordinal()];
    }

    public GemColour gainRandomGem() {
        Random random = new Random();
        GemColour gemColour = GemColour.values()[(random.nextInt(GemColour.values().length))];
        add(gemColour);
        return gemColour;
    }

    public void remove(GemColour colour) {
        gemAmounts[colour.ordinal()]--;
    }

    @Override
    public void update(float delta) {

    }
}
