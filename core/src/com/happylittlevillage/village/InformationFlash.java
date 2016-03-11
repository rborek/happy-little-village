package com.happylittlevillage.village;

import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.rituals.VillageModifier;

public class InformationFlash {
    private String info;
    private float alpha = 1;
    private Vector2 start;
    private Vector2 end;
    private Vector2 moveTo;
    private VillageModifier relativePosition; //only for food, water and happiness( 0, 1 ,2)

    public InformationFlash(String info, VillageModifier relativePosition) {
        this.info = info;
        this.relativePosition = relativePosition;
        start = new Vector2(0, 200);
        end = new Vector2(0, 400);
        moveTo = new Vector2(0, 150);
    }

    public void setPositionX(int startX, int endX) {
        this.start.x = startX;
        this.end.x = endX;
    }

    public void setPositionY(int startY, int endY) {
        this.start.y = startY;
        this.end.y = endY;
    }

    public void updateMotion(float delta, boolean vertical) {
        if (vertical) {
            float moveY = (end.y - start.y) * delta / 4;
            moveTo.y += moveY;
        } else {
            float moveX = (end.x - start.x) * delta / 4;
            moveTo.x += moveX;
        }
        alpha -= delta / 1;
        if (alpha < 0) {
            alpha = 1;
        }
    }

    public float getAlpha() {
        return alpha;
    }

    public float getMoveToX() {
        return moveTo.x;
    }

    public float getMoveToY() {
        return moveTo.y;
    }

    public int getRelativePosition() {
        if (relativePosition.equals(VillageModifier.FOOD)) {
            return 0;
        } else if (relativePosition.equals(VillageModifier.WATER)) {
            return 1;
        } else if (relativePosition.equals(VillageModifier.HAPPINESS)) {
            return 2;
        } else {
            return -1;
        }
    }

    public String getInfo() {
        return info;
    }
}

