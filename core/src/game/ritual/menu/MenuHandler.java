package game.ritual.menu;

import game.ritual.GameHandler;

import java.util.ArrayList;

public class MenuHandler {
    private GameHandler gameHandler;
    private ArrayList<MenuItem> menuItems;

    public MenuHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        menuItems = new ArrayList<>();
    }

}
