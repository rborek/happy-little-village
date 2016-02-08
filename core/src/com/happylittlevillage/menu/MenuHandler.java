package com.happylittlevillage.menu;

import com.happylittlevillage.GameHandler;

import java.util.ArrayList;

public class MenuHandler {
    private GameHandler gameHandler;
    private ArrayList<MenuItem> menuItems;

    public MenuHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        menuItems = new ArrayList<MenuItem>();
    }

}
