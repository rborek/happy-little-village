package game.ritual.rituals;

import com.badlogic.gdx.graphics.Texture;
import game.ritual.GameObject;

public class RitualBook extends GameObject {
    private final Texture[] pages = {new Texture("scroll/book1.png"),
            new Texture(("scroll/book2.png")), new Texture("scroll/book3.png")};

    protected RitualBook(Texture texture, float xPos, float yPos) {
        super(new Texture("scroll/sun.png"), xPos, yPos);
        width = 300;
        height = 300;
    }

    @Override
    public void update(float delta) {

    }
}
