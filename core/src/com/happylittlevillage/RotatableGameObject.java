package com.happylittlevillage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by User on 06/03/16.
 */
public class RotatableGameObject extends  GameObject {
    private float angle = 0;

    public RotatableGameObject(Texture texture, float xPos, float yPos, float angle){
        super(texture,xPos,yPos);
        this.angle = angle;

    }
    public RotatableGameObject(Texture texture, float xPos, float yPos){
        super(texture,xPos,yPos);
    }

    @Override
    public  void render(Batch batch){
        if(angle == 0){
            batch.draw(texture,position.x, position.y);
        }
        else{
            batch.draw(texture,position.x,position.y, texture.getWidth()/2, texture.getHeight()/2,texture.getWidth(),texture.getHeight(), 1,1,angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false );
        }
    }
    @Override
    public void update(float delta) {

    }

    public void setAngle(float angle){
        this.angle = angle;
    }
    public void setPosition(float xPos, float yPos){
        position.x = xPos;
        position.y = yPos;
    }
}
