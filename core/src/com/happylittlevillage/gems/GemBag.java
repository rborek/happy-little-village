package com.happylittlevillage.gems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.Assets;

import java.util.Random;

public class GemBag extends GameObject {

	private RitualAltar gemSlot;
	private static final int slotSize = 64;
	private static int PosX = 740;
	public static GameObject[] gemTextures = {  // red blue green yellow
			new GameObject(Gem.getArrayOfTextures()[0], PosX, 310, slotSize, slotSize),
			new GameObject(Gem.getArrayOfTextures()[1], PosX, 390, slotSize, slotSize),
			new GameObject(Gem.getArrayOfTextures()[2], PosX, 470, slotSize, slotSize),
			new GameObject(Gem.getArrayOfTextures()[3], PosX, 550, slotSize, slotSize),};
	private Texture inactiveGem = Assets.getTexture("gems/gem_grey.png");
	private Rectangle[] slots = new Rectangle[4];
	private int[] gemAmounts = new int[GemColour.values().length];
	private double[] cumulativeProbabilities = {0, 0, 0, 0}; // total will always be 10 percent
	private double[] gemProbabilities = {25, 25, 25, 25};    // 10% difference in total

	public GemBag(float xPos, float yPos) {
		super(Assets.getTexture("ui/gem_bag.png"), xPos, yPos);
		for (int i = 0; i < gemAmounts.length; i++) {
			gemAmounts[i] = 100;
		}
		slots[0] = new Rectangle(gemTextures[0].getPosition().x, gemTextures[0].getPosition().y, slotSize, slotSize);
		slots[1] = new Rectangle(gemTextures[1].getPosition().x, gemTextures[1].getPosition().y, slotSize, slotSize);
		slots[2] = new Rectangle(gemTextures[2].getPosition().x, gemTextures[2].getPosition().y, slotSize, slotSize);
		slots[3] = new Rectangle(gemTextures[3].getPosition().x, gemTextures[3].getPosition().y, slotSize, slotSize);
	}

	public void add(GemColour colour) {
		gemAmounts[colour.ordinal()]++;
	}

	public void add(Gem[][] colour) {
		for (int k = 0; k < colour.length; k++) {
			for (int i = 0; i < colour[0].length; i++) {
				if (colour[k][i] != null) {
					gemAmounts[colour[k][i].getColour().ordinal()]++;
				}
			}
		}
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
//        batch.draw(texture, position.x, position.y);
		for (int i = 0; i < gemTextures.length; i++) {
			if (gemAmounts[i] != 0) {
				gemTextures[i].render(batch);

			} else {
				batch.draw(inactiveGem, slots[i].x, slots[i].y);
			}
		}
		for (int i = 0; i < gemTextures.length; i++) {
			Assets.getFont(24).draw(batch, "" + gemAmounts[i], slots[i].x + 30, slots[i].y + 16);
		}
	}

	public int getAmount(GemColour colour) {
		return gemAmounts[colour.ordinal()];
	}

	/*Here is how random gems with possibility implemented:
	 *Case 1: toggle 1 gem: If other gems is randomed then increase % of that one gem until that gem is randomed then reset all 4 to 25%
	 *Case 2: toggle 2 gems: If other 2 gems is randomed then increase % of these 2 gems. If one of these 2 gems i randomed, then reset that gem back to 25%, equally add the difference to the other three.
	 *Case 3: toggle 3 or more: same method with 2 gems
	 */
	public void gainRandomGem() {
		Random random = new Random();
		double probability = random.nextDouble() * 100; // general probability
		double ceiling = 0;
		double floor = 0;
		for (int k = 0; k < gemTextures.length; k++) {
			floor = ceiling;
			ceiling += gemProbabilities[k];
			if (probability < ceiling && probability >= floor) { // if it matches the random range of gem
				add(GemColour.values()[k]);
				if (cumulativeProbabilities[k] > 0) { // if the chosen gem is the desired one
					for (int h = 0; h < gemTextures.length; h++) {// equally distribute % to the rest of the gems
						if (h != k) {
							gemProbabilities[h] += (gemProbabilities[k] - 25) / 3;
						}
					}
					gemProbabilities[k] = 25; // set the desired gem's % back to 25
				} else { // the chosen gem is the undesired one
					addCumulatives();
				}
				return;
			}
		}
	}

	private void addCumulatives() {
		for (int k = 0; k < gemTextures.length; k++) {
			gemProbabilities[k] += cumulativeProbabilities[k];
		}

	}

	public void remove(GemColour colour) {
		gemAmounts[colour.ordinal()]--;
	}

	public void setCumulativeProbabilities(double[] cumulativeProbabilities) {
		this.cumulativeProbabilities = cumulativeProbabilities;
	}

	@Override
	public void update(float delta) {

	}
}
