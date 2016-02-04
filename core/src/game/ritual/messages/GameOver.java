package game.ritual.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import game.ritual.GameHandler;

public class GameOver extends MessageBox {
	private String message;
	private String[] condition;
	private BitmapFont gameOverFont;


	public GameOver(GameHandler gameHandler) {
		super("", gameHandler);
		title = " GAME OVER ";
		message = "";
		condition = new String[4];
		condition[0] = " No more villagers are left!";
		condition[1] = " Your villagers died of hunger!";
		condition[2] = " Your villagers died of thirst!";
		condition[3] = " You didn't complete the monthly ritual on time!";
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 36;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		font = generator.generateFont(parameter);
	}

	public void setCondition(int a) {
		message = condition[a];
	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, title, position.x + 70, position.y + 250);
		font.draw(batch, message, position.x + 70, position.y + 280);
	}

}
