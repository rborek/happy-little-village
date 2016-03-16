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

    /**
     * Created by User on 14/03/16.
     */
    public static class Load {
    }
}
