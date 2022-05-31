import bagel.Image;
import bagel.Input;

/**
 * Class representing the life bar in the top left corner of the game window.
 */
public class LifeBar implements Updateable{
    private final Image FULL_HEART_IMAGE = new Image("res/level/fullLife.png");
    private final Image EMPTY_HEART_IMAGE = new Image("res/level/noLife.png");
    private final int X_POS = 100;
    private final int Y_POS = 15;
    private final int GAP = 50;
    private final int HEALTH_LVL0 = 3;
    protected final int HEALTH_LVL1 = 6;
    protected int health;
    private int fullHealth;
    protected boolean isLvl1;
    private int i;

    /**
     * Standard constructor.
     */
    public LifeBar() {
        health = HEALTH_LVL0;
        isLvl1 = false;
        fullHealth = HEALTH_LVL0;
    }

    /**
     * Updates state of the life bar.
     * @param input This is the player input.
     */
    @Override
    public void update(Input input) {
        if (isLvl1) {
            fullHealth = HEALTH_LVL1;
        }
        for (i=0; i<health; i++) {
            FULL_HEART_IMAGE.drawFromTopLeft(X_POS + i*GAP, Y_POS);
        }
        for (i=health; i < fullHealth; i++) {
            EMPTY_HEART_IMAGE.drawFromTopLeft(X_POS + i*GAP, Y_POS);
        }

    }
}
