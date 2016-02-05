package game.ritual.village;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import game.ritual.Assets;
import game.ritual.GameObject;
import game.ritual.Line;

import java.util.ArrayList;
import java.util.Random;

public class Villager extends GameObject {
    private VillagerRole role;
    private final static Texture[][] villagerTextures = {Assets.getTextures("villagers/citizen/citizen.png", "villagers/citizen/citizen_left_1.png",
            "villagers/citizen/citizen_left_2.png", "villagers/citizen/citizen_left_3.png", "villagers/citizen/citizen_left_2.png"),
            Assets.getTextures("villagers/miner/miner.png", "villagers/miner/miner_left_1.png",
                    "villagers/miner/miner_left_2.png", "villagers/miner/miner_left_3.png", "villagers/miner/miner_left_2.png"),
            Assets.getTextures("villagers/farmer/farmer.png", "villagers/farmer/farmer_left_1.png",
                    "villagers/farmer/farmer_left_2.png", "villagers/farmer/farmer_left_3.png", "villagers/farmer/farmer_left_2.png"),
            Assets.getTextures("villagers/explorer/explorer.png", "villagers/explorer/explorer_left_1.png",
                    "villagers/explorer/explorer_left_2.png", "villagers/explorer/explorer_left_3.png", "villagers/explorer/explorer_left_2.png")};
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
    private Village village;
    private Vector2 destination;
    private float speed = 120; // magnitude of the villager
    private Vector2 velocity; // velocity of the villager
    private float restTimer = 2;
    private boolean resting = false;
    private float walkTimer = 0;

    // subtract delta from restTimer
    public Villager(VillagerRole role, Village village) {
        super(villagerTextures[role.ordinal()][0], 0, 0);
        this.village = village;
        position = new Vector2((float) Math.random() * 500, 195);
        this.role = role;
        velocity = new Vector2(0, 0);
        generateNewDestination();
    }

    public static void renderLines(ShapeRenderer renderer) {
        for (Line line : obstacles) {
            line.render(renderer);
        }
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
        int frame = (int) (walkTimer * 5 % 4) + 1;
        texture = villagerTextures[role.ordinal()][frame];
        walkTimer += delta;
        position.add(velocity.x * delta, velocity.y * delta);
    }

    private boolean arrivedAtDestination() {
        return Math.abs(position.x - destination.x) < 10 && Math.abs(position.y - destination.y) < 10;
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, position.x, position.y, width, height, 0, 0, (int) width, (int) height, isMovingRight(),
                false);
    }

    @Override
    public void update(float delta) {
        if (resting) {
            texture = villagerTextures[role.ordinal()][0];
            restTimer -= delta;
            if (restTimer <= 0) {
                generateNewDestination();
                Random r = new Random();
                restTimer = r.nextFloat() * 3 + 1;
                resting = false;
            }
        } else {
            move(delta);
            if (arrivedAtDestination()) {
                resting = true;
            }
        }
    }

    public void generateNewDestination() {
        destination = village.getEmptyPosition();
        Line path = new Line(position, destination);
        for (int i = 0; i < obstacles.size(); i++) {
            if (path.intersects(obstacles.get(i))) {
//                System.out.println("fk");
                destination = village.getEmptyPosition();
                path =  new Line(position, destination);
                i = -1;
            }
        }
        calculateVelocity();
    }

    public Vector2 getDestination() {
        return destination;
    }

    public VillagerRole getRole() {
        return role;
    }

    public void setRole(VillagerRole a) {
        role = a;
    }
}
