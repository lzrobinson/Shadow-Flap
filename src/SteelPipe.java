import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class representing the Steel Pipe in the game.
 */
public class SteelPipe extends PipeSet {
    private final Image STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final int FLAME_TIME = 20;
    private final double FLAME_HEIGHT = 39;
    private final double FLAME_WIDTH = 65;
    private int frameCount;
    private boolean isFlame;


    /**
     * Standard pipe constructor
     * @param isLvl1 refer to PipeSet.java Javadoc
     */
    public SteelPipe(boolean isLvl1) {
        super(isLvl1);
        frameCount = 0;
        isFlame = false;
    }

    /**
     * renders the pipes on screen.
     */
    @Override
    public void renderPipeSet() {
        STEEL_PIPE_IMAGE.draw(pipeX, yTopPipe);
        STEEL_PIPE_IMAGE.draw(pipeX, yBottomPipe, ROTATOR);
    }

    /**
     * Updates state of steel pipes.
     * @param input This is the player input.
     */
    @Override
    public void update(Input input) {

        super.update(input);
        if (frameCount % FLAME_TIME == 0 && !isFlame) {
            renderFlames();
            isFlame = true;
        }
        else if (frameCount % FLAME_TIME == 0 && isFlame) {
            isFlame = false;
        }
        else if (isFlame) {
            renderFlames();
        }
        frameCount++;
    }

    /**
     * This method renders the flame images from steel pipes.
     */
    public void renderFlames() {
        // Change magic no.
        FLAME_IMAGE.draw(pipeX, yTopPipe + 384 + FLAME_HEIGHT/2);
        FLAME_IMAGE.draw(pipeX, yTopPipe + 384 + PIPE_GAP - FLAME_HEIGHT/2, ROTATOR);

    }

    /**
     * Getter for bounding box of top flame
     * @return Rectangle This is the bounding box.
     */
    public Rectangle getTopFlameBox() {
        return FLAME_IMAGE.getBoundingBoxAt(new Point(pipeX, lvl1PipeType + FLAME_HEIGHT/2));
    }

    /**
     * Getter for bounding box of bottom flame
     * @return Rectangle This is the bounding box.
     */
    public Rectangle getBottomFlameBox() {
        return FLAME_IMAGE.getBoundingBoxAt(new Point(pipeX, lvl1PipeType + PIPE_GAP - FLAME_HEIGHT/2));
    }

    /**
     * Getter for bounding box of top pipe
     * @return Rectangle This is the bounding box.
     */
    public Rectangle getBox() {
        return STEEL_PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, yTopPipe));
    }

    /**
     * Getter for bounding box of bottom pipe
     * @return Rectangle This is the bounding box.
     */
    public Rectangle getBottomBox() {
        return STEEL_PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, yBottomPipe));
    }

    /**
     * Used to 'destroy' the pipes if they are hit by moving them out of the course of the window.
     */
    @Override
    public void deSpawn() {
        super.deSpawn();
        lvl1PipeType = OUT_OF_WAY;
    }
}

