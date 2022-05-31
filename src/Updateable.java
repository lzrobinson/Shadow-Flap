import bagel.Input;

/**
 * Interface representing all branching classes/attributes of ShadowFlap which require updating at the same rate as
 * AbstractGame.
 */
public interface Updateable {
    /**
     * Update method similar to that of AbstractGame, specific to each individual sublass.
     * @param input This is the standard keyboard input of the player.
     */
    void update(Input input);
}
