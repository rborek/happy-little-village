package game.ritual.gems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.GameObject;
import game.ritual.rituals.Ritual;

import java.util.ArrayList;

public class GemSlots extends GameObject {
    private Gem[] gems;
    private int count;
    private ArrayList<Ritual> rituals;

    public GemSlots(Texture text, float xPos, float yPos, int size) {
        super(text, xPos, yPos);
        gems = new Gem[size];
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            useGems();
        }
    }

    @Override
    public void render(Batch batch) {
        for (int i = 0; i < gems.length; i++) {
            batch.draw(gems[i].getTexture(), position.x + (i * 128), position.y);
        }
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
            gems[count] = gem;
        }
    }

    private void removeAllGems() {
        for (int i = 0; i < gems.length; i++) {
            gems[i] = null;
        }
    }

}
