package com.happylittlevillage.messages;

import com.happylittlevillage.GameHandler;

public class Introduction extends MessageBox {
	private static String instruction = "This is the game's tutorial";

	public Introduction(GameHandler gameHandler, boolean isTutorial) {
		super(instruction, gameHandler);
		if(isTutorial){
			text = "Welcome to Happy Little Village. Please press continue";
		}

	}

}
