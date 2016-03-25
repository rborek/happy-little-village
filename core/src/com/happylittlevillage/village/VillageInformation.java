package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.menu.MenuItem;
import com.happylittlevillage.rituals.VillageModifier;

import java.util.ArrayList;

public class VillageInformation extends GameObject implements MenuItem {

    // add file to constructors
    private GameObject foodTexture = new GameObject(Assets.getTexture("ui/food.png"), position.x + 20, 65);
    private GameObject waterTexture = new GameObject(Assets.getTexture("ui/water.png"), position.x + 150, 70);
    private GameObject popTexture;
    private Village village;
    private BitmapFont font = new BitmapFont();
    private ArrayList<InformationFlash> addedResource = new ArrayList<InformationFlash>();
    private ArrayList<TextureRegion> villagers = new ArrayList<TextureRegion>();
    private GameObject nextButton = new GameObject(Assets.getTexture("ui/next_button_villageinfo.png"), position.x + 450, position.y + 60);
    private Rectangle nextButtonPosition = new Rectangle(nextButton.getPosition().x, nextButton.getPosition().y, nextButton.getWidth(), nextButton.getHeight());
    private int page = 0;

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
        for (int index = 0; index < addedResource.size(); index++) {
            addedResource.get(index).updateMotion(delta, true);
            if (addedResource.get(index).getAlpha() <= 0.07) {
                addedResource.remove(addedResource.get(index));
                index--;
            }
        }
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y, 500, 150);
        nextButton.render(batch);
        if (page == 0) {
            // page 1
            foodTexture.render(batch);
            waterTexture.render(batch);
            Assets.getFont(36).draw(batch, "" + village.getFood(), position.x + 70, position.y + 90);
            Assets.getFont (36).draw(batch, "" + village.getWater(), position.x + 220, position.y + 90);
            Assets.getFont(36).draw(batch, "" + village.getHappiness(), position.x + 300, position.y + 90);
            moveAndFade(batch);
        } else {
            // page 2
            batch.draw(villagers.get(0), position.x + 50, 50);
            Assets.getFont(30).draw(batch, "" + village.getPop(), position.x + 50, 40);
            batch.draw(villagers.get(1), position.x + 100, 50);
            Assets.getFont(30).draw(batch, "" + village.getNumberOf(VillagerRole.FARMER), position.x + 100, 40);
            batch.draw(villagers.get(2), position.x + 150, 50);
            Assets.getFont(30).draw(batch, "" + village.getNumberOf(VillagerRole.EXPLORER), position.x + 150, 40);
            batch.draw(villagers.get(3), position.x + 200, 50);
            Assets.getFont(30).draw(batch, "" + village.getNumberOf(VillagerRole.MINER), position.x + 200, 40);
            Assets.getFont(30).draw(batch, "Hours: " + (int) Math.ceil(village.getHoursLeft()), position.x + 230, 80);
            Assets.getFont(30).draw(batch, "Days elapsed: " + (int) Math.ceil(village.getDay()), position.x + 350, 120);
            Assets.getFont(30).draw(batch, "Days left: " + village.getDaysLeft(), position.x + 350, 80);
        }
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
        if (nextButtonPosition.contains(mouseX, mouseY)) {
            page = page == 0 ? 1 : 0; // change the page
            return true;
        } else {
            return false;

        }
    }
}
