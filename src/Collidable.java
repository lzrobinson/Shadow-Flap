import bagel.util.Point;
import bagel.util.Rectangle;


/** An interface which extends Updateable; encompasses all classes used in the game which have
 * an collidable aspect; e.g. pipes, weapons, birds.
 */
public interface Collidable extends Updateable {

    /** Function with the intention of being used to get a rectangle surrounding a collidable object.
     * @return Rectangle
     */
    public Rectangle getBox();

    /** Function with the intention of being used to spawn a collidable object onto the game window.
     */
    public void spawn();
}
