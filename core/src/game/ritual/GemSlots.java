package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class GemSlots extends GameObject {
    Gem[] gems;
    ArrayList<Ritual> rituals;

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

    private void useGems() {
        for (Ritual ritual : rituals) {
            ritual.attempt(gems);
        }
    }
}
