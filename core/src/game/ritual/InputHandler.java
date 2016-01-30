package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {

    public InputHandler() {

    }

    public void handleInput() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();
        }
    }

}
