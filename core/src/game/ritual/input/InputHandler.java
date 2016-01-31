package game.ritual.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.gems.GemSlots;

public class InputHandler implements InputProcessor {
    private GemSlots gemSlots;
    private GemBag gemBag;
    private Gem selectedGem;
    boolean enabled = true;

    public InputHandler(GemSlots gemSlots, GemBag gemBag) {
        this.gemSlots = gemSlots;
        this.gemBag = gemBag;
    }

    public void renderSelectedGem(Batch batch) {
        if (selectedGem != null) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            selectedGem.render(batch, mouseX - 32, mouseY - 32);
        }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    private void pickUpGem(float mouseX, float mouseY) {
        if (selectedGem == null) {
            Gem potentialGem = gemBag.pickUpGem(mouseX, mouseY);
            if (potentialGem != null) {
                selectedGem = potentialGem;
            }
        }
    }

    private void dropGem(float mouseX, float mouseY) {
        gemSlots.add(selectedGem, mouseX, mouseY);
        selectedGem = null;

    }

    private void addToSlots(float mouseX, float mouseY) {

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
            pickUpGem(mouseX, mouseY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        dropGem(mouseX, mouseY);
        return true;
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
