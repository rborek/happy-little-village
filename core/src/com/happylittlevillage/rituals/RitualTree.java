package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.Assets;
import com.happylittlevillage.objects.RotatableGameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RitualTree extends GameObject {
	private GameHandler gameHandler;
	private int blackGem = 18;
	private ArrayList<Ritual> unlockedRituals = new ArrayList<Ritual>(); // all the unlocked rituals. Equal or greater than chosenRituals
	private ArrayList<Ritual> chosenRituals = new ArrayList<Ritual>(); // chosen rituals and passed in ritualBook and ritualAltar
	private RitualNode viewingRitual = null;
	private static HashMap<Integer, RitualNode> ritualIndexOnTree = new HashMap<Integer, RitualNode>();

	private RotatableGameObject dot = new RotatableGameObject(Assets.getTexture("ui/dot.png"), 0, 0);
	private RotatableGameObject dot2 = new RotatableGameObject(Assets.getTexture("ui/dot2.png"), 0, 0);
	private GameObject lockedRitualTextureOnTree = new GameObject(Assets.getTexture("ui/ritual_on_tree_locked.png"), 0, 0, 80, 80);
	private GameObject surroundingPickAxe = new GameObject(Assets.getTexture("ui/surround_pick_axe.png"), 387, 369, 100, 100);
	//	private GameObject unlockedRitualTexturesOnTree = new GameObject(Assets.getTexture("ui/ritual_on_tree_activated.png"), 0, 0);
	private static HashMap<String, GameObject> unlockedRitualTexturesOnTree = new HashMap<String, GameObject>();
	private GameObject continueButton = new GameObject(Assets.getTexture("ui/continue_button.png"), position.x + 1020, position.y);
	private Rectangle continueButtonPosition = new Rectangle(continueButton.getPosition().x, continueButton.getPosition().y, continueButton.getWidth(), continueButton.getHeight());
	private GameObject chooseButton = new GameObject(Assets.getTexture("ui/choose_button.png"), 925, 170);
	private GameObject chosenButton = new GameObject(Assets.getTexture("ui/chosen_button.png"), 925, 170);
	private GameObject unlockButton = new GameObject(Assets.getTexture("ui/unlock_button.png"), 925, 170);
	private GameObject lockedButton = new GameObject(Assets.getTexture("ui/lock_button.png"), 925, 170);
	private GameObject resetButton;
	private GameObject chosenSign = new GameObject(Assets.getTexture("ui/chosen_sign.png"), 0, 0, 35, 35);
	private Rectangle chooseButtonPosition = new Rectangle(chooseButton.getPosition().x, chooseButton.getPosition().y, chooseButton.getWidth(), chooseButton.getHeight()); // choose a Ritual
	private Rectangle nextButtonPosition = new Rectangle(1055, 80, 70, 65); // for the chosen ritual bar
	private Rectangle prevButtonPosition = new Rectangle(1160, 80, 70, 65);// for the chosen ritual bar
	private RotatableGameObject pickAxe = new RotatableGameObject(Assets.getTexture("ui/pick_axe.png"), 405, 390, 60, 60);
	private Rectangle pickAxePosition = new Rectangle(pickAxe.getPosition().x, pickAxe.getPosition().y, pickAxe.getWidth(), pickAxe.getHeight());
	private boolean viewPickAxe = false;
	private boolean pickAxeUnlocked = false;
	private GameObject blackGemTexture = new GameObject(Assets.getTexture("gems/gem_black.png"), 0, 0, 40, 40);
	private static final int ritualSize = 80;

	private Rectangle[] ritualPositionsOnTree = {
			new Rectangle(70, 525, ritualSize, ritualSize),  // tier 1
			new Rectangle(70, 400, ritualSize, ritualSize),
			new Rectangle(70, 275, ritualSize, ritualSize),

			new Rectangle(195, 565, ritualSize, ritualSize), // tier 2
			new Rectangle(195, 445, ritualSize, ritualSize),
			new Rectangle(195, 325, ritualSize, ritualSize),
			new Rectangle(195, 205, ritualSize, ritualSize),

			new Rectangle(320, 565, ritualSize, ritualSize), // tier 3
			new Rectangle(320, 445, ritualSize, ritualSize),
			new Rectangle(320, 325, ritualSize, ritualSize),
			new Rectangle(320, 205, ritualSize, ritualSize),

			new Rectangle(470, 565, ritualSize, ritualSize), // tier 4
			new Rectangle(470, 445, ritualSize, ritualSize),
			new Rectangle(470, 325, ritualSize, ritualSize),
			new Rectangle(470, 205, ritualSize, ritualSize),

			new Rectangle(575, 565, ritualSize, ritualSize), // tier 5
			new Rectangle(575, 445, ritualSize, ritualSize),
			new Rectangle(575, 325, ritualSize, ritualSize),
			new Rectangle(575, 205, ritualSize, ritualSize),

			new Rectangle(680, 580, ritualSize, ritualSize), // tier 6
			new Rectangle(680, 485, ritualSize, ritualSize),
			new Rectangle(680, 390, ritualSize, ritualSize),
			new Rectangle(680, 295, ritualSize, ritualSize),
			new Rectangle(680, 200, ritualSize, ritualSize),
	};
	// two vector2 to render lines
	private Vector2 start = new Vector2(0, 0);
	private Vector2 end = new Vector2(0, 0);

	public RitualTree(GameHandler gameHandler, float xPos, float yPos) {
		super(Assets.getTexture("ui/ritual_tree.png"), xPos, yPos);
		this.gameHandler = gameHandler;
		createIndexesOfRituals(); // synchronize rituals with their positions on the ritualTree
		setPrerequisites();
		addPresetChosenRituals(); // add default chosen rituals
		texturesOfUnlockedRituals();
		for (String name : Ritual.getRitualNames()) {
			if (Ritual.getRituals().containsKey(name)) {
				System.out.println("ritual is " + Ritual.getRitualNode(name).toString());
			}
		}

	}

	private void createIndexesOfRituals() {
		// this hashmap will connect each ritual to each index/position on the tree so that ritualPositionOnTree can just call the index for interact/render
		// TODO since Ritual reads ritual in alphabetical order, renaming or adding new files in the middle will mess up the order of the rituals on the tree
		int index = 0;
		for (String name : Ritual.getRitualNames()) {
			ritualIndexOnTree.put(index, Ritual.getRitualNode(name));
			index++;
		}
	}

	private void texturesOfUnlockedRituals() {
		unlockedRitualTexturesOnTree.put("FARMER", new GameObject(Assets.getTexture("ui/ritual_on_tree_farmer.png"), 0, 0, 80, 80));
		unlockedRitualTexturesOnTree.put("EXPLORER", new GameObject(Assets.getTexture("ui/ritual_on_tree_explorer.png"), 0, 0, 80, 80));
		unlockedRitualTexturesOnTree.put("MINER", new GameObject(Assets.getTexture("ui/ritual_on_tree_miner.png"), 0, 0, 80, 80));
		unlockedRitualTexturesOnTree.put("VILLAGER", new GameObject(Assets.getTexture("ui/ritual_on_tree_villager.png"), 0, 0, 80, 80));
		unlockedRitualTexturesOnTree.put("FOOD", new GameObject(Assets.getTexture("ui/ritual_on_tree_food.png"), 0, 0, 80, 80));
		unlockedRitualTexturesOnTree.put("WATER", new GameObject(Assets.getTexture("ui/ritual_on_tree_water.png"), 0, 0, 80, 80));
		unlockedRitualTexturesOnTree.put("SACRIFICE", new GameObject(Assets.getTexture("ui/ritual_on_tree_sacrifice.png"), 0, 0, 80, 80));
		unlockedRitualTexturesOnTree.put("HAPPINESS", new GameObject(Assets.getTexture("ui/ritual_on_tree_happy.png"), 0, 0, 80, 80));
	}


	private void setPrerequisites() { // this code is very very sloppy and ugly
		ArrayList<String> prerequisites = new ArrayList<String>();
		prerequisites.add("Dried meat");
		addPrerequisites("Farming is fun!", prerequisites);

		prerequisites.add("Dried meat");
		prerequisites.add("Well water");
		addPrerequisites("Mining time!", prerequisites);

		prerequisites.add("Well water");
		prerequisites.add("A little party");
		addPrerequisites("Time to explore!", prerequisites);

		prerequisites.add("A little party");
		addPrerequisites("Reproduce", prerequisites);

		for (int k = 7; k < 20; k++) { // automatically add prerequisites for indexes from 7 to 20
			prerequisites.add(ritualIndexOnTree.get(k - 4).getRitual().getName());
			addPrerequisites(ritualIndexOnTree.get(k).getRitual().getName(), prerequisites);
		}

		for (int k = 20; k < 23; k++) { // automatically add prerequisites for indexes from 20 to 23
			prerequisites.add(ritualIndexOnTree.get(k - 5).getRitual().getName());
			prerequisites.add(ritualIndexOnTree.get(k - 4).getRitual().getName());
			addPrerequisites(ritualIndexOnTree.get(k).getRitual().getName(), prerequisites);
		}

		prerequisites.add("A large party");
		addPrerequisites("Super Fertility", prerequisites);
	}

	//TODO need to add condition so that it is impossible to remove preset rituals
	private void addPresetChosenRituals() {
		chosenRituals.add(Ritual.getRitual("Dried meat"));
		chosenRituals.add(Ritual.getRitual("Well water"));
		chosenRituals.add(Ritual.getRitual("A little party"));


		for (Ritual ritual : chosenRituals) {
			unlockedRituals.add(ritual);
		}
		//unlock prerequisites according to unlocked Rituals
		for (Ritual ritual : unlockedRituals) {
			for (Map.Entry<Integer, RitualNode> entry : ritualIndexOnTree.entrySet()) {
				if (entry.getValue().getRitual() == ritual) {
					entry.getValue().activate();
				}
			}

		}
	}

	public void addWeeklyRitualToChosenRitual(Ritual weeklyRitual) {
		chosenRituals.add(0, weeklyRitual);
	}

	private void addPrerequisites(String name, ArrayList<String> prerequisites) {
		HashMap<String, RitualNode> allRituals = Ritual.getRituals(); // shorten the name
		if (allRituals.containsKey(name)) {
			for (String prerequisite : prerequisites) {
				RitualNode node = allRituals.get(prerequisite);
				allRituals.get(name).setPrerequisites(node); // set prerequisites
			}
		}
		prerequisites.clear();
	}

	public ArrayList<Ritual> getUnlockedRituals() {
		return unlockedRituals;
	}

	public ArrayList<Ritual> getChosenRituals() {
		return chosenRituals;
	}

	public int getBlackGem() {
		return blackGem;
	}

	public void setBlackGem(int blackGem) {
		this.blackGem = blackGem;
	}

	public boolean interact(float mouseX, float mouseY) {
		//continue the game
		if (continueButtonPosition.contains(mouseX, mouseY)) {
			gameHandler.getVillage().setBlackGem(0);
			gameHandler.unpauseInGame();
			return true;
		}
		//click on the pickAxe
		else if (pickAxePosition.contains(mouseX, mouseY)) {
			viewingRitual = null;
			viewPickAxe = true;
		}
		//TODO slide the bar
		else if (nextButtonPosition.contains(mouseX, mouseY) || prevButtonPosition.contains(mouseX, mouseY)) {
			System.out.println("Slide the bar");
		}
		//view ritual
		else if (touchRitual(mouseX, mouseY)) {
			return true;
		}
		// choose ritual
		else if (chooseButtonPosition.contains(mouseX, mouseY)) {
			if (viewingRitual != null) {
				System.out.println("View/Unlock/Choose");
				// if it is not unlocked
				if (blackGem > 0) { // if there is enough skillpoint
					if (!unlockedRituals.contains(viewingRitual.getRitual())) { // if it is not in the unlocked list
						if(viewingRitual.getRitual().getBlackGemRequire() <= blackGem){
							if (viewingRitual.activate()) { // if it is activated. Return true also activates it
								unlockedRituals.add(viewingRitual.getRitual());
								blackGem -= viewingRitual.getRitual().getBlackGemRequire();
								return true;
							}
						}
					}
				}
				//if it is unlocked, then select it and put it in the bar. Do not decrement skillPoint
				//if it is not in chosenRituals and it is unlocked
				if (!chosenRituals.contains(viewingRitual.getRitual()) && unlockedRituals.contains(viewingRitual.getRitual())) {
					chosenRituals.add(viewingRitual.getRitual());
					System.out.println("added to chosenRitual");
				}
				//unlock other ritual
				return true;
			}
		}
		// unChoose ritual from the bar
		else {
			System.out.println("size is:" + chosenRituals.size());
			//rectangle of the chosen ritual on the bar
			Rectangle chosenRitualPosition = new Rectangle(0, 0, 0, 0);
			for (int k = 0; k < chosenRituals.size(); k++) {
				chosenRitualPosition.set(40 + 100 * k, 20, 100, 120);
				if (chosenRitualPosition.contains(mouseX, mouseY)) {
					if (!chosenRituals.get(k).getName().equals("Weekly Ritual")) {
						chosenRituals.remove(k);
						System.out.println("REMOVED");
						return true;
					}
				}
			}
		}
		System.out.println("touched nothing and the size of chosenRitual is " + chosenRituals.size());
		return false;
	}

	private boolean touchRitual(float mouseX, float mouseY) {
		for (int k = 0; k < ritualPositionsOnTree.length; k++) {
			if (ritualPositionsOnTree[k].contains(mouseX, mouseY)) {
				// since each ritual is rendered with the same indexes as the Rectangle[],
				// they share the same indexes on the key
				viewingRitual = ritualIndexOnTree.get(k);
				viewPickAxe = false;
				System.out.println(viewingRitual.toString());
				return true;
			}
		}
		return false;
	}

	public void addBlackGem(int blackGem){
		this.blackGem += blackGem;
	}

	@Override
	public void update(float delta) {
		//check to enable the pickAxe
		if (!pickAxeUnlocked) {
			for (int k = 15; k < 19; k++) { // loop through tier 4, index 11-15
				if (ritualIndexOnTree.get(k).prerequisitesActivated()) {
					pickAxeUnlocked = true;
					break;
				}
			}
		}
	}

	@Override
	public void render(Batch batch) {
		BitmapFont font = Assets.getFont(30);
		batch.draw(texture, position.x, position.y, width, height);
		continueButton.render(batch);
		blackGemTexture.setPosition(1105, 550);
		blackGemTexture.render(batch);
		if (pickAxeUnlocked) {
			surroundingPickAxe.render(batch);
		}
		font.draw(batch, blackGem + "", 1120, 570);
		pickAxe.render(batch, 0, 0);
		//render lines
		renderLines(batch);
		//render rituals on tree
		for (int k = 0; k < ritualPositionsOnTree.length; k++) {
			if (unlockedRituals.contains(ritualIndexOnTree.get(k).getRitual())) { // for rituals that are unlocked
				String firstEffect = ritualIndexOnTree.get(k).getRitual().getEffects()[0].getModifier().toString();
				if (firstEffect.equals("VILLAGER")) {
					if (ritualIndexOnTree.get(k).getRitual().getEffects()[0].getAmount() < 0) {
						firstEffect = "SACRIFICE";
					}
				}
				unlockedRitualTexturesOnTree.get(firstEffect).setPosition(ritualPositionsOnTree[k].x, ritualPositionsOnTree[k].y);
				unlockedRitualTexturesOnTree.get(firstEffect).render(batch);
				if (chosenRituals.contains(ritualIndexOnTree.get(k).getRitual())) { // for rituals that are unlocked and chosen
					chosenSign.setPosition(ritualPositionsOnTree[k].x + 50, ritualPositionsOnTree[k].y + 50);
					chosenSign.render(batch);
				}
			} else { // for the locked rituals
				lockedRitualTextureOnTree.setPosition(ritualPositionsOnTree[k].x, ritualPositionsOnTree[k].y);
				lockedRitualTextureOnTree.render(batch);
				blackGemTexture.setPosition(ritualPositionsOnTree[k].x + 45, ritualPositionsOnTree[k].y);
				blackGemTexture.render(batch);
				font.draw(batch, ritualIndexOnTree.get(k).getRitual().getBlackGemRequire() + "", ritualPositionsOnTree[k].x + 45, ritualPositionsOnTree[k].y + 30);
			}
		}


		//render the viewing ritual
		if (viewingRitual != null) {
			if (!viewingRitual.prerequisitesActivated()) { // if the ritual is locked
				lockedButton.render(batch);
			} else if (!unlockedRituals.contains(viewingRitual.getRitual()) && blackGem > 0) { // if the ritual is unlockable
				unlockButton.render(batch);
			} else { // if the ritual is unlocked. Now we can see its info
				viewingRitual.getRitual().render(batch, font, 845, 605, 835, 355, 54, 6);
				if (!chosenRituals.contains(viewingRitual.getRitual())) {
					chooseButton.render(batch);
				} else {
					chosenButton.render(batch);
				}
			}
		}

		if (viewPickAxe) {
			String description;
			if (!pickAxeUnlocked) {
				description = "Pick Axe allows to monitor mining gems\n" +
						"unlocked automatically upon\n" + "unlocking any tier 4 ritual";
			} else {
				description = "Pick Axe has been unlocked\n" + "Click the icon on the mine to use it\n";
			}
			font.draw(batch, description, 840, 390);
		}
		//render chosen rituals on the bar
		if (chosenRituals.size() != 0) {
			for (int k = 0; k < chosenRituals.size(); k++) {
				chosenRituals.get(k).renderRecipe(batch, 40 + 100 * k, 115, 20, 4);
			}
		}


	}

	private void renderLines(Batch batch) {
		for (Map.Entry<Integer, RitualNode> entry : ritualIndexOnTree.entrySet()) { // for each ritual
			int key = entry.getKey(); // get the integer index
			start.set(ritualPositionsOnTree[key].x + ritualSize / 2, ritualPositionsOnTree[key].y + ritualSize / 2);
			//get the end point of lines
			RitualNode value = entry.getValue(); // get the ritualNode
			for (RitualNode ritualNode : value.getPrerequisiteRituals()) { //loop through the prerequisites
				// need to find the key2 that matches each ritualNode
				int key2 = 0;
				for (Map.Entry<Integer, RitualNode> entry1 : ritualIndexOnTree.entrySet()) { // for each ritual
					if (entry1.getValue() == ritualNode) { // if it matches the prerequisite of the ritual in key
						key2 = entry1.getKey();
						break;
					}
				}
				end.set(ritualPositionsOnTree[key2].x + ritualSize, ritualPositionsOnTree[key2].y + ritualSize / 2);

				drawLines(start, end, batch, ritualNode.isActivated());
			}

		}
	}

	private void drawLines(Vector2 start, Vector2 end, Batch batch, boolean activated) {
		float distanceBetweenDot = 2;
		double distance = Math.sqrt(Math.pow(end.y - start.y, 2) + Math.pow(end.x - start.x, 2));
		int times = (int) (distance / distanceBetweenDot);
		Vector2 dir = new Vector2(end.x - start.x, end.y - start.y);
		int signX = 1;
		int signY = 1;
		if (dir.x < 0) {
			signX = -1;
			if (dir.y < 0) {
				signY = -1;
			} else {
				signY = 1;
			}
		} else {
			if (dir.y < 0) {
				signY = -1;
			}
		}
		double slope = (dir.y) / (dir.x);
		double angle = Math.atan(slope);
		double y = start.y;
		double x = start.x;
		int count = 0;
		while (count < times) {
			if (activated) {
				dot2.render(batch);
			} else {
				dot.render(batch);
			}

			y += Math.abs(Math.sin(angle)) * distanceBetweenDot * signY;
			x += Math.abs(Math.cos(angle)) * distanceBetweenDot * signX;
			;
			if (activated) {
				dot2.setPosition((float) x, (float) y);
				dot2.setAngle((float) angle  * 57.2958f);
			} else {
				dot.setPosition((float) x, (float) y);
				dot.setAngle((float) angle * 57.2958f);
			}
			count++;
		}
	}

	public boolean isPickAxeUnlocked() {
		return pickAxeUnlocked;
	}
}