package com.happylittlevillage.messages;

import com.happylittlevillage.GameHandler;

public class Menu extends MessageBox {
	private static String instruction = "Welcome to Happy Little Village";

	public Menu(GameHandler gameHandler) {
		super(instruction, gameHandler);
	}

}
