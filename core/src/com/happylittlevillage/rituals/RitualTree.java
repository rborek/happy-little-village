package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RitualTree extends GameObject {
    private GameHandler gameHandler;
    private int skillPoints = 10;

    private ArrayList<Ritual> unlockedRituals = new ArrayList<Ritual>();
    private ArrayList<Ritual> chosenRituals = new ArrayList<Ritual>();
    private RitualNode viewingRitual = null;
    private static HashMap<Integer, RitualNode> ritualIndexOnTree = new HashMap<Integer, RitualNode>();

    private GameObject dot = new GameObject(Assets.getTexture("ui/dot.png"), 0, 0);
    private GameObject disabledRitualTextureOnTree = new GameObject(Assets.getTexture("ui/ritual_on_tree_disabled.png"), 0, 0);
    private GameObject enabledRitualTextureOnTree = new GameObject(Assets.getTexture("ui/ritual_on_tree_activated.png"), 0, 0);
    private GameObject continueButton = new GameObject(Assets.getTexture("ui/continue_button.png"), 1060, 23);
    private Rectangle continueButtonPosition = new Rectangle(continueButton.getPosition().x, continueButton.getPosition().y, continueButton.getWidth(), continueButton.getHeight());
    private GameObject chooseButton = new GameObject(Assets.getTexture("ui/choose_button.png"), 925, 170);
    private GameObject chosenButton = new GameObject(Assets.getTexture("ui/chosen_button.png"), 925, 170);
    private GameObject unlockButton = new GameObject(Assets.getTexture("ui/unlock_button.png"), 925, 170);
    private GameObject resetButton;
    private GameObject chosenSign = new GameObject(Assets.getTexture("ui/chosen_sign.png"), 0, 0);
    private Rectangle chooseButtonPosition = new Rectangle(chooseButton.getPosition().x, chooseButton.getPosition().y, chooseButton.getWidth(), chooseButton.getHeight()); // choose a Ritual
    private Rectangle nextButtonPosition = new Rectangle(1055, 80, 70, 65); // for the chosen ritual bar
    private Rectangle prevButtonPosition = new Rectangle(1160, 80, 70, 65);// for the chosen ritual bar
    private static final int ritualSize = 100;
    private Rectangle[] ritualPositionsOnTree = {
            new Rectangle(120, 425, ritualSize, ritualSize),
            new Rectangle(245, 540, ritualSize, ritualSize),
            new Rectangle(230, 290, ritualSize, ritualSize),
            new Rectangle(378, 550, ritualSize, ritualSize),
            new Rectangle(376, 287, ritualSize, ritualSize),
            new Rectangle(525, 548, ritualSize, ritualSize),
            new Rectangle(520, 302, ritualSize, ritualSize),
            new Rectangle(636, 543, ritualSize, ritualSize),
    };

    public RitualTree(GameHandler gameHandler, float xPos, float yPos) {
        super(Assets.getTexture("ui/ritual_tree.png"), xPos, yPos);
        this.gameHandler = gameHandler;
        addIndexOnTree(); // synchronize rituals with their positions on the ritualTree
        addUnlockedRituals();
        addChosenRituals();
        SetPrerequisites();
        for (String name : Ritual.getRitualNames()) {
            if (Ritual.getRituals().containsKey(name)) {
                System.out.println("" + Ritual.getRitualNode(name).toString());
            }
        }
    }

    private void addIndexOnTree() {
        // this hashmap will connect each ritual to each index on the tree so that ritualPositionOnTree can just call the index for interact
        // TODO since Ritual reads ritual in alphabetical order, renaming or adding new files in the middle will mess up the order of the rituals on the tree
        int index = 0;
        for (String name : Ritual.getRitualNames()) {
            ritualIndexOnTree.put(index, Ritual.getRitualNode(name));
            index++;
        }

    }

    private void addUnlockedRituals() {
        unlockedRituals.add(Ritual.getRitual("Dried meat"));
    }

    private void SetPrerequisites() {
        ArrayList<String> prerequisites = new ArrayList<String>();
        prerequisites.add("Dried meat");
        addPrerequisites("Liquid diet", prerequisites); // this one has 1 prerequisite
        System.out.println("matched");
        prerequisites.add("Liquid diet");
        addPrerequisites("Well water", prerequisites); // water has 2 prerequisite
        System.out.println("matched");
        prerequisites.clear(); //
        prerequisites.add("Farming is fun!");
        prerequisites.add("Liquid diet");
        addPrerequisites("Time to explore!", prerequisites);
        System.out.println("matched");

    }


    private void addChosenRituals() {
        chosenRituals.add(Ritual.getRitual("Dried meat"));
    }

    private void addPrerequisites(String name, ArrayList<String> prerequisites) {
        HashMap<String, RitualNode> allRituals = Ritual.getRituals(); // shorten the name
        if (allRituals.containsKey(name)) {
            for (String prerequisite : prerequisites) {
                RitualNode node = allRituals.get(prerequisite);
                allRituals.get(name).setPrerequisites(node); // set prerequisites

            }
        }
    }

    public ArrayList<Ritual> getUnlockedRituals() {
        return unlockedRituals;
    }

    public ArrayList<Ritual> getChosenRituals() {
        return chosenRituals;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public boolean interact(float mouseX, float mouseY) {
        //continue the game
        if (continueButtonPosition.contains(mouseX, mouseY)) {
            gameHandler.unpause();
            return true;
        }
        //slide the bar
        else if (nextButtonPosition.contains(mouseX, mouseY) || prevButtonPosition.contains(mouseX, mouseY)) {
            System.out.println("Slide the bar");
        }
        //view ritual
        else if (touchRitual(mouseX, mouseY)) {
            return true;
        }
        // choose ritual
        else if (chooseButtonPosition.contains(mouseX, mouseY)) {
            if (viewingRitual != null) {
                System.out.println("Choose");
                // if it is not unlocked, then unlock it and decrement skillPoint
                if (!unlockedRituals.contains(viewingRitual.getRitual()) && skillPoints > 0) {
                    unlockedRituals.add(viewingRitual.getRitual());
                    skillPoints--;
                }
                // if it is unlocked, then select it and put it in the bar. Do not decrement skillPoint
                else if (!chosenRituals.contains(viewingRitual.getRitual())) {
                    chosenRituals.add(viewingRitual.getRitual());
                }
                //unlock other ritual
                return true;
            }
        }
        // unChoose ritual from the bar
        //TODO I wanna pass in an array of positions of the bar in the if statement's argument but I cannot write a for loop there
        else {
            System.out.println("size is:" + chosenRituals.size());
            //rectangle of the chosen ritual on the bar
            Rectangle chosenRitualPosition = new Rectangle(0, 0, 0, 0);
            for (int k = 0; k < chosenRituals.size(); k++) {
                System.out.println("Checking chosen rituals");
                chosenRitualPosition.set(35 + 130 * k, 20, 130, 130);
                if (chosenRitualPosition.contains(mouseX, mouseY)) {
                    chosenRituals.remove(k);
                    System.out.println("REMOVED");
                    return true;
                }
            }
        }
        System.out.println("touched nothing and the size of chosenRitual is " + chosenRituals.size());
        return false;
    }

    private boolean touchRitual(float mouseX, float mouseY) {
        for (int k = 0; k < ritualPositionsOnTree.length; k++) {
            if (ritualPositionsOnTree[k].contains(mouseX, mouseY)) {
                // since each ritual is rendered with the same indexes as the Rectangle[],
                // they share the same indexes on the key
                viewingRitual = ritualIndexOnTree.get(k);
                System.out.println(viewingRitual.toString());
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Batch batch) {
        BitmapFont font = Assets.getFont(36);
        batch.draw(texture, position.x, position.y, width, height);
        continueButton.render(batch);
        font.draw(batch, skillPoints + "", 1105, 600);
        //render rituals on tree
        for (int k = 0; k < ritualPositionsOnTree.length; k++) {
            if (unlockedRituals.contains(ritualIndexOnTree.get(k).getRitual())) {
                enabledRitualTextureOnTree.setPosition(ritualPositionsOnTree[k].x, ritualPositionsOnTree[k].y);
                enabledRitualTextureOnTree.render(batch);
                if (chosenRituals.contains(ritualIndexOnTree.get(k).getRitual())) {
                    chosenSign.setPosition(ritualPositionsOnTree[k].x + 80, ritualPositionsOnTree[k].y + 80);
                    chosenSign.render(batch);
                }
            } else {
                disabledRitualTextureOnTree.setPosition(ritualPositionsOnTree[k].x, ritualPositionsOnTree[k].y);
                disabledRitualTextureOnTree.render(batch);
            }
        }
        //render lines
        renderLines(batch);
        //render viewing ritual
        if (viewingRitual != null) {
            DynamicRitual.renderRitual(batch, font, viewingRitual.getRitual(), 830, 630, 835, 385, 54, 6);
            // if the viewingRitual is already picked then render the chosenButton instead
            if (!unlockedRituals.contains(viewingRitual.getRitual()) && skillPoints > 0) {
                unlockButton.render(batch);
            } else if (!chosenRituals.contains(viewingRitual.getRitual())) {
                chooseButton.render(batch);
            } else {
                chosenButton.render(batch);
            }
        }
        //render chosen rituals
        if (chosenRituals.size() != 0) {
            for (int k = 0; k < chosenRituals.size(); k++) {
                DynamicRitual.renderRecipe(batch, font, chosenRituals.get(k), 35 + 150 * k, 115, 24, 6);
            }
        }

    }

    private void renderLines(Batch batch) {
        Vector2 start = new Vector2();
        Vector2 end = new Vector2();
        for (Map.Entry<Integer, RitualNode> entry : ritualIndexOnTree.entrySet()) {
            int key = entry.getKey();
            start.set(ritualPositionsOnTree[key].x + ritualSize / 2, ritualPositionsOnTree[key].y + ritualSize / 2);
            //get the end point of lines
            RitualNode value = entry.getValue();
            for (RitualNode ritualNode : value.getPrerequisiteRituals()) {
                // need to find the key2 that matches each ritualNode
                int key2 = 0;
                for (Map.Entry<Integer, RitualNode> entry1 : ritualIndexOnTree.entrySet()) {
                    if (entry1.getValue() == ritualNode) {
                        key2 = entry1.getKey();
                        break;
                    }
                }
                end.set(ritualPositionsOnTree[key2].x + ritualSize, ritualPositionsOnTree[key2].y + ritualSize / 2);
                drawLines(start, end, batch);
            }

        }
    }

    private void drawLines(Vector2 start, Vector2 end, Batch batch) {
        float distanceBetweenDot = 10;
        double distance = Math.sqrt(Math.pow(end.y - start.y, 2) + Math.pow(end.x - start.x, 2));
        int times = (int) (distance / distanceBetweenDot);
        Vector2 dir = new Vector2(end.x - start.x, end.y - start.y);
        int signX = 1;
        int signY = 1;
        if (dir.x < 0) {
            signX = -1;
            if (dir.y < 0) {
                signY = -1;
            } else {
                signY = 1;
            }
        } else {
            if (dir.y < 0) {
                signY = -1;
            }
        }
        double slope = (dir.y) / (dir.x);
        double degree = Math.atan(slope);
        double y = start.y;
        double x = start.x;
        int count = 0;
        while (count < times) {
            dot.render(batch);
            y += Math.abs(Math.sin(degree)) * distanceBetweenDot * signY;
            x += Math.abs(Math.cos(degree)) * distanceBetweenDot * signX;
            dot.setPosition((float) x, (float) y);
            count++;
        }
    }
}