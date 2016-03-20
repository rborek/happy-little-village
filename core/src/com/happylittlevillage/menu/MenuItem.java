package com.happylittlevillage.menu;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface MenuItem { // VillageInformation implements this so be careful before any change
    public boolean interact(float mouseX, float mouseY);
}
