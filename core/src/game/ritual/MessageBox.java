package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

import game.ritual.village.Village;
import game.ritual.village.VillageInformation;

public class MessageBox extends GameObject {
    protected GameHandler gameHandler;
    protected BitmapFont font;
    protected String text;
    protected Texture continueButton = new Texture("scroll/toContinue.png");
    protected String clickToContinue = "Click to Continue";
    protected boolean click = false;
    protected float continueX;
    protected float continueY;

    protected MessageBox(Texture texture, float xPos, float yPos) {
        super(texture, xPos, yPos);
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        font = generator.generateFont(parameter);
        setButtonPos();
    }


    // for the instruction
    protected MessageBox(String instruction, GameHandler gameHandler) {
        super(new Texture("scroll/Summary.png"), 20, 300);
        font = new BitmapFont();
        this.text = instruction;
        this.gameHandler = gameHandler;
        setButtonPos();
    }

    private void setButtonPos() {
        continueX = position.x + 300;
        continueY = position.y + 20;
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
    }

    public void checkClick(float x, float y) {
        Rectangle r = new Rectangle(continueX, continueY, continueButton.getWidth(), continueButton.getHeight());
        System.out.println(continueX + ", " + continueY);
        if (r.contains(x, y)) {
            gameHandler.unpause();
        }
    }

    public void unclick() {
        click = false;
    }

    public boolean getClick() {
        return click;
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y);
        batch.draw(continueButton, continueX, continueY);
        font.draw(batch, clickToContinue, continueX + 28, continueY + 27);
    }

}
