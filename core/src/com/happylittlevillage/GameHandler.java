package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemBook;
import com.happylittlevillage.input.InputHandler;
import com.happylittlevillage.menu.SaveGame;
import com.happylittlevillage.messages.*;
import com.happylittlevillage.rituals.Ritual;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.rituals.RitualBook;
import com.happylittlevillage.rituals.RitualTree;
import com.happylittlevillage.village.Village;
import com.happylittlevillage.village.Villager;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameHandler {
    private Village village;
    private RitualAltar ritualAltar;
    private Texture background = Assets.getTexture("bg/background.png");
    private GemBag gemBag;
    private InputHandler inputHandler;
    private Gem gem;
    private Texture scroll = Assets.getTexture("ui/scroll.png");
    private boolean paused;
    private GemBook miniBook = new GemBook(this);
    private boolean bookOpen;
    private RitualBook ritualBook = new RitualBook(70, 160);
    private RitualTree ritualTree = new RitualTree(70, 120);
    private WinMessage winMessage;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean DEBUG = false;
    // all menu items is put here
    private MessageBox messageBox;
    private GemSummary gemSummary;
    private GodMessage godMessage;
    private GameOver gameOverMessage;
    private boolean win = false;
    private boolean intro = true;
    private boolean lose = false;
    private boolean isTutorial;
    private boolean finishTutorial = false;
    private static ArrayList<Vector2> arrow = new ArrayList<Vector2>();
    private TutorialMessage tutorialMessage;
    private Rectangle optionWheelPosition = new Rectangle(0, 600, 64, 64);
    private GameObject optionWheel = new GameObject(Assets.getTexture("menu/optionWheel.png"), 0, 600, 64, 64);
    private Json saveInfo = new Json();
    private SaveGame saveGame;


    public GameHandler(InputHandler inputHandler, boolean isTutorial, HappyLittleVillage happyLittleVillage) {
        this.isTutorial = isTutorial;
        this.inputHandler = inputHandler;
        init(isTutorial, happyLittleVillage);
    }

    public void init(boolean isTutorial, HappyLittleVillage happyLittleVillage) {
        gemBag = new GemBag(1280 - 420 - 36 - 32, 30 + 35 - 12);
        if (isTutorial) {
            village = new Village(gemBag, 200, 100, 5);
            ritualAltar = new RitualAltar(gemBag, 1280 - 400 - 48 - 30, 720 - 400 - 40 - 12, village, ritualBook);
            tutorialMessage = new TutorialMessage(this, ritualAltar, miniBook);
            arrow.add(new Vector2(476, 579));
        } else {
            village = new Village(gemBag, 1000, 1000, 5);
            ritualAltar = new RitualAltar(gemBag, 1280 - 400 - 48 - 30, 720 - 400 - 40 - 12, village, ritualBook);
        }

        messageBox = new Introduction(this, isTutorial);
        gameOverMessage = new GameOver(this, happyLittleVillage);
        winMessage = new WinMessage(this);
        Ritual.setVillage(village);
        ritualAltar.gainRitual(village.getWeeklyRitual());
        Gdx.input.setInputProcessor(inputHandler);
        pause();
    }


    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
        if (miniBook.isOpen()) {
            miniBook.close();
        }
        if (village.getDaysLeft() < 0) {
            lose = true;
        }
        if (lose) {
            messageBox = gameOverMessage;

        }
    }

    public void unpause() {
        if (messageBox instanceof WeekSummary) {
            messageBox = new GemSummary(gemBag, village, this);
        } else if (messageBox instanceof GemSummary) {
            messageBox = new GodMessage(gemBag, village, this);
            if (((GodMessage) messageBox).checkRitual()) {
                //TODO change this messy code
                village.generateNewWeeklyRitual();
            }
            ((GodMessage) messageBox).stateRitual();
        } else {
            messageBox = new WeekSummary(village, this);
            paused = false;
        }

    }

    // game logic goes here
    public void update(float delta) {
        // lose
        if (village.getSize() <= 0) {
            lose = true;
            gameOverMessage.setCondition(0);
            pause();
        }
        //win
        else if (village.getSize() >= 50) {
            win = true;
            winMessage.setCondition(1);
            pause();
        }

        if (!paused) { // not pause
            if (village.isNextDay()) {
                pause();
            }
            village.update(delta);
            ritualAltar.update(delta);
        }
        if (isTutorial) {
            //arrow for screen 0 and 1
            if (tutorialMessage.getTutorialScreen() <= 1) {
                // make the arrow point to a villager
                tutorialMessage.setAngle(village.getPositionOfARandomVillager(), arrow.get(0));
            }
            tutorialMessage.update(delta);
        }

    }

    public void render(Batch batch) {
        batch.draw(background, 0, 0);
        village.render(batch);
        batch.draw(scroll, 1280 - 550, -12);
        ritualAltar.render(batch);
        gemBag.render(batch);
        miniBook.render(batch);
//		ritualTree.render(batch);
//      optionWheel.render(batch);
        if (!paused) {
            if (isTutorial) {
                tutorialMessage.render(batch);
            }
            else if (bookOpen) {
                ritualBook.render(batch);
            } else {
                if (DEBUG) {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    Villager.renderLines(shapeRenderer);
                    shapeRenderer.end();
                }
            }
            inputHandler.renderSelectedGem(batch);
            inputHandler.renderSelectedRitual(batch);
        } else {
            messageBox.render(batch);
        }

    }

    public void saveGame() {
        saveGame = new SaveGame(village.getFood(), village.getWater(), village.getHappiness(), village.getWeeklyRitual(),
                village.getHoursLeft(), village.getDaysLeft(), village.getDay(),
                village.isNextDay(), village.getVillagerSpawnTimer(), village.getVillagersToSpawn().size(),
                village.getGemThreshold(), village.getHunger(), village.getDehydration(),
                village.getVillagers(), gemBag, ritualBook.getUnlockedRitual());
        saveInfo.toJson(saveGame, SaveGame.class);
        try {
            FileWriter fileWriter =
                    new FileWriter("data/data.save/saveInfo.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String text = saveInfo.prettyPrint(saveGame);
            System.out.print("Info is: " + saveGame.toString());
            bufferedWriter.write(text);
            bufferedWriter.flush();
            bufferedWriter.close();
            FileReader fileReader = new FileReader("data/data.save/saveInfo.txt");
            SaveGame saveGame1 = saveInfo.fromJson(SaveGame.class, fileReader);
        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file");
        }
    }

    public Village getVillage() {
        return village;
    }

    public void openBook() {
        bookOpen = true;
    }

    public void closeBook() {
        bookOpen = false;
    }

    public RitualAltar getRitualAltar() {
        return ritualAltar;
    }

    public GemBag getGemBag() {
        return gemBag;
    }

    public RitualBook getRitualBook() {
        return ritualBook;
    }

    public GemBook getMiniBook() {
        return miniBook;
    }

    public MessageBox getMessageBox() {
        return messageBox;
    }

    public TutorialMessage getTutorialMessage() {
        return tutorialMessage;
    }

    public boolean isTutorial() {
        return isTutorial;
    }

    public GameOver getGameOverMessage() {
        return gameOverMessage;
    }

    public Rectangle getOptionWheelPosition() {
        return optionWheelPosition;
    }

}
