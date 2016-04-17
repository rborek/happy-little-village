package com.happylittlevillage.messages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.objects.RotatableGameObject;
import com.happylittlevillage.gems.GemBook;
import com.happylittlevillage.gems.GemColour;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.Assets;

public class TutorialMessage extends MessageBox {
	protected RotatableGameObject arrow = new RotatableGameObject(Assets.getTexture("ui/arrow.png"), 0, 0);
	//this list contains position x and y of each tutorial messages
	private static float[] positionOfEachMessage =
			{480, 590,
					480, 590, // 1
					80, 200,
					350, 200,
					475, 400,
					475, 400,
					475, 400, // 6
					475, 400,
					480, 590,
					480, 590,
					690, 500,
					690, 500, //11
					690, 500,
					690, 500,
					690, 500,
					690, 500,
					690, 500, //16
					480, 590};
	private int positionIndex = 0;
	private RitualAltar ritualAltar;
	private int tutorialScreen = 0;
	private GameObject supportTexture1 = new GameObject(Assets.getTexture("gems/gem_yellow.png"), 0, 0);
	private GameObject supportTexture2 = new GameObject(Assets.getTexture("gems/gem_yellow.png"), 0, 0);
	private GameObject supportTexture3 = new GameObject(Assets.getTexture("gems/gem_yellow.png"), 0, 0);

	private static int[] disableContinueButton = {4, 5, 6, 7, 9};
	private static int[] disableBackButton = {0, 4, 5, 6, 7, 8, 9, 10};
	private static int[] noArrowScreen = {7, 8};
	private boolean disableBack = false;
	private boolean disableContinue = false;
	private boolean noArrow = false;
	private boolean clickTheButton = false; //for # 7 when player clicks to combine ritual
	private boolean gemPlaced = false;
	private float delta;
	private float case8Time = 0;
	//Exclusively for the arrow
	Vector2 move = null;
	private float arrowMoveX = 0;
	private float arrowMoveY = 0;
	private float arrowPositionX = 0;
	private float arrowPositionY = 0;
	private float supportX1 = 0, supportY1 = 0, supportX2 = 0, supportY2 = 0, supportX3 = 0, supportY3 = 0;
	GemBook gemBook;

	public TutorialMessage(GameHandler gameHandler, RitualAltar ritualAltar, GemBook gemBook) {
		super(gameHandler);
		this.ritualAltar = ritualAltar;
		this.gemBook = gemBook;
		texture = Assets.getTexture("ui/tutorialMessageBox.png");
//        continueButton = Assets.getTexture("ui/tutorialContinueButton.png");
//        backButton = Assets.getTexture("ui/tutorialBackButton.png");
		position.x = positionOfEachMessage[0];
		position.y = positionOfEachMessage[1];
		switchText();
	}

	private void switchText() {
		switch (tutorialScreen) {
			case 0:
				text = "This is one of your villager";
				break;
			case 1:
				text = "There are three type of special villagers";
				break;
			case 2:
				text = "This is your village's food and their happiness";
				break;
			case 3:
				text = "Your village's population \nand the remaining time of today";
				break;
			//simple ritual
			case 4:
				text = "Pick up a yellow gem and put it here";
				break;
			case 5:
				text = "Pick up a red gem and put it here";
				break;
			case 6:
				text = "Pick up another red gem and put it here";
				break;
			case 7:
				text = "This combination of Yellow and Red Gem create a ritual generates food but drains happiness \nClick commence and observe";
				break;
			case 8:
				text = "You can put the gems in a any grid you want \n as long as the components align with the recipe";
				break;
			//convert miner/explorer/farmer
			case 9:
				text = "All the unlocked ritual can be viewed in the ritualBook. Click to open it";
				break;
			case 10:
				text = "Here are the combinations \nand a abilities of each ritual";
				break;
			case 11:
				text = "Some rituals will reduce your village's happiness. So be wise to use it";
				break;
			case 12:
				text = "These rituals can turn your villagers into special villagers. Miners mine, farmer farm, explorers explore";
			case 13:
				text = "Rituals can be combined to reduce gems used";
				break;
			case 14:
				text = "However, if the gem does not follow any recipe\n you will waste it";
				break;
			case 15:
				text = "Click to close the book";
				break;
			case 16:
				text = "For each week there is a mandatory weekly ritual that needs to be done before time runs out";
				break;
			default:
				text = "End";


		}
	}

	public void setAngle(Vector2 positionOfARandomVillager, Vector2 arrowPoint) {
		double angle = Math.atan((positionOfARandomVillager.y - arrowPoint.y) / (positionOfARandomVillager.x - arrowPoint.x));
		//boolean flip is to know if the angle is flipped. Since tan is the same in the first and third quadrant
		angle = angle * 57.2958; //convert radian to degree
		boolean flip = false;
		if (positionOfARandomVillager.y - arrowPoint.y < 0 && positionOfARandomVillager.x - arrowPoint.x < 0) {
			flip = true;
		}
		if (flip) {
			angle += 180;
		}
		arrow.setAngle((float) angle);
	}

	public int getTutorialScreen() {
		return tutorialScreen;
	}

	@Override
	public void render(Batch batch) {
		//Since some supportingTextures need to be drawn after the box. We need to separate which case the texture or the supportingTextures is being rendered first
		//REMINDER of draw batch: scale=1 is normal. srcX,srcY and scrWidth/Height mark the render portion of the actual image not the game
		batch.draw(texture, position.x, position.y);
		switch (tutorialScreen) {
			//point village
			case 0:
				break;
			//explain types of villagers
			case 1:
//                supportTexture1 = Assets.getTexture("villagers/explorer/explorer.png");
//                supportTexture2 = Assets.getTexture("villagers/farmer/farmer.png");
//                supportTexture3 = Assets.getTexture("villagers/miner/miner.png");
//                batch.draw(supportTexture1, position.x, position.y + 30);
//                batch.draw(supportTexture2, position.x + supportTexture1.getWidth() + 50, position.y + 30);
//                batch.draw(supportTexture3, position.x + supportTexture2.getWidth() + supportTexture2.getWidth() + 100, position.y + 30);
				break;
			case 8:
				supportTexture1.setTexture(Assets.getTexture("gems/gem_yellow.png"));
				supportTexture2.setTexture(Assets.getTexture("gems/gem_red.png"));
				supportTexture3.setTexture(Assets.getTexture("gems/gem_red.png"));
				break;
		}
		if (!noArrow) {
			arrow.render(batch);
			if (tutorialScreen == 2) { // 2 arrow for Screen 2
				arrow.setPosition(175, 170);
				arrow.render(batch);
			}
		}
		if (!disableContinue) {
//            batch.draw(continueButton, position.x + texture.getWidth() - continueButton.getWidth(), position.y);
		}
		if (!disableBack && tutorialScreen != 0) {
//            batch.draw(backButton, position.x, position.y);
		}
		if (tutorialScreen == 8) {
			supportTexture1.render(batch);
			supportTexture2.render(batch);
			supportTexture3.render(batch);
		}
		font = Assets.getFont(24);
		if (text != null) {
			font.draw(batch, text, position.x, position.y + 100);
		}
	}

	@Override
	public void update(float delta) {
		this.delta = delta;
		disableBack = false;
		disableContinue = false;
		noArrow = false;
		for (int a : disableBackButton) {
			if (tutorialScreen == a) {
				disableBack = true;
				break;
			}
		}
		//check if the screen's continue button is disabled
		for (int a : disableContinueButton) {
			if (tutorialScreen == a) {
				disableContinue = true;
				break;
			}
		}
		for (int a : noArrowScreen) {
			if (tutorialScreen == a) {
				noArrow = true;
				break;
			}
		}
		switch (tutorialScreen) {
			case 0:
				arrow.setPosition(position.x - 70, position.y - texture.getHeight() / 2 - 40);
				break;
			case 1:
				arrow.setPosition(position.x - 70, position.y - texture.getHeight() / 2 - 40);
				break;
			case 2:
				arrow.setPosition(125, 170);
				arrow.setAngle(270);
				break;
			case 3:
				arrow.setPosition(400, 170);
				arrow.setAngle(270);
				break;
			case 4:
				setMovingPosition(delta, 1150, 80, 960, 500);
				arrow.setAngle(120);
				break;
			case 5:
				setMovingPosition(delta, 875, 80, 930, 360);
				arrow.setAngle(70);
				break;
			case 6:
				setMovingPosition(delta, 875, 80, 930, 270);
				arrow.setAngle(65);
				break;
			case 8:
				case8Time += delta;
				supportY1 = 390;
				supportY2 = 390 + 80;
				supportY3 = 390 + 160;
				if (case8Time < 1) {
					supportX1 = 845;
				} else if (case8Time < 2) {
					supportX1 = 925;

				} else if (case8Time < 3) {
					supportX1 = 1005;

				} else if (case8Time < 4) {
					supportX1 = 1085;
				} else {
					case8Time = 0;
				}
				supportTexture1.setPosition(supportX1, supportY1);
				supportTexture2.setPosition(supportX1, supportY2);
				supportTexture3.setPosition(supportX1, supportY3);
				break;
			case 9:
				arrow.setPosition(700, 200);
				arrow.setAngle(270);
				break;
			case 11:

			case 12:
				break;
		}
		//small if/else statements to move to nextScreen
		if (ritualAltar.getColour(1, 1) == GemColour.YELLOW && tutorialScreen == 4) {
			nextScreen();
		} else if (ritualAltar.getColour(2, 1) == GemColour.RED && tutorialScreen == 5) {
			nextScreen();
		} else if (ritualAltar.getColour(3, 1) == GemColour.RED && tutorialScreen == 6) {
			nextScreen();
		} else if (clickTheButton && tutorialScreen == 7) {
			nextScreen();
		} else if (gemBook.isOpen() && tutorialScreen == 9) {
			nextScreen();
		} else if (!gemBook.isOpen() && tutorialScreen == 15) {
			nextScreen();
		}

	}

	public void alreadyClick() {
		clickTheButton = true;
	}

	//TODO Change setMovingPosition
	private void setMovingPosition(float delta, float startX, float startY, float endX, float endY) {
		float moveX = (endX - startX) * delta / 3;
		float moveY = (endY - startY) * delta / 3;
		arrowMoveX -= moveX;
		arrowMoveY -= moveY;
		//this temporary code makes sure the arrow repeats
		if (startX < endX) {
			//going to the right
			if (startX + arrowMoveX > endX - 100) {
				arrowMoveX = 0;
				arrowMoveY = 0;
			}
		} else if (startX > endX) {
			//going to the left
			if (startX + arrowMoveY < endX + 100) {
				arrowMoveX = 0;
				arrowMoveY = 0;

			}
		}
		arrow.setPosition(startX - arrowMoveX, startY - arrowMoveY);
	}

	@Override
	public boolean interact(float mouseX, float mouseY) {
		//check if the screen's back button is disabled
		if (!disableBack) {
			Rectangle backPosition = new Rectangle(position.x, position.y, backButton.getWidth(), backButton.getHeight());
			if (backPosition.contains(mouseX, mouseY)) {
				prevScreen();
				return true;
			}
		}
		if (!disableContinue) {
			Rectangle continuePosition = new Rectangle(position.x + texture.getWidth() - continueButton.getWidth(), position.y, continueButton.getWidth(), continueButton.getHeight());
			if (continuePosition.contains(mouseX, mouseY)) {
				nextScreen();
				return true;
			}
		}
		return false;
	}

	private void nextScreen() {
		positionIndex += 2;
		position.x = positionOfEachMessage[positionIndex];
		position.y = positionOfEachMessage[positionIndex + 1];
		tutorialScreen++;
		switchText();
	}

	private void prevScreen() {
		positionIndex -= 2;
		position.x = positionOfEachMessage[positionIndex];
		position.y = positionOfEachMessage[positionIndex + 1];
		tutorialScreen--;
		switchText();
	}

}
