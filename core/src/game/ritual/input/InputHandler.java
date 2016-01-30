package game.ritual.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.gems.GemSlots;

public class InputHandler implements InputProcessor {
    private GemSlots gemSlots;
    private GemBag gemBag;
    boolean enabled = true;

    public InputHandler(GemSlots gemSlots, GemBag gemBag) {
        this.gemSlots = gemSlots;
        this.gemBag = gemBag;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }



    private void removeFromSlots(float mouseX, float mouseY) {
        int index = gemSlots.removeGem(mouseX, mouseY);
        if (index != -1) {
            GemColour colour = gemSlots.getColour(index);
            if (colour != null) {
                gemBag.add(colour);
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (enabled) {
            removeFromSlots(mouseX, mouseY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
