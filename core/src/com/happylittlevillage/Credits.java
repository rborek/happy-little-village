package com.happylittlevillage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by User on 18/02/16.
 */
public class Credits extends Miscellaneous {
    private Texture backButton = new Texture(Gdx.files.internal("textures/bg/backButton.png"), true);
    private Rectangle backButtonPosition = new Rectangle(700, 100, backButton.getWidth(), backButton.getHeight());

    public Credits (HappyLittleVillage happyLittleVillage){
        super(happyLittleVillage);
        backGround = new Texture(Gdx.files.internal("textures/bg/credits.png"), true);
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX,screenY,pointer,button); // set the realPos
        System.out.println(realPos);
        if(backButtonPosition.contains(realPos)){
            happyLittleVillage.setMenu();
        }
        return true;
    }
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backGround,0,0);
        batch.draw(backButton,700,100);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }


}