package com.happylittlevillage.rituals;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.happylittlevillage.Line;

public class Edge {
    private Line nodes = new Line();
    private double distance;

    public Edge(Vector2 position, Vector2 destination) {
        nodes.set(position, destination);
        distance = Math.sqrt(Math.pow(position.x - destination.x, 2) + Math.pow(position.y - destination.y, 2));
    }

    public double getDistance() {
        return distance;
    }

    public Line getNodes(){
        return nodes;
    }

}
