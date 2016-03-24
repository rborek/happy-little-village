package com.happylittlevillage.messages;

import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.GameHandler;

public class Introduction extends MessageBox {
	private static String instruction = "Welcome to Happy Little Village!\nManage your resources by performing rituals\n" +
			"and keep your villagers alive.";

	public Introduction(GameHandler gameHandler, boolean isTutorial) {
		super(instruction, gameHandler);
		if(isTutorial){
			text = "Welcome to Happy Little Village. Please press continue";
		}

	}
}
