package game.ritual.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import game.ritual.GameHandler;
import game.ritual.GameScreen;
import game.ritual.gems.GemBook;
import game.ritual.gems.Gem;
import game.ritual.gems.GemBag;
import game.ritual.gems.GemColour;
import game.ritual.messages.MessageBox;
import game.ritual.rituals.RitualAltar;
import game.ritual.rituals.RitualBook;

public class InputHandler implements InputProcessor {
	private GameScreen screen;
	private RitualAltar ritualAltar;
	private GemBag gemBag;
	private Gem selectedGem;
	private RitualBook ritualBook;
	private MessageBox messageBox;
	private GemBook miniBook;
	boolean enabled = true;


	public InputHandler(GameScreen screen) {
		this.screen = screen;
	}

	public void linkTo(GameHandler gameHandler) {
		this.ritualAltar = gameHandler.getRitualAltar();
		this.gemBag = gameHandler.getGemBag();
		this.messageBox = gameHandler.getMessageBox();
		this.ritualBook = gameHandler.getRitualBook();
		this.miniBook = gameHandler.getMiniBook();
	}

	public void renderSelectedGem(Batch batch) {
		if (selectedGem != null) {
			Vector2 realPos = screen.getRealScreenPos(Gdx.input.getX(), Gdx.input.getY());
			selectedGem.render(batch, realPos.x - 32, realPos.y - 32);
		}
	}

	private void tryToOpenBook(float mouseX, float mouseY) {
		miniBook.toggle(mouseX, mouseY);
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	public void setMessageBox(MessageBox message) {
		this.messageBox = message;
	}

	private void checkContinue(float mouseX, float mouseY) {
		messageBox.checkClick(mouseX, mouseY);
	}

	private void pickUpGem(float mouseX, float mouseY) {
		if (selectedGem == null) {
			Gem potentialGem = gemBag.pickUpGem(mouseX, mouseY);
			if (potentialGem != null) {
				GemColour gemColour = potentialGem.getColour();
				if (gemBag.getAmount(gemColour) > 0) {
					selectedGem = potentialGem;
					gemBag.remove(gemColour);
				}
			}
		}
	}

	private void dropGem(float mouseX, float mouseY) {
		if (selectedGem != null) {
			if (!ritualAltar.add(selectedGem, mouseX, mouseY)) {
				gemBag.add(selectedGem.getColour());
			}
			selectedGem = null;
		}

	}

	private void addToSlots(float mouseX, float mouseY) {

	}

	private void removeFromSlots(float mouseX, float mouseY) {
		if (selectedGem == null) {
			Gem potentialGem = ritualAltar.pickUpGem(mouseX, mouseY);
			if (potentialGem != null) {
				selectedGem = potentialGem;
			}

		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			ritualAltar.useGems();
		}
		return true;
	}

	public void tryToTurnPages(float x, float y) {
		ritualBook.click(x, y);
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
		Vector2 realPos = screen.getRealScreenPos(screenX, screenY);
		if (enabled) {
			tryToOpenBook(realPos.x, realPos.y);
			tryToTurnPages(realPos.x, realPos.y);
			removeFromSlots(realPos.x, realPos.y);
			pickUpGem(realPos.x, realPos.y);
		} else {
			checkContinue(realPos.x, realPos.y);
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
