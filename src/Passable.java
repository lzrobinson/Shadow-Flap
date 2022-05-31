import bagel.Input;
import bagel.Keys;

/**
 * Interface which extends Collidable, represents all classes/objects which 'pass' through the game, ie. can be
 * passed by the bird: pipes & weapons.
 */
public interface Passable extends Collidable {
    /**
     * Note that I have set the initial speed of the game (pipe/weapon speed) to -5 not -3 as -3 was too slow for my game.
     */
    final int INIT_X_VELOCITY = -5;

}

