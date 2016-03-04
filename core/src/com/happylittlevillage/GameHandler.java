package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.happylittlevillage.gems.Gem;
import com.happylittlevillage.gems.GemBag;
import com.happylittlevillage.gems.GemBook;
import com.happylittlevillage.input.InputHandler;
import com.happylittlevillage.messages.*;
import com.happylittlevillage.rituals.Ritual;
import com.happylittlevillage.rituals.RitualAltar;
import com.happylittlevillage.rituals.RitualBook;
import com.happylittlevillage.village.Village;
import com.happylittlevillage.village.Villager;
import com.happylittlevillage.village.VillagerRole;

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
    private boolean gameOver = false;
    private boolean isTutorial;
    //maybe make an int stageTutorial to show the steps of Tutorial
    private boolean finishTutorial = false;
    private static ArrayList<Line> arrow = new ArrayList<Line>();
    private TutorialMessage tutorialMessage;

    public GameHandler(InputHandler inputHandler, boolean isTutorial) {
        this.isTutorial = isTutorial;
        this.inputHandler = inputHandler;
        init(isTutorial);

    }

    public void init( boolean isTutorial) {
        gemBag = new GemBag(1280 - 420 - 36 - 32, 30 + 35 - 12);
        if(isTutorial){
            tutorialMessage = new TutorialMessage("This is one of your villager",this, 0);
            village = new Village(gemBag, 200, 100, 5);
            arrow.add(new Line(476, 579, 0, 0));
        }
        else{
            village = new Village(gemBag, 500, 200, 10);
        }
        ritualAltar = new RitualAltar(gemBag, 1280 - 400 - 48 - 30, 720 - 400 - 40 - 12, village);
        messageBox = new Introduction(this, isTutorial);
        gameOverMessage = new GameOver(this);
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
    }

    public void unpause() {
        if (messageBox instanceof WeekSummary) {
            messageBox = new GemSummary(gemBag, village, this);
        } else if (messageBox instanceof GemSummary) {
            messageBox = new GodMessage(gemBag, village, this);
            if (((GodMessage) messageBox).checkRitual()) {
                //TODO change this messy code
                ritualAltar.removeRitual(village.getWeeklyRitual());
                village.generateNewWeeklyRitual();
                ritualAltar.gainRitual(village.getWeeklyRitual());
            }
            ((GodMessage) messageBox).stateRitual();
        } else {
            messageBox = new WeekSummary(village, this);
            paused = false;
        }
    }

    // game logic goes here
    public void update(float delta) {
        if (!win) {
            if (village.getSize() <= 0) {
                gameOverMessage.setCondition(0);
                gameOver = true;
            } else if (village.getFood() <= 0) {
                gameOverMessage.setCondition(1);
                gameOver = true;
            } else if (village.getHappiness() <= 0) {
                gameOverMessage.setCondition(2);
                gameOver = true;
            }
            if (gameOver) {
                messageBox = gameOverMessage;
                pause();
            }
        }
        if (!gameOver) {
            if (village.getSize() < -1) {
                winMessage.setCondition(1);
                win = true;
            }
        }

        if (!paused && !gameOver && !win) {
            if (village.isNextDay()) {
                pause();
                village.gatheredFood();
                village.gatheredHappiness();
            }
            village.update(delta);
            ritualAltar.update(delta);
        }
        if(isTutorial){
            if(!finishTutorial){
                arrow.get(0).x2 = village.getPositionOfARandomVillager().x;
                arrow.get(0).y2 = village.getPositionOfARandomVillager().y;
            }
        }

    }

    // rendering goes here
    public void render(Batch batch) {
        batch.draw(background, 0, 0);
        village.render(batch);
        batch.draw(scroll, 1280 - 550, -12);
        ritualAltar.render(batch);
        gemBag.render(batch);
        inputHandler.renderSelectedGem(batch);
        miniBook.render(batch);
        if (!gameOver && !win) {
            if (paused) {
                messageBox.render(batch);
            } else if (bookOpen) {
                ritualBook.render(batch);
            } else {
                if (DEBUG) {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    Villager.renderLines(shapeRenderer);
                    shapeRenderer.end();
                }
                if(isTutorial){
                    tutorialMessage.render(batch);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    arrow.get(0).render(shapeRenderer);
                    shapeRenderer.end();
                }

            }
        } else {
            messageBox.render(batch);
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

    public TutorialMessage getTutorialMessage(){return tutorialMessage; }

}
