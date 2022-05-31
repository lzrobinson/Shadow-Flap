import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents the plastic pipes in the game.
 */
public class PlasticPipe extends PipeSet {
    private final Image PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");

    /**
     * Standard pipe constructor
     * @param isLvl1 refer to PipeSet.java Javadoc
     */
    public PlasticPipe(boolean isLvl1) {
        super(isLvl1);
    }

    /**
     * renders the pipes on screen.
     */
    @Override
    public void renderPipeSet() {
        PLASTIC_PIPE_IMAGE.draw(pipeX, yTopPipe);
        PLASTIC_PIPE_IMAGE.draw(pipeX, yBottomPipe, ROTATOR);
    }

    /**
     * Updates state of steel pipes.
     * @param input This is the player input.
     */
    @Override
    public void update(Input input) {
        super.update(input);
    }

    /**
     * Getter for bounding box of top pipe
     * @return Rectangle This is the bounding box.
     */
    public Rectangle getBox() {
        return PLASTIC_PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, yTopPipe));
    }

    /**
     * Getter for bounding box of bottom pipe
     * @return Rectangle This is the bounding box.
     */
    public Rectangle getBottomBox() {
        return PLASTIC_PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, yBottomPipe));
    }
}
