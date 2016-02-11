package com.happylittlevillage.messages;

import com.happylittlevillage.GameHandler;

public class Introduction extends MessageBox {
	private static String instruction = "This is the game's tutorial";

	public Introduction(GameHandler gameHandler) {
		super(instruction, gameHandler);
	}

}
