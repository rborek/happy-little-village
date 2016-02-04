package game.ritual.menu;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class MenuItem {

    public boolean interact(float mouseX, float mouseY) {
        return false;
    }

    public void render(Batch batch) {

    }

    public abstract void use();

}
