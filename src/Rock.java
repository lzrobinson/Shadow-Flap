import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Rock extends Weapon {
    private final Image rockImage = new Image("res/level-1/rock.png");
    private final int RANGE = 25;

    public Rock() {
        super();
    }

    @Override
    public void update(Input input) {
        super.update(input);
        if (!isCollected) {
            rockImage.draw(xPos, yPos);

        }
        else if (isThrown) {
            if (throwDistance == RANGE || isHit) {
                waitSpawn();
            }
            else {
                rockImage.draw(xPos, yPos);
            }
        }
        else {
            rockImage.draw(xPos, yPos);
        }
        boundingBox = rockImage.getBoundingBoxAt(new Point(xPos, yPos));
    }

    public Rectangle getBox() {
        return boundingBox;
    }

}
