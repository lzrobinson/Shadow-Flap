import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class representing the Bomb weapon.
 */
public class Bomb extends Weapon {
    private final Image bombImage = new Image("res/level-1/bomb.png");
    private final int RANGE = 50;

    /**
     * Standard constructor
     */
    public Bomb() {
        super();
    }

    /**
     * Again, this updates the state of the Bomb.
     * @param input This is the player input.
     */
    @Override
    public void update(Input input) {
        super.update(input);
        if (!isCollected) {
            bombImage.draw(xPos, yPos);

        }
        else if (isThrown) {
            if (throwDistance == RANGE || isHit) {
                waitSpawn();
            }
            else {
                bombImage.draw(xPos, yPos);
            }
        }
        else {
            bombImage.draw(xPos, yPos);
        }
        boundingBox = bombImage.getBoundingBoxAt(new Point(xPos, yPos));

    }

    /**
     * getBox method
     * @return Rectangle
     */
    public Rectangle getBox() {
        return boundingBox;
    }
}
