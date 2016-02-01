package game.ritual.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import game.ritual.GameHandler;

public class WinMessage extends MessageBox {
	private String message;
	private String[] condition;
	public WinMessage (GameHandler gameHandler){
		super("", gameHandler);
		message = "";
		condition = new String[3];
		condition[0] = "Enough food to survive, your \nvillagers part away with their food";
		condition[1] = "Enough villagers to create a tribe";
		condition[2] = "Enough water to survive, your vilagers part away with their water";
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 36;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		font = generator.generateFont(parameter);
		
	}
	public void setCondition(int a){
		message = condition[a];
	}
	@Override
	public void render(Batch batch) {
		super.render(batch);
		font.draw(batch, title, position.x + 70, position.y + 250);
		font.draw(batch, message, position.x + 70, position.y + 280);
		font.draw(batch, "YOU WIN!", position.x + 70, position.y + 230);
	}
}
