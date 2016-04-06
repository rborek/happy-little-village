package com.happylittlevillage.messages;

import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.GameHandler;

public class Introduction extends MessageBox {
	private static String instruction = "Welcome to Happy Little Village\nWisely utilize gems to perform rituals and flourish your " +
			"village";

	public Introduction(GameHandler gameHandler, boolean isTutorial) {
		super(instruction, gameHandler);
		if(isTutorial){
			text = "Welcome to Happy Little Village. Please press continue";
		}

	}

	@Override
	public boolean interact(float mouseX, float mouseY) {
		System.out.println("interacted with continue button");
		Rectangle r = new Rectangle(continueButton.getPosition().x, continueButton.getPosition().y, continueButton.getWidth(), continueButton.getHeight());
		if (r.contains(mouseX, mouseY)) {
			gameHandler.finishIntro();
			return true;
		}
		return false;
	}
}
