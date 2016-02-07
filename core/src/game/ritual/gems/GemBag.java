package game.ritual.gems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import game.ritual.Assets;
import game.ritual.GameObject;
import game.ritual.rituals.RitualAltar;

import java.util.Random;

public class GemBag extends GameObject {
	private BitmapFont font;
	private RitualAltar gemSlot;
	private Texture[] gemTextures = Gem.getArrayOfTextures();
	private Texture inactiveGem = Assets.getTexture("gems/gem_grey.png");
	private static final int slotSize = 64;
	private Rectangle[] slots = new Rectangle[4];
	private int[] gemAmounts = new int[GemColour.values().length];


	public GemBag(float xPos, float yPos) {
		super(Assets.getTexture("ui/gem_bag.png"), xPos, yPos);
		//gemAmounts = new int[GemColour.values().length];
		for (int i = 0; i < gemAmounts.length; i++) {
			gemAmounts[i] = 25;
		}
		slots[0] = new Rectangle(42, 54, slotSize, slotSize);
		slots[1] = new Rectangle(135, 54, slotSize, slotSize);
		slots[2] = new Rectangle(231, 54, slotSize, slotSize);
		slots[3] = new Rectangle(322, 54, slotSize, slotSize);
		for (int i = 0; i < slots.length; i++) {
			slots[i].x += position.x;
			slots[i].y += position.y;
		}
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/palitoon.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1;
		parameter.genMipMaps = true;
		parameter.magFilter = Texture.TextureFilter.MipMapLinearLinear;
		parameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
		font = generator.generateFont(parameter);
		texture.setFilter(Texture.TextureFilter.MipMapLinearLinear,Texture.TextureFilter.MipMapLinearLinear);
	}

	public void add(GemColour colour) {
		gemAmounts[colour.ordinal()]++;
	}

	public Gem pickUpGem(float x, float y) {
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].contains(x, y)) {
				return new Gem(GemColour.values()[i]);
			}
		}
		return null;
	}


	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
		for (int i = 0; i < gemTextures.length; i++) {
			if (gemAmounts[i] != 0) {
				batch.draw(gemTextures[i], slots[i].x, slots[i].y);
			} else {
				batch.draw(inactiveGem, slots[i].x, slots[i].y);
			}
		}
		for (int i = 0; i < gemTextures.length; i++) {
			font.draw(batch, "" + gemAmounts[i], slots[i].x + 50, slots[i].y + 16);
		}
	}

	public int getAmount(GemColour colour) {
		return gemAmounts[colour.ordinal()];
	}

	public GemColour gainRandomGem() {
		Random random = new Random();
		GemColour gemColour = GemColour.values()[(random.nextInt(GemColour.values().length))];
		add(gemColour);
		return gemColour;
	}

	public void remove(GemColour colour) {
		gemAmounts[colour.ordinal()]--;
	}

	@Override
	public void update(float delta) {

	}
}
