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
    private int positionIndex = 0;
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
    private static int[] disableContinueButton = { 4, 5, 6, 7};
    private static int[] disableBackButton = {0, 4, 5, 6, 7};
    private boolean disableBack = false;
    private boolean disableContinue = false;
    private boolean clickTheButton = false; //for # 7 when player clicks to combine ritual
    private float case8Time = 0;

    public TutorialMessage(GameHandler gameHandler, RitualAltar ritualAltar) {
        super(gameHandler);
        this.ritualAltar = ritualAltar;
        texture = Assets.getTexture("ui/tutorialMessageBox.png");
        continueButton = Assets.getTexture("ui/tutorialContinueButton.png");
        backButton = Assets.getTexture("ui/tutorialBackButton.png");
        position.x = 480;
        position.y = 590;
    }


    private void switchText() {
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
                break;
            case 5:
                text = "Pick up a red gem and put it here";
                break;
            case 6:
                text = "Pick up another red gem and put it here";
                break;
            case 7:
                text = "This Ritual generates food but drains happiness \nClick compile and observe";
                break;
            case 8:
                text = "You can put the gems in a any grid you want \n as long as the components align with the recipe";
                break;
            //convert miner/explorer/farmer
            case 9:
                text = "If the gem does not follow any recipe\n you will waste it";
                break;
            case 10:
                text = "All the unlocked ritual can be viewed in the ritualBook";
                break;
            case 11:
                text = "Brief description and ability of each ritual";
                break;
            case 12:
                text = "Click to close the book";
                break;
            case 13:
                text = "Ritual can be combined to cost less gem";
                break;
            case 14:
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
                batch.draw(continueButton, position.x + texture.getWidth() - continueButton.getWidth(), position.y);
                break;
            //explain types of villagers
            case 1:
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
                move = setMovingPosition(delta, 1150, 80, 960, 500);
                moveX -= move.x;
                moveY -= move.y;
                // reset move if it passes the destination. This is pretty bad code
                if (1150 - moveX < 800 || 80 - moveY > 350) {
                    moveX = 0;
                    moveY = 0;
                }
                batch.draw(arrow, 1150 - moveX, 80 - moveY, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 120, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                break;
            //pick up a red gem and put it here
            case 5 :
                move = setMovingPosition(delta,875,80,930,360);
                moveX -= move.x;
                moveY -= move.y;
                // reset move if it passes the destination. This is pretty bad code
                if (875 - moveX < 850 || 80 - moveY > 230) {
                    moveX = 0;
                    moveY = 0;
                }
                batch.draw(arrow, 875 - moveX, 80 - moveY, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 70, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                break;
                /// /pick up another red gem and put it here
            case 6 :
                move = setMovingPosition(delta,875,80,930,270);
                moveX -= move.x;
                moveY -= move.y;
                // reset move if it passes the destination. This is pretty bad code
                if (875 - moveX < 850 || 80 - moveY > 180) {
                    moveX = 0;
                    moveY = 0;
                }
                batch.draw(arrow, 875 - moveX, 80 - moveY, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 65, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                break;
            case 7 :
                break;
            //you can align ritual wherever you want as long as it fits the ritual recipe
            case 8 :
                supportTexture1 = Assets.getTexture("gems/gem_yellow.png");
                supportTexture2 = Assets.getTexture("gems/gem_red.png");
                supportTexture3 = Assets.getTexture("gems/gem_red.png");
                case8Time+= delta;
                if(case8Time<1){
                    batch.draw(supportTexture1, 845,390);
                    batch.draw(supportTexture2, 845,390+80);
                    batch.draw(supportTexture3, 845,390+160);
                    break;
                }
                else if(case8Time <2){
                    batch.draw(supportTexture1, 925,390);
                    batch.draw(supportTexture2, 925,390+80);
                    batch.draw(supportTexture3, 925,390+160);
                    break;
                }
                else if(case8Time<3){
                    batch.draw(supportTexture1, 1005,390);
                    batch.draw(supportTexture2, 1005,390+80);
                    batch.draw(supportTexture3, 1005,390+160);
                    break;
                }
                else if(case8Time <4){
                    batch.draw(supportTexture1, 1085,390);
                    batch.draw(supportTexture2, 1085,390+80);
                    batch.draw(supportTexture3, 1085,390+160);
                    break;
                }
                else{
                    case8Time = 0;
                }

            case 9 :
                break;
            case 10:
                batch.draw(arrow, 700, 200, 0, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, 270, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;

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
        }else  if(clickTheButton && tutorialScreen == 7){
            nextScreen();
        }
    }
    public void alreadyClick(){
        clickTheButton = true;
    }
    private Vector2 setMovingPosition(float delta, float startX, float startY, float endX, float endY) {
        float moveX = (endX - startX) * delta / 3;
        float moveY = (endY - startY) * delta / 3;
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
        switchText();
    }
    private void prevScreen(){
        positionIndex -= 2;
        position.x = positionOfEachMessage[positionIndex];
        position.y = positionOfEachMessage[positionIndex + 1];
        tutorialScreen--;
        switchText();
    }

}
