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
    private int skillPoints = 1;

    private ArrayList<Ritual> unlockedRituals = new ArrayList<Ritual>();
    private ArrayList<Ritual> chosenRituals = new ArrayList<Ritual>();
    private RitualNode viewingRitual = null;
    private static HashMap<Integer, RitualNode> ritualIndexOnTree = new HashMap<Integer, RitualNode>();

    private GameObject dot = new GameObject(Assets.getTexture("ui/dot.png"), 0, 0);
    private GameObject ritualTextureOnTree = new GameObject(Assets.getTexture("ui/ritual_on_tree.png"), 0, 0);
    private GameObject continueButton = new GameObject(Assets.getTexture("ui/continue_button.png"), 1060, 23);
    private Rectangle continueButtonPosition = new Rectangle(continueButton.getPosition().x, continueButton.getPosition().y, continueButton.getWidth(), continueButton.getHeight());
    private Rectangle chooseButton = new Rectangle(1100, 100, 150, 70); // choose a Ritual
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
        for (String name : Ritual.getRitualNames()) {
            chosenRituals.add(Ritual.getRitual(name));
        }
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
        for (String name : Ritual.getRitualNames()) {
            chosenRituals.add(Ritual.getRitual(name));
        }
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
        if (continueButtonPosition.contains(mouseX, mouseY)) {
            gameHandler.unpause();
            return true;
        } else if (nextButtonPosition.contains(mouseX, mouseY) || prevButtonPosition.contains(mouseX, mouseY)) {
            System.out.println("Touch change");
        } else if (touchRitual(mouseX, mouseY)) {
            return true;
        } else if (viewingRitual != null) {
            if (chooseButton.contains(mouseX, mouseY)) {
                System.out.println("PIKACHU I CHOOSE YOU");
            }
        }
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
            ritualTextureOnTree.setPosition(ritualPositionsOnTree[k].x, ritualPositionsOnTree[k].y);
            ritualTextureOnTree.render(batch);
        }
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

        if (viewingRitual != null) {
            DynamicRitual.renderRitual(batch, font, viewingRitual.getRitual(), 830, 630, 835, 385, 54, 6);
        }

    }

    private void drawLines(Vector2 start, Vector2 end, Batch batch) {
        float distanceBetweenDot = 10;
        double distance = Math.sqrt(Math.pow(end.y - start.y, 2) + Math.pow(end.x - start.x, 2));
//        System.out.println("distance is" + distance);
        int times = (int) (distance / distanceBetweenDot);
        Vector2 dir = new Vector2(end.x - start.x, end.y - start.y);
        int signX = 1;
        int signY = 1;
        if (dir.x < 0) {
            signX = -1;
            if (dir.y < 0) {
                signY = -1;
            }
            else{
                signY = 1;
            }
        }
        else{
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