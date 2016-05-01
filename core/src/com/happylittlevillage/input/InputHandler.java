package com.happylittlevillage.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.messages.MessageBox;
import com.happylittlevillage.messages.TutorialMessage;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.rituals.RitualBook;
import com.happylittlevillage.rituals.RitualTree;
import com.happylittlevillage.screens.GameScreen;

public class InputHandler implements GestureDetector.GestureListener {
	private GameScreen screen;
	private RitualAltar ritualAltar;
	private GemBag gemBag;
	private Gem selectedGem;

	private Gem[][] selectedRitual;
	private RitualBook ritualBook;
	private MessageBox messageBox;
	private GameHandler gameHandler;
	private TutorialMessage tutorialMessage;
	private HappyLittleVillage happyLittleVillage;

	private RitualTree ritualTree;
	private float delta;
	private float touchTime = 0;
	private boolean slideable = false;
	private boolean slided = true;
	private static Rectangle slideArea = new Rectangle(620, 0, 800, 220);
	private boolean isLongPressed = false;

	public InputHandler(GameScreen screen, HappyLittleVillage happyLittleVillage) {
		this.screen = screen;
		this.happyLittleVillage = happyLittleVillage;
	}


	public void linkTo(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
		this.ritualAltar = gameHandler.getRitualAltar();
		this.gemBag = gameHandler.getGemBag();
		this.ritualBook = gameHandler.getRitualBook();
		this.tutorialMessage = gameHandler.getTutorialMessage();
		this.ritualTree = gameHandler.getRitualTree();
	}

	public void renderSelectedGem(Batch batch) {
		if (selectedGem != null) {
			Vector2 realPos = screen.getRealScreenPos(Gdx.input.getX(), Gdx.input.getY());
			selectedGem.render(batch, realPos.x - RitualAltar.SLOT_SIZE / 2, realPos.y - RitualAltar.SLOT_SIZE / 2);
		}
	}

	public void renderSelectedRitual(Batch batch) {
		if (selectedRitual != null) {
			Vector2 realPos = screen.getRealScreenPos(Gdx.input.getX(), Gdx.input.getY());
			for (int k = 0; k < selectedRitual.length; k++) {
				for (int i = 0; i < selectedRitual[0].length; i++) {
					if (selectedRitual[k][i] != null) {
						//do not change this algorithm or stuff will be flipped
						selectedRitual[k][i].render(batch, realPos.x + RitualAltar.SLOT_SIZE + RitualAltar.SPACING - RitualAltar.SLOT_SIZE / 2 + (i - selectedRitual[0].length) * (RitualAltar.SPACING + RitualAltar.SLOT_SIZE),
								realPos.y - RitualAltar.SLOT_SIZE / 2 + (-k) * (RitualAltar.SPACING + RitualAltar.SLOT_SIZE));
					}
				}
			}
		}
	}

	private void checkContinue(float mouseX, float mouseY) {
		if (gameHandler.getMessageScreen() == 0) {
			gameHandler.getMessageBox().interact(mouseX, mouseY);
		} else if (gameHandler.getMessageScreen() == 1) {
			gameHandler.getRitualTree().interact(mouseX, mouseY);
		}
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

	private void pickUpRitual(float mouseX, float mouseY) {
		if (selectedGem == null && selectedRitual == null) {
			selectedRitual = ritualBook.getRitualRecipe(mouseX, mouseY);
		}
	}

	public void dropGem(float mouseX, float mouseY) {
		if (selectedGem != null) {
			//if it's not added to any of the gem
			if (!ritualAltar.placeGem(selectedGem, mouseX, mouseY)) {
				gemBag.add(selectedGem.getColour());
			}
			selectedGem = null;
		}
	}

	public void dropRitual(float mouseX, float mouseY) {
		if (selectedRitual != null) {
			ritualAltar.placeRitual(selectedRitual, mouseX, mouseY);
			selectedRitual = null;
		}
	}

	private void removeFromSlots(float mouseX, float mouseY) {
		if (selectedGem == null) {
			Gem potentialGem = ritualAltar.pickUpGem(mouseX, mouseY);
			if (potentialGem != null) {
				selectedGem = potentialGem;
			}

		}
	}

	private void addToSlots(float mouseX, float mouseY) {

	}

//    @Override
//    public boolean keyDown(int keycode) {
//        if (!gameHandler.isPaused()) {
//            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//                ritualAltar.useGems();
//            }
//        }
//        return true;
//    }



	public boolean clickOptionWheel(float x, float y) {
		return (gameHandler.getOptionWheelPosition().contains(x, y));
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}


	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean touchDown(float screenX, float screenY, int pointer, int button) {
		Vector2 realPos = screen.getRealScreenPos(screenX, screenY);
		System.out.println("Mouse click at:" + realPos);
//        if(clickOptionWheel(realPos.x,realPos.y)){
//            gameHandler.saveGame();
//            happyLittleVillage.setMenu();
//            gameHandler.pause();
//    }
		isLongPressed = false;
		if (slideArea.contains(realPos.x, realPos.y)) {
			slideable = true;
		} else {
			slideable = false;
		}
		if (gameHandler.isPaused()) {
			checkContinue(realPos.x, realPos.y);
		} else if (gameHandler.isTutorial()) { //  tutorial
			tutorialMessage.interact(realPos.x, realPos.y);
			//this restrict player's interaction with the grid only
			if (tutorialMessage.getTutorialScreen() >= 4 && tutorialMessage.getTutorialScreen() < 10) {
				pickUpGem(realPos.x, realPos.y);
				removeFromSlots(realPos.x, realPos.y);
				//exclusively for clicking at the compile button only
				if (ritualAltar.interact(realPos.x, realPos.y)) {
					tutorialMessage.alreadyClick();
				}
			}
			//This restrict player's interaction with the book only
			if (tutorialMessage.getTutorialScreen() >= 10 || tutorialMessage.getTutorialScreen() <= 12) {
				pickUpRitual(realPos.x, realPos.y);
			}
		} else { // start gameplay
			if(gameHandler.getRitualTree().isPickAxeUnlocked()){
				gameHandler.getMiningWindow().interact(realPos.x, realPos.y); // for the mining window
			}
			pickUpGem(realPos.x, realPos.y);
			removeFromSlots(realPos.x, realPos.y);
			ritualAltar.interact(realPos.x, realPos.y); // commence button
		}
//        return super.touchDown(screenX, screenY, pointer, button);
		return true;
	}

	public void interactRitualBook(boolean isLeft, float deltaX) {
		ritualBook.moveRitual(isLeft, deltaX);
	}

	// this is only for the TouchUp in GestureDetector.
	public void reset(){
//		ritualBook.reset(0);
	}
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Vector2 realPos = new Vector2(screen.getRealScreenPos(x, y));
		if (slideable && !isLongPressed) { // if it is in the slideable zone and user does not intend to pick up ritual
			if (realPos.x >= slideArea.x && realPos.y <= slideArea.height) { // if the finger is in range
				if (Math.abs(deltaX) > Math.abs(deltaY)) {
					if (deltaX > 0) {
						interactRitualBook(false, deltaX); //if the finger goes to the right
					} else {
						interactRitualBook(true, deltaX); // if the finger goes to the left
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		isLongPressed = true;
		Vector2 realPos = screen.getRealScreenPos(x, y);
		System.out.println("Long press detected");
		pickUpRitual(realPos.x, realPos.y);
		return false;
	}

}
