package com.happylittlevillage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.messages.MessageBox;

public class TutorialMessage extends MessageBox {
    protected Rectangle continuePosition;
    //this list contains position x and y of each tutorial messages
    private static float[] positionOfEachMessage = {480, 590, 500, 500};
    private int messageNumber;


    public TutorialMessage(String instruction, GameHandler gameHandler, int messageNumber) {
        super(instruction, gameHandler);
        this.messageNumber = messageNumber;
        texture = Assets.getTexture("ui/tutorialMessageBox.png");
        continueButton = Assets.getTexture("ui/tutorialContinueButton.png");
        position.x = 480;
        position.y = 590;
        continuePosition = new Rectangle(position.x + texture.getWidth() -continueButton.getWidth() , position.y, continueButton.getWidth(), continueButton.getHeight());
    }

    public boolean clickContinue(float mouseX, float mouseY){
        System.out.println("Continue Button at:"+continuePosition.toString());
        if (continuePosition.contains(mouseX, mouseY)) {
            //if they click continue then the message position is moved to the next one
            System.out.println("Gone here");
            messageNumber+=2;
            position.x = positionOfEachMessage[messageNumber];
            position.y = positionOfEachMessage[messageNumber+1];
            return true;
        }
        return false;
    }

    @Override
    public  void render(Batch batch){
        font = Assets.getFont(36);
        batch.draw(texture, position.x, position.y);
        batch.draw(continueButton, position.x + texture.getWidth() -continueButton.getWidth(), position.y);
        if (text != null) {
            font.draw(batch, text, 500, 500);
        }
    }

}
