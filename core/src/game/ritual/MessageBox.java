package game.ritual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import game.ritual.village.VillageInformation;

public class MessageBox extends GameObject {
	private BitmapFont font;
	private VillageInformation info;
	private int food;
	private int water;
	private int pop;
	private String instruction;

	protected MessageBox(Texture texture, float xPos, float yPos) {
		super(texture, xPos, yPos);
		font = new BitmapFont();
		instruction = " Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n Fusce ullamcorper rutrum purus, vel auctor sapien tincidunt vel.\n"
				+ " Proin felis massa, venenatis ut imperdiet in, scelerisque a ex. Donec eget mauris enim.\n"
				+ " In nec est vel risus ultricies placerat.\n"
				+ " Cras vulputate, enim at semper volutpat, libero sem euismod justo, gravida lobortis nulla nunc id nunc.\n"
				+ " Suspendisse lacinia felis odio, vel mollis velit sodales id. Maecenas id erat rhoncus, vehicula augue sit amet, suscipit nulla.";
	}

	public void getInfo(int food, int water, int pop) {
		this.food = food;
		this.water = water;
		this.pop = pop;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture,position.x,position.y);
		font.draw(batch, instruction, position.x+20, position.y+200);

	}

}
