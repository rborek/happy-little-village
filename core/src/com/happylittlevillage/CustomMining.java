package com.happylittlevillage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by User on 05/04/16.
 */
public class CustomMining extends GameObject {
    private boolean show = false;
    private GameObject box = new GameObject(Assets.getTexture("ui/parchment2.png"),200,150, 340, 420 );

    public CustomMining(Texture texture, float xPos, float yPos, int width, int height) {
        super(texture, xPos, yPos, width, height);

    }


    public void interact(float x, float y){
        Rectangle a = new Rectangle(position.x, position.y, width, height);
        if(a.contains(x, y)){
            if(show){
                show = false;
            }
            else{
                show = true;
            }
        }
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
        if(show){
            box.render(batch);
        }
    }
}
