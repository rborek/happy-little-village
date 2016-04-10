package com.happylittlevillage.village;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.happylittlevillage.Assets;
import com.happylittlevillage.GameObject;
import com.happylittlevillage.Line;
import com.happylittlevillage.rituals.Edge;

import java.util.*;

public class Villager extends GameObject implements Comparable<Villager> {
    private VillagerRole role;
    private TextureRegion region;
    private final static TextureAtlas villagerAtlas = Assets.getAtlas("villagers");
    private final static TextureRegion[][] idleTextures = {villagerAtlas.findRegions("citizen").toArray(TextureRegion.class),
            villagerAtlas.findRegions("miner").toArray(TextureRegion.class), villagerAtlas.findRegions("farmer").toArray(TextureRegion.class),
            villagerAtlas.findRegions("explorer").toArray(TextureRegion.class)};
    private final static TextureRegion[][] walkTextures = {villagerAtlas.findRegions("citizen_walk").toArray(TextureRegion.class),
            villagerAtlas.findRegions("miner_walk").toArray(TextureRegion.class), villagerAtlas.findRegions("farmer_walk").toArray(TextureRegion.class),
            villagerAtlas.findRegions("explorer_walk").toArray(TextureRegion.class)};
    private static Pool<Line> linePool = new Pool<Line>() {
        @Override
        protected Line newObject() {
            return new Line();
        }
    };

    private static Vector2[] pathMarkers = { // destination that villagers can walk in
            new Vector2(200, 304), // 0
            new Vector2(400, 410),
            new Vector2(279, 458),
            new Vector2(230, 440),
            new Vector2(585, 409),
            new Vector2(602, 372), // 5
            new Vector2(401, 259),
            new Vector2(193, 254),
            new Vector2(123, 472),
            new Vector2(41, 387),
            new Vector2(44, 295), // 10
            new Vector2(620, 256),
            new Vector2(670, 518),
            new Vector2(185, 545),
            new Vector2(386, 544),

    };
    private static ArrayList<Line> obstacles = new ArrayList<Line>() {
        {
            // house
            add(new Line(55, 285, 22, 379));
            add(new Line(22, 379, 62, 447));
            add(new Line(62, 447, 124, 483));
            add(new Line(124, 483, 248, 429));
            add(new Line(248, 429, 257, 338));
            add(new Line(257, 338, 164, 278));
            add(new Line(164, 278, 55, 285));
            // fences
            add(new Line(370, 270, 387, 334));
            add(new Line(387, 334, 441, 370));
            add(new Line(441, 370, 602, 370));
            add(new Line(602, 370, 625, 270));
            add(new Line(625, 270, 370, 270));
            // mine
            add(new Line(299, 423, 584, 419));
            add(new Line(584, 419, 596, 460));
            add(new Line(596, 460, 472, 527));
            add(new Line(472, 527, 327, 530));
            add(new Line(327, 530, 299, 480));
            add(new Line(299, 480, 299, 423));
        }
    };
    private static ArrayList<Edge> edges = new ArrayList<Edge>();
    private Village village;
    private Vector2 destination;
    private float speed = 120; // magnitude of the villager
    private Vector2 velocity; // velocity of the villager
    private float timer = getNewRestDuration();
    private boolean resting = false;
    private float walkTimer = 0;
    private boolean goToMine = true;
    private static final int MINE_INDEX = 1;
    private static Vector2[] connected = { // set the connected pair. The first coord is the destination, the second coord is the position
            new Vector2(0, 6), new Vector2(0, 7),
            new Vector2(1, 0), new Vector2(1, 2), new Vector2(1, 3), new Vector2(1, 4), new Vector2(1, 5), new Vector2(1, 6), new Vector2(1, 7),
            new Vector2(2, 14), new Vector2(3, 13),
            new Vector2(3, 8),
            new Vector2(4, 12),
            new Vector2(5, 11),
            new Vector2(7, 10),
            new Vector2(8, 9),
    };

    private static void createEdges() {
        for (Vector2 link : connected) {
            edges.add(new Edge(pathMarkers[(int) link.x], pathMarkers[(int) link.y])); // add the edges of the connected positions
        }
    }

    private Queue<Integer> pathToTake = new ArrayDeque<Integer>(); // this is the path that a converted villager will take

    public Villager(VillagerRole role, Village village) {
        super(null, 0, 0, 28, 42);
        this.village = village;
        position = new Vector2(216, 300);
        this.role = role;
        velocity = new Vector2(0, 0);
        region = idleTextures[role.ordinal()][0];
        generateNewDestination();
        createEdges();
    }

    public static void renderLines(ShapeRenderer renderer) {
        for (Line line : obstacles) {
            line.render(renderer);
        }
    }

    public static void renderPath(ShapeRenderer renderer) {
        for (Edge edge : edges) {
            edge.getNodes().render(renderer);
        }
    }

    public static void renderPathMakers(Batch batch) {
        BitmapFont font = Assets.getFont(16);
        for (int k = 0; k < pathMarkers.length; k++) {
            batch.draw(Assets.getTexture("ui/path_marker.png"), pathMarkers[k].x, pathMarkers[k].y);
            font.draw(batch, "" + k, pathMarkers[k].x, pathMarkers[k].y);
        }
    }

    public static TextureRegion getIdleVillagerTexture(VillagerRole role) {
        return idleTextures[role.ordinal()][0];
    }

    public boolean isMovingRight() {
        return position.x < destination.x;
    }

    private void calculateVelocity() {
        float x = destination.x - position.x;
        float y = destination.y - position.y;
        float ratio = Math.abs(x / y);
        // next 2 lines set the velocity so that the magnitude stay constant;
        velocity.y = (float) Math.sqrt(speed * speed / (1 + ratio) / (1 + ratio));
        velocity.x = velocity.y * ratio;
        int xDir = (int) Math.signum(x);
        int yDir = (int) Math.signum(y);
        velocity.x *= xDir;
        velocity.y *= yDir;
    }

    private void move(float delta) {
        int frame = (int) (walkTimer * 5 % 4);
        if (frame == 3) {
            region = walkTextures[role.ordinal()][frame - 2];
        } else {
            region = walkTextures[role.ordinal()][frame];
        }
        walkTimer += delta;
        if (walkTimer >= 12) {
            walkTimer = 0;
        }
        position.add(velocity.x * delta, velocity.y * delta);
    }

    private boolean arrivedAtDestination() {
        return (Math.abs(position.x - destination.x) < 10 && Math.abs(position.y - destination.y) < 10);
    }

    @Override
    public void render(Batch batch) {
        if (role == VillagerRole.CITIZEN) {
            batch.draw(region, isMovingRight() ? position.x + width : position.x, position.y, 0, 0, width, height, isMovingRight() ? -1 : 1, 1, 0);
        } else if (role == VillagerRole.MINER) {
            if (!resting) {
                batch.draw(region, isMovingRight() ? position.x + width : position.x, position.y, 0, 0, width, height, isMovingRight() ? -1 : 1, 1, 0);
            }
        }
    }

    @Override
    public void update(float delta) {
        if (this.role == VillagerRole.CITIZEN) {
            if (resting) {
                region = idleTextures[role.ordinal()][0];
                timer -= delta;
                if (timer <= 0) {
                    generateNewDestination();
                    timer = getNewRestDuration();
                    resting = false;
                }
            } else {
                move(delta);
                if (arrivedAtDestination()) {
                    resting = true;
                }
            }
        } else if (this.role == VillagerRole.MINER) {
            if (goToMine) { // if the miner is travelling to the mine. False if the miner is at the mine and pathToTake is empty
                if (!pathToTake.isEmpty()) {
                    destination = pathMarkers[pathToTake.peek()]; // the distance is the next index in path to take
                    calculateVelocity();
                    move(delta);
                    if (arrivedAtDestination()) {
                        pathToTake.remove();
                    }
                } else { // the miner is going to his final destination
                    if (arrivedAtDestination()) {
                        goToMine = false;
                    }
                }
            } else { // routine
                if (destination == pathMarkers[MINE_INDEX]) {
                    if (arriveAt(MINE_INDEX)) { // if the miner is at the mine
                        if (resting) { // the miner will disappear
                            timer -= delta;
                            if (timer <= -1) { // end disappearing, reappearing and go back to the house
                                destination = pathMarkers[0];
                                calculateVelocity();
                                resting = false;
                            }
                        } else { // the standard of a new miner is not resting, or not disappearing into the mine
                            timer = getNewRestDuration();
                            resting = true;
                        }
                    } else {
                        move(delta);
                    }

                } else if (destination == pathMarkers[0]) {
                    if (arriveAt(0)) {
                        System.out.println("yes");
                        destination = pathMarkers[MINE_INDEX];
                        calculateVelocity();
                    }
                    move(delta);
                }
            }

        }
    }

    private boolean arriveAt(int index) {
        return (Math.abs(position.x - pathMarkers[index].x) < 10 && Math.abs(position.y - pathMarkers[index].y) < 10);
    }

    private float getNewRestDuration() {
        Random r = new Random();
        return r.nextFloat() * 4;
    }

    private void generateNewDestination() {
        destination = village.getEmptyPosition();
        Line path = linePool.obtain();
        path.set(position, destination);
        for (int i = 0; i < obstacles.size(); i++) { //check if it intersects with any bounding lines
            if (path.intersects(obstacles.get(i))) {
                destination = village.getEmptyPosition();
                path.set(position, destination);
                i = -1;
            }
        }
        linePool.free(path);
        calculateVelocity();
    }

    private void goToMineDestination() {
        int nextIndex = 0;
        double nextDestination = 1000; //arbitrary value
        double temp;
        // find the closest node to walk to
        for (int k = 0; k < pathMarkers.length; k++) {
            temp = Math.sqrt(Math.pow(position.x - pathMarkers[k].x, 2) + Math.pow(position.y - pathMarkers[k].y, 2));
            if (temp < nextDestination) {
                nextDestination = temp;
                nextIndex = k; // we only care about nextIndex, which is the closet node to walk to
            }
        }
        pathToTake.add(nextIndex);

        findPath(nextIndex, MINE_INDEX);//find the smallest path; MINE_INDEX here is the destination of the mine. Can change
    }

    private void findPath(int startIndex, int destinationIndex) { // this follows Dijkstra's algorithm
        dijkstra(startIndex, destinationIndex);
    }

    private int minDistance(int[] dist, boolean[] sptSet) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < pathMarkers.length; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    private void dijkstra(int src, int end) {
        int[] dist = new int[pathMarkers.length]; // distances from src to the rest
        boolean[] sptSet = new boolean[pathMarkers.length]; // true for processed vertexes, false otherwise
        for (int i = 0; i < pathMarkers.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
        dist[src] = 0; // distance from src to itself is 0
        //find shortest path for all vertices
        for (int count = 0; count < pathMarkers.length - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < pathMarkers.length; v++)
                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && findConnected(v, u) != 0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u] + findConnected(v, u) < dist[v])
                    dist[v] = dist[u] + (int) findConnected(v, u);
        }

        //sort the array
        int[] shortestIndex = new int[pathMarkers.length]; // store the order of shortest indexes
        int index = 0; // count for shortest index

        int[] dist2 = new int[pathMarkers.length]; // dist2 hold dist
        System.arraycopy(dist, 0, dist2, 0, pathMarkers.length); // copy to new array
        Arrays.sort(dist); // now dist is sorted

        for (int count = 0; count < pathMarkers.length; count++) { // loop through sorted dist list
            for (int count2 = 0; count2 < pathMarkers.length; count2++) { // for each element in sorted dist list, compare and store the smallest dist order into shortestIndex
                if (dist[count] == dist2[count2]) {
                    shortestIndex[index] = count2;
                    index++;
                    break;
                }
            }
        }
        updatePathToTake(shortestIndex, end);
    }

    private void updatePathToTake(int[] shortestIndex, int end) { // add elements to pathToTake
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(end);
        boolean findEndPoint = true;
        int nextAdjacentPoint = end;
        for (int count = pathMarkers.length - 1; count >= 0; count--) {
            if (findEndPoint) {
                if (shortestIndex[count] == end) { // found endPoint
                    findEndPoint = false;
                }
            } else {
                if (findConnected(shortestIndex[count], nextAdjacentPoint) != 0) { // add the correct order to path
                    path.add(shortestIndex[count]);
                    nextAdjacentPoint = shortestIndex[count];
                }
            }
        }
        System.out.println("size of path " + path.size());
        // now path contains the right node to walk, but from destination to position. So we have to loop back and add it to pathToTake. We exclude k = 0 as it is the position added above
        for (int k = path.size() - 1; k >= 0; k--) {
            pathToTake.add(path.get(k));
            System.out.println(" -> " + path.get(k));
        }
    }


    private double findConnected(int v, int u) {
        for (int k = 0; k < connected.length; k++) { // iterate through connected graph
            if ((connected[k].x == v && connected[k].y == u)
                    || (connected[k].x == u && connected[k].y == v)) { // if there is a link between u and v
                return edges.get(k).getDistance();
            }
        }
        return 0;
    }


    public Vector2 getDestination() {
        return destination;
    }

    public VillagerRole getRole() {
        return role;
    }

    public void setRole(VillagerRole a) {
        role = a;
        if (role == VillagerRole.MINER) {
            goToMineDestination();
        }
    }

    @Override
    public int compareTo(Villager other) {
        if (this.position.y > other.position.y) {
            return -1;
        } else if (this.position.y == other.position.y) {
            return 0;
        } else {
            return 1;
        }
    }

}
