package com.happylittlevillage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by User on 18/02/16.
 */
public class Options extends Miscellaneous {
    private Texture backButton = new Texture(Gdx.files.internal("textures/bg/backButton.png"), true);
    private float xPos =800;
    private float yPos =400;
    private Rectangle backButtonPosition = new Rectangle(700, 100, backButton.getWidth(), backButton.getHeight());
    private Texture buttonScale = new Texture(Gdx.files.internal("textures/bg/buttonScale.png"), true);
    private Rectangle buttonScalePosition = new Rectangle(20, 20, buttonScale.getWidth(), buttonScale.getHeight());
    private Texture bar = new Texture(Gdx.files.internal("textures/bg/bar.png"),true);
    private Rectangle barPosition = new Rectangle(800, 400, bar.getWidth(), bar.getHeight());

    public Options(HappyLittleVillage happyLittleVillage){
        super(happyLittleVillage);
        backGround = new Texture(Gdx.files.internal("textures/bg/options.png"), true);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX,screenY,pointer,button); // set the realPos
        if(barPosition.contains(realPos)){
            xPos = realPos.x;
        }
        if(backButtonPosition.contains(realPos)){
            happyLittleVillage.setMenu();
        }

        return true;
    }

    private void setPosition(){
        xPos = realPos.x;
        yPos = realPos.y;
    }
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backGround,0,0);
        batch.draw(backButton,800,100);
        batch.draw(bar,800,400);
        batch.draw(buttonScale,xPos,yPos+5);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }



}

