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
    private static int[] disableContinueButton = { 4, 5, 6, 7};
    private static int[] disableBackButton = {0, 4, 5, 6, 7};
    private static int[] noArrowScreen = {7, 8, 9};
    private boolean disableBack = false;
    private boolean disableContinue = false;
    private boolean noArrow = false;
    private boolean clickTheButton = false; //for # 7 when player clicks to combine ritual
    private float case8Time = 0;
    //Exclusively for the arrow
    Vector2 move = null;
    private float arrowMoveX = 0;
    private float arrowMoveY = 0;
    private float arrowPositionX = 0;
    private float arrowPositionY = 0;
    private float supportX1 =0, supportY1 = 0, supportX2 = 0, supportY2 = 0,  supportX3 = 0, supportY3 = 0;

    public TutorialMessage(GameHandler gameHandler, RitualAltar ritualAltar) {
        super(gameHandler);
        this.ritualAltar = ritualAltar;
        texture = Assets.getTexture("ui/tutorialMessageBox.png");
        continueButton = Assets.getTexture("ui/tutorialContinueButton.png");
        backButton = Assets.getTexture("ui/tutorialBackButton.png");
        position.x = positionOfEachMessage[0];
        position.y = positionOfEachMessage[1];
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


        batch.draw(texture, position.x, position.y);
        switch (tutorialScreen) {
            //point village
            case 0:
                break;
            //explain types of villagers
            case 1:
//                supportTexture1 = Assets.getTexture("villagers/explorer/explorer.png");
//                supportTexture2 = Assets.getTexture("villagers/farmer/farmer.png");
//                supportTexture3 = Assets.getTexture("villagers/miner/miner.png");
//                batch.draw(supportTexture1, position.x, position.y + 30);
//                batch.draw(supportTexture2, position.x + supportTexture1.getWidth() + 50, position.y + 30);
//                batch.draw(supportTexture3, position.x + supportTexture2.getWidth() + supportTexture2.getWidth() + 100, position.y + 30);
                break;
            //food and happiness
            case 2:
                break;
            //pop and hour
            case 3:
                break;
            //pick up a yellow gem and put it here
            case 4:
                break;
            //pick up a red gem and put it here
            case 5 :
                break;
                /// /pick up another red gem and put it here
            case 6 :
                break;
            case 7 :
                break;
            //you can align ritual wherever you want as long as it fits the ritual recipe
            case 8 :
                supportTexture1 = Assets.getTexture("gems/gem_yellow.png");
                supportTexture2 = Assets.getTexture("gems/gem_red.png");
                supportTexture3 = Assets.getTexture("gems/gem_red.png");
            case 9 :
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;

        }
        if(!noArrow){
            batch.draw(arrow, arrowPositionX, arrowPositionY, arrow.getWidth() / 2, arrow.getHeight() / 2, arrow.getWidth(), arrow.getHeight(), 1, 1, angle, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);}
        if(!disableContinue){
            batch.draw(continueButton, position.x + texture.getWidth() - continueButton.getWidth(), position.y);
        }
        if(!disableBack && tutorialScreen != 0){
            batch.draw(backButton, position.x, position.y);
        }
        if(tutorialScreen == 8){
            batch.draw(supportTexture1,supportX1, supportY1);
            batch.draw(supportTexture2,supportX2, supportY2);
            batch.draw(supportTexture3,supportX3, supportY3);
        }
        font = Assets.getFont(24);
        if (text != null) {
            font.draw(batch, text, position.x, position.y + 100);
        }
    }

    @Override
    public void update(float delta) {
        this.delta = delta;
        disableBack = false;
        disableContinue = false;
        noArrow = false;
        for ( int a : disableBackButton) {
            if (tutorialScreen == a) {
                disableBack = true;
                break;
            }
        }
        //check if the screen's continue button is disabled
        for ( int a : disableContinueButton) {
            if (tutorialScreen == a) {
                disableContinue = true;
                break;
            }
        }
        for( int a : noArrowScreen){
            if(tutorialScreen == a){
                noArrow = true;
                break;
            }
        }
        switch (tutorialScreen){
            case 0: arrowPositionX = position.x - 70;
                arrowPositionY = position.y - texture.getHeight() / 2 - 40;
                break;
            case 1: arrowPositionX = position.x - 70;
                arrowPositionY = position.y - texture.getHeight() / 2 - 40;
                break;
            case 2:arrowPositionX = 125;
                arrowPositionY = 170;
                angle = 270;
                break;
            case 3: arrowPositionX = 400;
                arrowPositionY = 170;
                angle = 270;
                break;
            case 4: setMovingPosition(delta, 1150, 80, 960, 500);
                angle = 120;
                break;
            case 5: setMovingPosition(delta,875,80,930,360);
                angle = 70;
                break;
            case 6: setMovingPosition(delta,875,80,930,270);
                angle = 65;
                break;
            case 8:
                case8Time+= delta;
                supportY1 = 390;
                supportY2 = 390 + 80;
                supportY3 = 390 + 160;
                if(case8Time<1){
                    supportX1 = 845;
                    supportX2 = 845;
                    supportX3 = 845;
                    break;
                }
                else if(case8Time <2){
                    supportX1 = 845;
                    supportX2 = 845;
                    supportX3 = 845;
                    break;
                }
                else if(case8Time<3){
                    supportX1 = 1005;
                    supportX2 = 1005;
                    supportX3 = 1005;
                    break;
                }
                else if(case8Time <4){
                    supportX1 = 1085;
                    supportX2 = 1085;
                    supportX3 = 1085;
                    break;
                }
                else{
                    case8Time = 0;
                }
                break;
            case 10:
                arrowPositionX = 700;
                arrowPositionY = 200;
                angle = 270;
                break;
            case 12:
                break;

        }
        //for the simple ritual from #4-#6
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
    private void setMovingPosition(float delta, float startX, float startY, float endX, float endY) {
        float moveX = (endX - startX) * delta / 3;
        float moveY = (endY - startY) * delta / 3;
        arrowMoveX -= moveX;
        arrowMoveY -= moveY;
        //this temporary code makes sure the arrow repeats
        if(startX < endX){
            //going to the right
            if(startX-arrowMoveX >endX -100){
                arrowMoveX = 0;
                arrowMoveY = 0;
            }
        }
        else if(startX > endX){
            if(startX-arrowMoveY < endX + 100){
                arrowMoveX = 0;
                arrowMoveY = 0;
            }
        }
        arrowPositionX = startX - arrowMoveX;
        arrowPositionY = startY - arrowMoveY;
    }

    @Override
    public boolean interact(float mouseX, float mouseY) {
        //check if the screen's back button is disabled
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
