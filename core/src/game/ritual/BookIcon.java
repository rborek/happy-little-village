package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ryan on 1/31/2016.
 */
public class BookIcon extends GameObject {
    GameHandler gameHandler;
    boolean opened = false;

    public BookIcon(GameHandler gameHandler) {
        super(new Texture("scroll/small_book.png"), 675, 50);
        this.gameHandler = gameHandler;
    }

    public void open(float x, float y) {
        Rectangle r = new Rectangle(position.x, position.y, width, height);
        if (r.contains(x, y)) {
            if (!opened) {
                gameHandler.openBook();
                opened = true;
                texture = new Texture("scroll/small_book_open.png");
            } else {
                gameHandler.closeBook();
                opened = false;
                texture = new Texture("scroll/small_book.png");
            }

        }
    }

    @Override
    public void update(float delta) {

    }
}
