package game.ritual.messages;

import game.ritual.GameHandler;

/**
 * Created by User on 05/02/16.
 */
public class Introduction extends  MessageBox{
    private static String instruction = "This is the game's tutorial";
    public Introduction(GameHandler gameHandler) {
        super(instruction, gameHandler);
    }

}
