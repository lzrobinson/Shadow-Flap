import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

/**
 * Abstract class used to represent the weapons in the game.
 */
public abstract class Weapon implements Passable {
    protected final int THROW_VELOCITY = 5;
    protected final int X_SPAWN_POINT = Window.getWidth()/2;
    protected final int LEN = 32;
    protected final int OUT_OF_THE_WAY = -(Window.getHeight());
    protected boolean isCollected;
    protected boolean isThrown;
    protected boolean isBomb;
    protected boolean isHit;
    protected double xPos;
    protected double yPos;
    protected double xVelocity;
    protected int frameCount = 0;
    protected int throwDistance = 0;
    protected Rectangle rectangle;
    protected Point point;
    protected Random rand = new Random();
    protected Rectangle boundingBox;

    /**
     * Standard constructor.
     */
    public Weapon() {
        isCollected = false;
        isThrown = false;
        isHit = false;
        xPos = Window.getWidth();
        yPos = rand.nextInt(Window.getHeight());
        xVelocity = INIT_X_VELOCITY;
        point = new Point(xPos, yPos);

    }

    /**
     * Again, updates the Weapon using standard player input.
     * @param input This is the player input.
     */
    public void update(Input input) {

        if (!isCollected) {
            point = new Point(xPos-(LEN/2), yPos-(LEN/2));

            if (xPos < 0) {
                waitSpawn();
            }
        }
        else if (isCollected) {
            // moveToBirdLoc(birdX, birdY);
            if (input.wasPressed(Keys.S)) {
                throwWeapon();
            }
        }
        if (isThrown) {
            throwDistance++;
            if (isHit) {
                waitSpawn();
            }
        }

        if (yPos == OUT_OF_THE_WAY) {
            waitSpawn();
        }

        xPos += xVelocity;
        frameCount++;
        if (frameCount == 50) {
            frameCount = 0;
        }

    }

    /**
     * Spawns the weapon.
     */
    public void spawn() {
        yPos = rand.nextInt(Window.getHeight());
        xPos = Window.getWidth();
        isCollected = false;
        isThrown = false;
        isHit = false;

    }

    /**
     * Moves the weapon outside the course of the game window whilst the weapon waits to respawn.
     */
    public void waitSpawn() {
        yPos = OUT_OF_THE_WAY;
        point = new Point(xPos, yPos);
        rectangle = getBox();

        if (frameCount == 0) {
            spawn();
        }
    }


    /**
     * Method which represents the action of throwing the weapon.
     */
    public void throwWeapon() {
        xVelocity = THROW_VELOCITY;
        isThrown = true;
    }

    /**
     * Abstract method which is used to get the bounding box of the weapon.
     * @return Rectangle This is the bounding box.
     */
    public abstract Rectangle getBox();

}
