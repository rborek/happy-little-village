package com.happylittlevillage.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.HappyLittleVillage;
import com.happylittlevillage.menu.GameScreen;
import com.happylittlevillage.TutorialMessage;
import com.happylittlevillage.gems.GemBook;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.messages.MessageBox;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.rituals.RitualBook;
import com.happylittlevillage.rituals.RitualTree;
import com.happylittlevillage.village.Village;

public class InputHandler implements InputProcessor {
    private GameScreen screen;
    private RitualAltar ritualAltar;
    private GemBag gemBag;
    private Gem selectedGem;
    private Gem[][] selectedRitual;
    private RitualBook ritualBook;
    private MessageBox messageBox;
    private GemBook miniBook;
    private GameHandler gameHandler;
    private TutorialMessage tutorialMessage;
    private HappyLittleVillage happyLittleVillage;
    private RitualTree ritualTree;
//    private int spaceBetweenGems = 40;
//    private int gemSize = 48;

    public InputHandler(GameScreen screen, HappyLittleVillage happyLittleVillage) {
        this.screen = screen;
        this.happyLittleVillage = happyLittleVillage;
    }

    public void linkTo(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.ritualAltar = gameHandler.getRitualAltar();
        this.gemBag = gameHandler.getGemBag();
        this.ritualBook = gameHandler.getRitualBook();
        this.miniBook = gameHandler.getMiniBook();
        this.tutorialMessage = gameHandler.getTutorialMessage();
        this.ritualTree = gameHandler.getRitualTree();
    }

    public void renderSelectedGem(Batch batch) {
        if (selectedGem != null) {
            Vector2 realPos = screen.getRealScreenPos(Gdx.input.getX(), Gdx.input.getY());
            selectedGem.render(batch, realPos.x - RitualAltar.slotSize2/2, realPos.y - RitualAltar.slotSize2/2);
        }
    }

    public void renderSelectedRitual(Batch batch) {
        if (selectedRitual != null) {
            Vector2 realPos = screen.getRealScreenPos(Gdx.input.getX(), Gdx.input.getY());
            for (int k = 0; k < selectedRitual.length; k++) {
                for (int i = 0; i < selectedRitual[0].length; i++) {
                    if (selectedRitual[k][i] != null) {
                        //do not change this algorithm or stuff will be flipped
                        selectedRitual[k][i].render(batch, realPos.x + (i - selectedRitual[0].length) * (RitualAltar.spacing +RitualAltar.slotSize2),
                                realPos.y + (-k) * (RitualAltar.spacing +RitualAltar.slotSize2));
                    }
                }
            }
        }
    }

    private void tryToOpenBook(float mouseX, float mouseY) {
        miniBook.toggle(mouseX, mouseY);
    }

    private void checkContinue(float mouseX, float mouseY) {
        if(gameHandler.getMessageScreen()!=3){
            gameHandler.getMessageBox().interact(mouseX, mouseY);
        }
        else{
            gameHandler.getRitualTree().interact(mouseX,mouseY);
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

    private void dropGem(float mouseX, float mouseY) {
        if (selectedGem != null) {
            //if it's not added to any of the gem
            if (!ritualAltar.placeGem(selectedGem, mouseX, mouseY)) {
                gemBag.add(selectedGem.getColour());
            }
            selectedGem = null;
        }
    }

    private void dropRitual(float mouseX, float mouseY) {
        if (selectedRitual != null) {
            ritualAltar.placeRitual(selectedRitual, mouseX, mouseY);
            selectedRitual = null;
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
        if (!gameHandler.isPaused()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                ritualAltar.useGems();
            }
        }
        return true;
    }

    public void interactRitualBook(float x, float y) {
        ritualBook.turnPage(x, y);
    }

    public boolean clickOptionWheel(float x, float y) {
        return (gameHandler.getOptionWheelPosition().contains(x, y));
    }

    public void clickNextButtonVillageInfo(float x, float y) {
        gameHandler.getVillage().getVillageInformation().interact(x, y);
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
        System.out.println("Mouse click at " + realPos);
//        if(clickOptionWheel(realPos.x,realPos.y)){
//            gameHandler.saveGame();
//            happyLittleVillage.setMenu();
//            gameHandler.pause();
//    }
        clickNextButtonVillageInfo(realPos.x, realPos.y); //  VillageInfo next button
        if (gameHandler.isPaused()) {
            System.out.print("CHECKED");
            checkContinue(realPos.x, realPos.y);
        }
        //if Tutorial
        else if (gameHandler.isTutorial()) {
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
                tryToOpenBook(realPos.x, realPos.y);
                if (miniBook.isOpen()) {
                    interactRitualBook(realPos.x, realPos.y);
                    pickUpRitual(realPos.x, realPos.y);
                }
            }
        }
        //start GamePlay
        else {
            tryToOpenBook(realPos.x, realPos.y);
            if (miniBook.isOpen()) {
                interactRitualBook(realPos.x, realPos.y);
                pickUpRitual(realPos.x, realPos.y);
            }
            removeFromSlots(realPos.x, realPos.y);
            pickUpGem(realPos.x, realPos.y);
            ritualAltar.interact(realPos.x, realPos.y);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 realPos = screen.getRealScreenPos(screenX, screenY);
        dropGem(realPos.x, realPos.y);
        dropRitual(realPos.x, realPos.y);
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
