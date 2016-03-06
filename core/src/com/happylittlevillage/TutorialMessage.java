package com.happylittlevillage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.messages.MessageBox;
import com.happylittlevillage.rituals.RitualAltar;

public class TutorialMessage extends MessageBox {
    protected Texture arrow = Assets.getTexture("ui/arrow.png");
    //this list contains position x and y of each tutorial messages
    private static float[] positionOfEachMessage = {480, 590, 480, 590, 80, 200, 350, 200, 475, 400, 475, 400, 475, 400, 475, 400,
            480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590};
    private int positionIndex;
    private RitualAltar ritualAltar;
    private float angle;
    private int tutorialScreen = 0;
    private Texture supportTexture1;
    private Texture supportTexture2;
    private Texture supportTexture3;
    private float delta;
    private boolean gemPlaced = false;
    private float moveX = 0;
    private float moveY = 0;
    private static int[] disableContinueButton = {4, 5, 6, 7};
    private static int[] disableBackButton = {0, 4, 5, 6, 7};
    private boolean disableBack = false;
    private boolean disableContinue = false;

    public TutorialMessage(GameHandler gameHandler, int positionIndex, RitualAltar ritualAltar) {
        super(gameHandler);
        this.ritualAltar = ritualAltar;
        this.positionIndex = positionIndex;
        texture = Assets.getTexture("ui/tutorialMessageBox.png");
        continueButton = Assets.getTexture("ui/tutorialContinueButton.png");
        backButton = Assets.getTexture("ui/tutorialBackButton.png");
        position.x = 480;
        position.y = 590;
    }


    private void switchScreen() {
        System.out.println("Tutorial Screen is"+tutorialScreen);
        switch (tutorialScreen) {
            case 0:
                text = "This is one of your villager";
                break;
            case 1:
                text = "There are three type of special villagers";
                break;
            case 2:
                text = "This is your village's food and their happiness";
                break;
            case 3:
                text = "Your village's population \nand the remaining time of today";
                break;
            //simple ritual
            case 4:
                text = "Pick up a yellow gem and put it here";
                System.out.println(" 4Was called");

                break;
            case 5:
                System.out.println(" 5 Was called");
                text = "Pick up a red gem and put it here";
                break;
            case 6:
                text = "Pick up another red gem and put it here";
                break;
            case 7:
                text = "Click compile and get your resources";
                break;
            case 8:
                text = "You can put the gems in a any grid you want";
                break;
            //convert miner/explorer/farmer
            case 9:
                text = "Now try this ritual";
                break;
            case 10:
                text = "If the gem does not follow any recipe\n you will waste it";
                break;
            case 11:
                text = "All the unlocked ritual can be viewed in the ritualBook";
                break;
            case 12:
                text = "Click to close the book";
                break;
            case 13:
                text = "For each week there is a mandatory weekly ritual that needs to be done before time runs out";
                break;
            default:
                text = "End";


        }
    }

    public void setAngle(double angle, boolean flip) {
        //boolean flip is to know if the angle is flipped. Since tan is the same in the first and third quadrant
        angle = angle * 57.2958;
        if (flip) {
            angle += 180;
        }
        this.angle = (float) angle;
    }

    public int getTutorialScreen() {
        return tutorialScreen;
    }

    @Override
    public void render(Batch batch) {
        //Since some supportingTextures need to be drawn after the box. We need to separate which case the texture or the supportingTextures is being rendered first
        //REMINDER of draw batch: scale=1 is normal. srcX,srcY and scrWidth/Height mark the render portion of the actual image not the game
        Vector2 move = null;
        batch.draw(texture, position.x, position.y);
        switch (tutorialScreen) {
            //point village
            case 0:
                batch.draw(arrow, position.x - 70, position.y - texture.getHeight() / 2 - 40, arrow.getWidth() / 2, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, angle, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                batch.draw(texture, position.x, position.y);
                batch.draw(continueButton, position.x + texture.getWidth() - continueButton.getWidth(), position.y);
                break;
            //explain types of villagers
            case 1:
                batch.draw(texture, position.x, position.y);
                batch.draw(continueButton, position.x + texture.getWidth() - continueButton.getWidth(), position.y);
                batch.draw(backButton, position.x, position.y);
                supportTexture1 = Assets.getTexture("villagers/explorer/explorer.png");
                supportTexture2 = Assets.getTexture("villagers/farmer/farmer.png");
                supportTexture3 = Assets.getTexture("villagers/miner/miner.png");
                batch.draw(supportTexture1, position.x, position.y + 30);
                batch.draw(supportTexture2, position.x + supportTexture1.getWidth() + 50, position.y + 30);
                batch.draw(supportTexture3, position.x + supportTexture2.getWidth() + supportTexture2.getWidth() + 100, position.y + 30);
                batch.draw(arrow, position.x - 70, position.y - texture.getHeight() / 2 - 40, arrow.getWidth() / 2, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, angle, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                break;
            //food and happiness
            case 2:
                batch.draw(arrow, 125, 170, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 270, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                batch.draw(arrow, 225, 170, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 270, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                break;
            //pop and hour
            case 3:
                batch.draw(arrow, 400, 170, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 270, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                break;
            //pick up a yellow gem and put it here
            case 4:
                move = setMovingPosition(delta, 1150, 100, 960, 500);
                moveX -= move.x;
                moveY -= move.y;
                // reset move if it passes the destination. This is pretty bad code
                if (1150 - moveX < 800 || 100 - moveY > 350) {
                    moveX = 0;
                    moveY = 0;
                }
                batch.draw(arrow, 1150 - moveX, 100 - moveY, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 120, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                batch.draw(texture, position.x, position.y);
                break;
            //pick up a red gem and put it here
            case 5 :
                batch.draw(texture, position.x, position.y);
                move = setMovingPosition(delta,1075,100,960,400);
                moveX -= move.x;
                moveY -= move.y;
                // reset move if it passes the destination. This is pretty bad code
                if (1075 - moveX < 800 || 100 - moveY > 250) {
                    moveX = 0;
                    moveY = 0;
                }
                batch.draw(arrow, 1075 - moveX, 100 - moveY, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 120, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);

        }
        if(!disableContinue){
            batch.draw(continueButton, position.x + texture.getWidth() - continueButton.getWidth(), position.y);
        }
        if(!disableBack && tutorialScreen != 0){
            batch.draw(backButton, position.x, position.y);
        }

        font = Assets.getFont(24);
        if (text != null) {
            font.draw(batch, text, position.x, position.y + 100);
        }
    }

    @Override
    public void update(float delta) {
        this.delta = delta;
        if (ritualAltar.getColour(1, 1) == GemColour.YELLOW && tutorialScreen == 4) {
            nextScreen();
        } else if (ritualAltar.getColour(2, 1) == GemColour.RED && tutorialScreen == 5) {
            nextScreen();
        } else if (ritualAltar.getColour(3, 1) == GemColour.RED && tutorialScreen == 6) {
            nextScreen();
        }
    }

    private Vector2 setMovingPosition(float delta, float startX, float startY, float endX, float endY) {
        float moveX = (endX - startX) * delta / 2;
        float moveY = (endY - startY) * delta / 2;
        return new Vector2(moveX, moveY);
    }

    @Override
    public boolean interact(float mouseX, float mouseY) {
        //check if the screen's back button is disabled
        disableBack = false;
        disableContinue = false;
        for (int k = 0; k < disableBackButton.length; k++) {
            if (tutorialScreen == disableBackButton[k]) {
                disableBack = true;
                break;
            }
        }
        //check if the screen's continue button is disabled
        for (int k = 0; k < disableContinueButton.length; k++) {
            if (tutorialScreen == disableContinueButton[k]) {
                disableContinue = true;
                break;
            }
        }
        if (!disableBack) {
            System.out.println("Disable going back");
            Rectangle backPosition = new Rectangle(position.x, position.y, backButton.getWidth(), backButton.getHeight());
            if (backPosition.contains(mouseX, mouseY)) {
                prevScreen();
                return true;
            }
        }
        if (!disableContinue) {
            Rectangle continuePosition = new Rectangle(position.x + texture.getWidth() - continueButton.getWidth(), position.y, continueButton.getWidth(), continueButton.getHeight());
            if (continuePosition.contains(mouseX, mouseY)) {
                nextScreen();
                return true;
            }
        }
        return false;
    }
    private void nextScreen(){
        positionIndex += 2;
        position.x = positionOfEachMessage[positionIndex];
        position.y = positionOfEachMessage[positionIndex + 1];
        tutorialScreen++;
        switchScreen();
    }
    private void prevScreen(){
        positionIndex -= 2;
        position.x = positionOfEachMessage[positionIndex];
        position.y = positionOfEachMessage[positionIndex + 1];
        tutorialScreen--;
        switchScreen();
    }

}
