import bagel.DrawOptions;
import bagel.Input;
import bagel.Window;
import bagel.util.Rectangle;
import java.util.Random;

/**
 * Class representing the pipes in the game.
 */
public abstract class PipeSet implements Passable {
    protected final int PIPE_GAP = 168;
    private final double HIGH_TOP_PIPE_Y = -284;
    private final double HIGH_BOTTOM_PIPE_Y = 100 + PIPE_GAP + 384;
    private final double MID_TOP_PIPE_Y = -84;
    private final double MID_BOTTOM_PIPE_Y = 300 + PIPE_GAP + 384;
    private final double LOW_TOP_PIPE_Y = 116;
    private final double LOW_BOTTOM_PIPE_Y = 500 + PIPE_GAP + 384;
    protected final int OUT_OF_WAY = -2000;
    protected final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    protected double pipeX = Window.getWidth();
    protected int lvl0PipeType;
    protected int lvl1PipeType;
    protected double pipeVelocity = INIT_X_VELOCITY;
    protected boolean isSteel = false;
    protected double yTopPipe;
    protected double yBottomPipe;
    private Random rand = new Random();
    protected boolean isLvl1;
    protected boolean isHit = false;

    /**
     * Constructor
     * @param isLvl1 indicates whether it is level 1 in the game.
     */
    public PipeSet(boolean isLvl1) {
        this.isLvl1 = isLvl1;
        spawn();
    }

    /**
     * Abstract method used to render the pipes in the window.
     */
    public abstract void renderPipeSet();

    /**
     * Again, this updates the state of the Pipes.
     * @param input This is the player input.
     */
    public void update(Input input) {
        renderPipeSet();
        pipeX += pipeVelocity;
    }

    /**
     * Abstract getter method for the top pipe bounding box.
     * @return Rectangle
     */
    public abstract Rectangle getBox();

    /**
     * Abstract getter method for the bottom pipe bounding box.
     * @return Rectangle
     */
    public abstract Rectangle getBottomBox();

    /**
     * Used to spawn a new set of pipes on the window.
     */
    public void spawn() {
        pipeX = Window.getWidth();
        // lvl0PipeType is used to determine pipe type: e.g. 0 for low gap, 1 for mid gap, 2 for high gap
        // lvl1PipeType randomly selects an integer between 100 and 500
        if (!isLvl1) {
            lvl0PipeType = rand.nextInt(3);

            if (lvl0PipeType == 0) {
                yTopPipe = LOW_TOP_PIPE_Y;
                yBottomPipe = LOW_BOTTOM_PIPE_Y;
            }
            else if (lvl0PipeType == 1) {
                yTopPipe = MID_TOP_PIPE_Y;
                yBottomPipe = MID_BOTTOM_PIPE_Y;
            }
            else {
                yTopPipe = HIGH_TOP_PIPE_Y;
                yBottomPipe = HIGH_BOTTOM_PIPE_Y;
            }
        }
        else {
            lvl1PipeType = rand.nextInt(401) + 100;
            yTopPipe = lvl1PipeType - Window.getHeight() / 2;
            yBottomPipe = lvl1PipeType + PIPE_GAP + Window.getHeight() / 2;

        }
    }

    /**
     * Used to 'destroy' the pipes if they are hit by moving them out of the course of the window.
     */
    public void deSpawn() {
        yTopPipe = OUT_OF_WAY;
        yBottomPipe = OUT_OF_WAY;
    }


}
