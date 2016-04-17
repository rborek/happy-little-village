package com.happylittlevillage.util;

import com.badlogic.gdx.math.Vector2;

public class Edge {
	private Line path = new Line();
	private double distance;

	public Edge(Vector2 position, Vector2 destination) {
		path.set(position, destination);
		distance = Math.sqrt(Math.pow(position.x - destination.x, 2) + Math.pow(position.y - destination.y, 2));
	}

	public double getDistance() {
		return distance;
	}

	public Line getPath() {
		return path;
	}

}
