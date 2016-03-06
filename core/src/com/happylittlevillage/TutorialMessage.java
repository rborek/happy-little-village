package com.happylittlevillage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.messages.MessageBox;

public class TutorialMessage extends MessageBox {
    protected Texture arrow = Assets.getTexture("ui/arrow.png");
    //this list contains position x and y of each tutorial messages
    private static float[] positionOfEachMessage = {480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590,
                                480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590, 480, 590};
    private int messageNumber;
    private float angle;
    private int indexTutorial = 0;
    private Texture supportTexture1;
    private Texture supportTexture2;
    private Texture supportTexture3;




    public TutorialMessage( GameHandler gameHandler, int messageNumber) {
        super( gameHandler);
        this.messageNumber = messageNumber;
        texture = Assets.getTexture("ui/tutorialMessageBox.png");
        continueButton = Assets.getTexture("ui/tutorialContinueButton.png");
        backButton = Assets.getTexture("ui/tutorialBackButton.png");
        position.x = 480;
        position.y = 590;
    }


    private void switchScreen( ){
        switch (indexTutorial){
            case 0: text = "This is one of your villager";
                    break;
            case 1: text = "There are three type of special villagers";

                    break;
            case 2: text = "This is your village's food and their happiness";
                    break;
            case 3: text = "Your village's population and the remaining time of today";
                    break;
            //simple ritual
            case 4: text = "Pick up a yellow gem and put it here";
                    break;
            case 5: text = "Pick up a red gem and put it here";
                    break;
            case 6: text = "Pick up another red gem and put it here";
                    break;
            case 7: text = "Click compile and get your resources";
                    break;
            case 8: text = "You can put the gems in a any grid you want";
                    break;
            //convert miner/explorer/farmer
            case 9: text = "Now try this ritual";
                    break;
            case 10:text = "All the unlocked ritual can be viewed in the ritualBook";
                    break;
            case 11:text = "Click to close the book";
                    break;
            case 12:text = "For each week there is a mandatory weekly ritual that needs to be done before time runs out";
                    break;
            default: text = "End";


        }
    }

    public void setAngle(double angle, boolean flip){
        //boolean flip is to know if the angle is flipped. Since tan is the same in the first and third quadrant
        angle = angle*57.2958;
        if(flip){
            angle+= 180;
        }
        this.angle = (float)angle;
    }
    public int getIndexTutorial(){
        return indexTutorial;
    }

    @Override
    public void render(Batch batch) {
        switch(indexTutorial){
            case 0: batch.draw(arrow, position.x - 20, position.y-texture.getHeight()/2, 0, arrow.getHeight()/2, arrow.getWidth(), arrow.getHeight(), 1, 1, angle, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                    break;
            case 1: // supportTexture1 = Assets.getTexture("villagers/explorer/explorer.png");
                    // supportTexture2 = Assets.getTexture("villagers/farmer/farmer.png");
                    // supportTexture3 = Assets.getTexture("villagers/miner/miner.png");
                    // batch.draw(supportTexture1, position.x, position.y+30);
                    // batch.draw(supportTexture2, position.x + supportTexture1.getWidth() + 50, position.y + 30);
                    // batch.draw(supportTexture3, position.x + supportTexture2.getWidth() + supportTexture2.getWidth() + 100, position.y + 30);
                    batch.draw(arrow, position.x - 20, position.y-texture.getHeight()/2, 0, arrow.getHeight()/2, arrow.getWidth(), arrow.getHeight(), 1, 1, angle, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                    break;
            case 2: batch.draw(arrow, 125, 170, 0, arrow.getHeight()/2, arrow.getWidth(), arrow.getHeight(), 1, 1, 270, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                    batch.draw(arrow, 225, 170, 0, arrow.getHeight()/2, arrow.getWidth(), arrow.getHeight(), 1, 1, 270, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);
                    position.set(80,200);
                    break;
            case 3: position.set(350,200);
                    batch.draw(arrow, 400, 170, 0, arrow.getHeight()/2, arrow.getWidth(), arrow.getHeight(), 1, 1, 270, 0, 0, arrow.getWidth(), arrow.getHeight(), false, false);


        }
        font = Assets.getFont(24);
        batch.draw(texture, position.x, position.y);
        batch.draw(continueButton, position.x + texture.getWidth() - continueButton.getWidth(), position.y);
        batch.draw(backButton,position.x, position.y);
        //REMINDER of draw batch: scale=1 is normal. srcX,srcY and scrWidth/Height mark the render portion of the actual image not the game
        if (text != null) {
            font.draw(batch, text, position.x, position.y+100);
        }
    }

    @Override
    public boolean interact(float mouseX, float mouseY) {
        Rectangle continuePosition = new Rectangle(position.x + texture.getWidth() - continueButton.getWidth(), position.y, continueButton.getWidth(), continueButton.getHeight());
        Rectangle backPosition = new Rectangle(position.x, position.y,backButton.getWidth(), backButton.getHeight());
        if (continuePosition.contains(mouseX, mouseY)) {

            messageNumber += 2;
            position.x = positionOfEachMessage[messageNumber];
            position.y = positionOfEachMessage[messageNumber + 1];
            indexTutorial++;
            switchScreen();
            return true;
        }
        else if(backPosition.contains(mouseX, mouseY)) {
            messageNumber -= 2;
            if(messageNumber<0){
                messageNumber = 0;
            }
            position.x = positionOfEachMessage[messageNumber];
            position.y = positionOfEachMessage[messageNumber + 1];
            indexTutorial--;
            switchScreen();
            return true;
        }
        return false;
    }

}
