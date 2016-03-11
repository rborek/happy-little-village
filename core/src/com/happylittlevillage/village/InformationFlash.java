package com.happylittlevillage.village;

/**
 * Created by User on 10/03/16.
 */
public class InformationFlash {
    private String info;
    private float alpha = 1;
    private int startX, startY, endX, endY;
    private int moveToY, moveToX;
    private int relativePosition; //only for food, water and happiness( 0, 1 ,2)
    public InformationFlash(String info, int relativePosition){
        this.info = info;
        this.relativePosition = relativePosition;
        startY = 400;
        endY = 600;
    }

    public void setPositionX(int startX, int endX ){
        this.startX = startX;
        this.endX = endX;
    }

    public void setPositionY(int startY, int endY){
        this.startY = startY;
        this.endY = endY;
    }

    public void updateMotion(float delta, boolean vertical) {
        if(vertical){
            float moveY = (endY - startY) * delta/10;
            moveToY += moveY;
        }
        else{
            float moveX = (endX - startX) * delta/10;
            moveToX += moveX;
        }
        alpha -= delta/5;
        if(alpha <= 0){
            alpha = 1;
        }
    }

    public float getAlpha(){
        return alpha;
    }
    public float getmoveToX(){
        return moveToX;
    }
    public float getmoveToY(){
        return moveToY;
    }

    public int getRelativePosition(){
        return relativePosition;
    }
    public String getInfo(){
        return info;
    }
}

