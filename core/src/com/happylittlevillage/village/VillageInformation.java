package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.objects.GameObject;
import com.happylittlevillage.menu.MenuItem;
import com.happylittlevillage.rituals.VillageModifier;

import java.util.ArrayList;

public class VillageInformation extends GameObject implements MenuItem {

    // add file to constructors
    private GameObject foodTexture = new GameObject(Assets.getTexture("ui/food.png"), position.x + 10, 105, 40, 40);
    private GameObject waterTexture = new GameObject(Assets.getTexture("ui/water.png"), position.x + 10, 65, 40, 40);
    private GameObject happyTexture = new GameObject(Assets.getTexture("ui/happy_icon.png"), position.x + 185, 70, 40, 40);
    private GameObject popTexture;
    private Village village;
    private BitmapFont font = new BitmapFont();
    private ArrayList<InformationFlash> addedResource = new ArrayList<InformationFlash>();
    public static ArrayList<TextureRegion> villagers = new ArrayList<TextureRegion>();
    private GameObject nextButton = new GameObject(Assets.getTexture("ui/next_button_villageinfo.png"), position.x + 450, position.y + 60);
    private Rectangle nextButtonPosition = new Rectangle(nextButton.getPosition().x, nextButton.getPosition().y, nextButton.getWidth(), nextButton.getHeight());

    protected VillageInformation(Village village, float xPos, float yPos) {
        super(Assets.getTexture("ui/info_menu.png"), xPos, yPos);
        this.village = village;
        villagers.add(Villager.getIdleVillagerTexture(VillagerRole.CITIZEN));
        villagers.add(Villager.getIdleVillagerTexture(VillagerRole.FARMER));
        villagers.add(Villager.getIdleVillagerTexture(VillagerRole.EXPLORER));
        villagers.add(Villager.getIdleVillagerTexture(VillagerRole.MINER));
    }

    public void getAddedResource(String resource, int amount) {
        if (resource.equals("food")) {
            addedResource.add(new InformationFlash(String.valueOf(amount), VillageModifier.FOOD));
        } else if (resource.equals("water")) {
            addedResource.add(new InformationFlash(String.valueOf(amount), VillageModifier.WATER));
        } else if (resource.equals("happiness")) {
            addedResource.add(new InformationFlash(String.valueOf(amount), VillageModifier.HAPPINESS));
        }
    }


    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y, 500, 150);
            foodTexture.render(batch);
            waterTexture.render(batch);
            happyTexture.render(batch);
            Assets.getFont(36).draw(batch, "" + village.getFood(), position.x + 60,  140);
            Assets.getFont(36).draw(batch, "" + village.getWater(), position.x + 60, 100);
            Assets.getFont(36).draw(batch, "" + village.getHappiness(), position.x + 230, position.y + 90);

            Assets.getFont(30).draw(batch, "Time: " + (int) Math.ceil(village.getHoursLeft()), position.x + 300, 50);
            Assets.getFont(30).draw(batch, "Days elapsed: " + (int) Math.ceil(village.getDay()), position.x + 30, 50);

            batch.draw(villagers.get(0), position.x + 355, 80);
            Assets.getFont(30).draw(batch, "" + village.getPop(), position.x + 355, 80);
            batch.draw(villagers.get(1), position.x + 390, 80);
            Assets.getFont(30).draw(batch, "" + village.getNumberOf(VillagerRole.FARMER), position.x + 390, 80);
            batch.draw(villagers.get(2), position.x + 425, 80);
            Assets.getFont(30).draw(batch, "" + village.getNumberOf(VillagerRole.EXPLORER), position.x + 425, 80);
            batch.draw(villagers.get(3), position.x + 460, 80);
            Assets.getFont(30).draw(batch, "" + village.getNumberOf(VillagerRole.MINER), position.x + 460, 80);
            moveAndFade(batch);

    }

    // TODO need to fix this method
    private void moveAndFade(Batch batch) {
        for (InformationFlash resource : addedResource) {
            if (resource != null) {
                resource.getCache().draw(batch);
            }
        }
    }

    @Override
    public boolean interact(float mouseX, float mouseY) {
        return false;
    }
}
