package com.happylittlevillage.messages;

import com.happylittlevillage.GameHandler;

public class Introduction extends MessageBox {
	private static String instruction = "Welcome to happy little village\nManage your resources by performing rituals";

	public Introduction(GameHandler gameHandler, boolean isTutorial) {
		super(instruction, gameHandler);
		if(isTutorial){
			text = "Welcome to Happy Little Village. Please press continue";
		}

	}

}
