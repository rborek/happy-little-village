package game.ritual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Line {
    public final float x1;
    public final float x2;
    public final float y1;
    public final float y2;

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

    public void render(ShapeRenderer renderer) {
        renderer.line(x1, y1, 0, x2, y2, 0, Color.RED, Color.RED);
    }

//    public boolean intersects(Line other)
//    {
//        float s1_x, s1_y, s2_x, s2_y;
//        s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
//        s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;
//
//        float s, t;
//        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
//        t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);
//
//        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
//        {
//            // Collision detected
//            if (i_x != NULL)
//            *i_x = p0_x + (t * s1_x);
//            if (i_y != NULL)
//            *i_y = p0_y + (t * s1_y);
//            return 1;
//        }
//
//        return 0; // No collision
//    }

    public boolean intersects(Line other) {
        Vector2 a = new Vector2(this.x1, this.y1);
        Vector2 b = new Vector2(this.x2, this.y2);
        Vector2 c = new Vector2(other.x1, other.y1);
        Vector2 d = new Vector2(other.x2, other.y2);

        Vector2 CmP = new Vector2(c.x - a.x, c.y - a.y);
        Vector2 r = new Vector2(b.x - a.x, b.y - a.y);
        Vector2 s = new Vector2(d.x - c.x, d.y - c.y);

        float CmPxr = CmP.x * r.y - CmP.y * r.x;
        float CmPxs = CmP.x * s.y - CmP.y * s.x;
        float rxs = r.x * s.y - r.y * s.x;

        if (CmPxr == 0f) {
            // Lines are collinear, and so intersect if they have any overlap
            return ((c.x - a.x < 0f) != (c.x - b.x < 0f)) || ((c.y - a.y < 0f) != (c.y - b.y < 0f));
        }

        if (rxs == 0f) return false; // Lines are parallel.

        float rxsr = 1f / rxs;
        float t = CmPxs * rxsr;
        float u = CmPxr * rxsr;

        return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
    }
}
