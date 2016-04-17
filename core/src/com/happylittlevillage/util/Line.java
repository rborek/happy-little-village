package com.happylittlevillage.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Line {
	public float x1;
	public float x2;
	public float y1;
	public float y2;

	public Line() {
	}

	public Line(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public Line(Vector2 start, Vector2 end) {
		x1 = start.x;
		x2 = end.x;
		y1 = start.y;
		y2 = end.y;
	}

	public void set(Vector2 start, Vector2 end) {
		x1 = start.x;
		x2 = end.x;
		y1 = start.y;
		y2 = end.y;
	}

	public void render(ShapeRenderer renderer) {
		renderer.line(x1, y1, 0, x2, y2, 0, Color.RED, Color.RED);
	}

	public boolean intersects(Line other) {
		float cmpX = other.x1 - this.x1;
		float cmpY = other.y1 - this.y1;
		float rX = this.x2 - this.x1;
		float rY = this.y2 - this.y1;
		float sX = other.x2 - other.x1;
		float sY = other.y2 - other.y1;

		float CmPxr = cmpX * rY - cmpY * rX;
		float CmPxs = cmpX * sY - cmpY * sX;
		float rxs = rX * sY - rY * sX;

		if (CmPxr == 0f) {
			// Lines are collinear, and so intersect if they have any overlap
			return ((other.x1 - this.x1 < 0f) != (other.x1 - this.x2 < 0f)) || ((other.y1 - this.y1 < 0f) != (other.y1 - this.y2 < 0f));
		}

		if (rxs == 0f) return false; // Lines are parallel.

		float rxsr = 1f / rxs;
		float t = CmPxs * rxsr;
		float u = CmPxr * rxsr;

		return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
	}
}
