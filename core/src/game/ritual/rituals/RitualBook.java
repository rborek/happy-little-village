package game.ritual.rituals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.GameObject;

public class RitualBook extends GameObject {
    private final Texture[] pages = {new Texture("scroll/book1.png"),
            new Texture(("scroll/book2.png")), new Texture("scroll/book3.png")};
    private int pageNumber = 1;
    private Rectangle leftArrow;
    private Rectangle rightArrow;

    public RitualBook(float xPos, float yPos) {
        super(new Texture("scroll/book1.png"), xPos, yPos);
        leftArrow = new Rectangle(xPos + 75, yPos + 20, 70, 45);
        rightArrow = new Rectangle(xPos + 495, yPos + 20, 70, 45);
    }

    public void click(float x, float y) {
        if (leftArrow.contains(x, y)) {
            turnLeft();
        } else if (rightArrow.contains(x, y)) {
            turnRight();
        }
        updateTexture();
    }

    public void turnLeft() {
        if (pageNumber == 1) {
            pageNumber = 3;
        } else {
            pageNumber--;
        }
    }

    public void turnRight() {
        if (pageNumber == 3) {
            pageNumber = 1;
        } else {
            pageNumber++;
        }
    }

    public void updateTexture() {
        texture = pages[pageNumber - 1];
    }

    @Override
    public void update(float delta) {

    }
}
