package com.happylittlevillage.rituals;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameHandler;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.gems.GemColour;

import java.util.ArrayList;
import java.util.HashMap;

public class RitualTree extends GameObject {
    private GameHandler gameHandler;
    private int skillPoints = 1;

    private ArrayList<Ritual> unlockedRituals = new ArrayList<Ritual>();
    private ArrayList<Ritual> chosenRituals = new ArrayList<Ritual>();
    private RitualNode viewingRitual = null;
    private static HashMap<Integer, RitualNode> ritualIndexOnTree = new HashMap<Integer, RitualNode>();

    private GameObject continueButton = new GameObject(Assets.getTexture("ui/continue_button.png"), 1060, 23);
    private Rectangle continueButtonPosition = new Rectangle(continueButton.getPosition().x, continueButton.getPosition().y, continueButton.getWidth(), continueButton.getHeight());
    private Rectangle chooseButton = new Rectangle(1100, 100, 150, 70); // choose a Ritual
    private Rectangle nextButtonPosition = new Rectangle(1055, 80, 70, 65); // for the chosen ritual bar
    private Rectangle prevButtonPosition = new Rectangle(1160, 80, 70, 65);// for the chosen ritual bar
    private Rectangle[] ritualPositionsOnTree = {
            new Rectangle(120, 425, 50, 50),
            new Rectangle(245, 540, 50, 50),
            new Rectangle(230, 290, 50, 50),
            new Rectangle(378, 550, 50, 50),
            new Rectangle(376, 287, 50, 50),
            new Rectangle(525, 548, 50, 50),
            new Rectangle(520, 302, 50, 50),
            new Rectangle(636, 543, 50, 50),
            new Rectangle(636, 300, 50, 50),
    };

    public RitualTree(GameHandler gameHandler, float xPos, float yPos) {
        super(Assets.getTexture("ui/ritual_tree.png"), xPos, yPos);
        this.gameHandler = gameHandler;
        addIndexOnTree();
        addUnlockedRituals();
        addChosenRituals();
        SetPrerequisites();
        for (String name : Ritual.getRitualNames()) {
            if (Ritual.getRituals().containsKey(name)) {
                System.out.println("" + Ritual.getRitualNode(name).toString());
            }
        }
    }

    private void addIndexOnTree(){
        // this hashmap will connect each ritual to each index on the tree so that ritualPositionOnTree can just call the index for interact
        // TODO since Ritual reads ritual in alphabetical order, renaming or adding new files in the middle will mess up the order of the rituals on the tree
        int index = 0;
        for(String name : Ritual.getRitualNames()){
            ritualIndexOnTree.put(index, Ritual.getRitualNode(name));
            index ++;
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
        } else if(touchRitual(mouseX, mouseY)){
            return true;
        }
        else if (viewingRitual != null){
            if(chooseButton.contains(mouseX, mouseY)){
                System.out.println("PIKACHU I CHOOSE YOU");
            }
        }
        return false;
    }

    private boolean touchRitual(float mouseX, float mouseY){
        for(int k = 0; k < ritualPositionsOnTree.length; k++){
            if(ritualPositionsOnTree[k].contains(mouseX , mouseY)){
                viewingRitual = ritualIndexOnTree.get(k); // since each ritual is rendered with the same indexes as the Rectangle[],
                // they share the same indexes on the key
                return true;
            }
        }
        return false;
    }
    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y, width, height);
        continueButton.render(batch);

    }
}
