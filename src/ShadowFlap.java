import bagel.*;
import bagel.util.Rectangle;

import java.util.Random;

/**
 * Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
 * @author Leo Robinson
 *
 * Much of the below code is from the Project 1 solution, found on SWEN20003 canvas
 * https://canvas.lms.unimelb.edu.au/courses/108869/files/9136756?module_item_id=3206730
 * author of solution code is Betty Lin
 * @version 1.0
 */
public class ShadowFlap extends AbstractGame {
    private final Image BACKGROUND_IMAGE0 = new Image("res/level-0/background.png");
    private final Image BACKGROUND_IMAGE1 = new Image("res/level-1/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String LVL_UP_MSG = "LEVEL-UP!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private final int SHOOT_MSG_OFFSET = 68;
    private final int HEALTH_LVL0 = 3;
    private final int HEALTH_LVL1 = 6;
    private final int MAX_X_VELOCITY = -25;
    private final int MIN_X_VELOCITY = -5;
    private final int LVL_UP_TIME = 20;
    private Bird bird;
    private PipeSet[] pipeSet;
    private LifeBar lifeBar;
    private Weapon weapon;
    private int score;
    private int health;
    private boolean gameOn;
    private boolean collision;
    private boolean win;
    private boolean gameOver;
    private int lvlUp;
    private boolean isLvl1;
    private int frameCount;
    private int scoreFrameCount;
    private double xVelocity;
    private Random rand = new Random();

    /** Constructor for ShadowFlap class.
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        pipeSet = new PipeSet[3];
        lifeBar = new LifeBar();
        score = 0;
        gameOn = false;
        collision = false;
        win = false;
        isLvl1 = false;
        gameOver = false;
        frameCount = 0;
        health = HEALTH_LVL0;
        xVelocity = MIN_X_VELOCITY;
        scoreFrameCount = 0;

    }

    /**
     * The entry point for the program.
     * @param args standard parameter for main() method.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     * @param input is the input from the player - keys pressed.
     * makes use of all methods and classes included in the src file.
     */
    @Override
    public void update(Input input) {
        if (!isLvl1) {
            BACKGROUND_IMAGE0.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }
        else {
            BACKGROUND_IMAGE1.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // game has not started
        if (!gameOn) {
            renderInstructionScreen(input, isLvl1);
        }

        // if game is over
        if (gameOver) {
            renderGameOverScreen();
        }

        // lose life
        if (collision || birdOutOfBound()) {
            health--;
            lifeBar.health--;
            if (health == 0) {
                renderGameOverScreen();
                gameOver = true;
            }
            if (collision) {
                collision = false;
            }

            if (birdOutOfBound()) {
                bird.respawn();
            }

        }

        // level up
        if (lvlUp > 0 && lvlUp < LVL_UP_TIME+1) {
            renderLevelUpScreen();
            lvlUp++;
            lifeBar.health = lifeBar.HEALTH_LVL1;
            health = lifeBar.HEALTH_LVL1;
        }

        // start level 1 screen
        if (lvlUp >= LVL_UP_TIME+1) {
            lvlUp = 0;
            isLvl1 = true;
            bird.isLvl1 = true;
            lifeBar.isLvl1 = true;
            gameOn = false;

            if (rand.nextBoolean()) {
                weapon = new Rock();
                weapon.isBomb = false;
            }
            else {
                weapon = new Bomb();
                weapon.isBomb = true;
            }

        }

        // game won
        if (win) {
            renderWinScreen();
        }

        // game is active
        if (gameOn && !collision && !win && !birdOutOfBound() && lvlUp==0 && !gameOver) {

            bird.update(input);

            xVelocity = updateSpeed(input, xVelocity);


            if (isLvl1) {
                weapon.update(input);

                if (weapon.isCollected && !weapon.isThrown) {
                    weapon.xPos = bird.getX();
                    weapon.yPos = bird.getY();
                }
                if (!weapon.isCollected) {
                    weapon.xVelocity = xVelocity;
                    weapon.isCollected = detectCollection(bird, weapon);
                }
                if ((weapon.yPos == weapon.OUT_OF_THE_WAY || weapon.xPos < 0) && weapon.frameCount == 0) {
                    if (rand.nextBoolean()) {
                        weapon = new Rock();
                        weapon.isBomb = false;
                    }
                    else {
                        weapon = new Bomb();
                        weapon.isBomb = true;
                    }
                }
            }


            for (int i=0; i< pipeSet.length; i++) {

                if (frameCount == i * 100) {

                    if (isLvl1) {
                        if (rand.nextBoolean()) {
                            pipeSet[i] = new SteelPipe(isLvl1);
                            pipeSet[i].isSteel = true;
                        }
                        else {
                            pipeSet[i] = new PlasticPipe(isLvl1);
                            pipeSet[i].isSteel = false;
                        }
                    }
                    else {
                        pipeSet[i] = new PlasticPipe(isLvl1);
                        pipeSet[i].isSteel = false;
                    }
                }
                if (pipeSet[i] != null) {
                    pipeSet[i].update(input);
                    pipeSet[i].pipeVelocity = xVelocity;
                    if (detectCollision(bird.getBox(), pipeSet[i])) {
                        pipeSet[i].isHit = true;
                        pipeSet[i].deSpawn();
                        collision = true;
                    }
                    if (weapon != null &&
                            weapon.isThrown &&
                            detectCollision(weapon.getBox(), pipeSet[i])) {
                        if (weapon.isBomb || !pipeSet[i].isSteel) {
                            pipeSet[i].isHit = true;
                            weapon.isHit = true;
                            score++;
                            pipeSet[i].deSpawn();
                        }
                    }

                }
            }
            lifeBar.update(input);
            updateScore();
            frameCount++;
            if (frameCount == pipeSet.length*100) {
                frameCount = 0;
            }

        }
    }

    /** This method is used to detect if the bird is out of bounds.
     * @return boolean This returns whether or not the bird is out of bounds
     */
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /** This method renders the instruction screen before each level is started.
     * @param input Standard input from the player's keyboard.
     * @param isLvl1 This is a boolean which indicates whether the game is at level 0 or 1.
     */
    public void renderInstructionScreen(Input input, boolean isLvl1) {
        // paint the instruction on screen
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (isLvl1) {
            FONT.drawString(SHOOT_MSG, (Window.getWidth()/2.0-(FONT.getWidth(SHOOT_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SHOOT_MSG_OFFSET);
        }
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
        if (gameOn && isLvl1) {
            for (int i=0; i<pipeSet.length; i++) {
                pipeSet[i].deSpawn();
            }
        }

    }

    /** This method renders the Game over screen, when the game has been lost.
     */
    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /** This method renders the win screen when the game has been won.
     */
    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /** This method renders the level up screen, when the first level has been completed.
     */
    public void renderLevelUpScreen() {
        FONT.drawString(LVL_UP_MSG, (Window.getWidth()/2.0-(FONT.getWidth(LVL_UP_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    /** This method looks for a collision between the collidable object (bird or weapon) and a set of pipes.
     * @param collider This is the bird or weapon.
     * @param pipeSet This is the pipeSet.
     * @return boolean This is the value of whether or not a collision has occurred between the two parameters.
     */
    public boolean detectCollision(Rectangle collider, PipeSet pipeSet) {
        Rectangle topPipeBox = pipeSet.getBox();
        Rectangle bottomPipeBox = pipeSet.getBottomBox();

        if (pipeSet.isSteel) {
            Rectangle topFlameBox = getFlameBox((SteelPipe) pipeSet, true);
            Rectangle bottomFlameBox = getFlameBox((SteelPipe) pipeSet, false);
            return collider.intersects(topPipeBox) ||
                    collider.intersects(bottomPipeBox) ||
                    collider.intersects(topFlameBox) ||
                    collider.intersects(bottomFlameBox);
        }
        return collider.intersects(topPipeBox) ||
                collider.intersects(bottomPipeBox);
    }

    /** Method which detects whether a weapon has been collected.
     * @param bird This is the bird active in the game.
     * @param weapon This is the weapon currently on the field of play.
     * @return boolean This indicated whether a collection has occured.
     */
    public boolean detectCollection(Bird bird, Weapon weapon) {
        Rectangle weaponRect = weapon.getBox();
        Rectangle birdRect = bird.getBox();
        return weaponRect.intersects(birdRect);
    }

    /** This method gets the rectangle surrounding the top or bottom flame of a steel pipe.
     * @param steelPipe this is the steel pipe of interest.
     * @param isTop This indicates whether it is the top or bottom flame required.
     * @return Rectangle This is the rectangle surrounding the indicated flame.
     */
    public Rectangle getFlameBox(SteelPipe steelPipe, boolean isTop) {
        if (isTop) {
            return steelPipe.getTopFlameBox();
        }
        else {
            return steelPipe.getBottomFlameBox();
        }
    }

    /** Method which updates the score of the game.
     */
    public void updateScore() {
        for (int i=0; i< pipeSet.length; i++) {
            if (pipeSet[i] != null &&
                    bird.getX() > pipeSet[i].getBox().right() &&
                    scoreFrameCount == 0 &&
                    !collision &&
                    pipeSet[i].yTopPipe != pipeSet[i].OUT_OF_WAY) {
                score += 1;
                break;
            }
        }
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, 100, 100);

        // detect level up
        if (!isLvl1 && score == 10) {
            isLvl1 = true;
            lvlUp = 1;
        }
        // detect win
        else if (isLvl1 && score == 30) {
            win = true;
        }
        if (score > 0) {
            scoreFrameCount++;
        }
        if (scoreFrameCount == 100) {
            scoreFrameCount = 0;
        }
    }

    /** Method which updates the speed of the game, changed by 'L' or 'K' keys.
     * @param input This is again the standard keyboard input.
     * @param velocity This is the current speed of the game.
     * @return double This is the updated speed of the game based on any input received.
     */
    public double updateSpeed(Input input, double velocity) {
        if (input.wasPressed(Keys.L)) {
            velocity = Math.max(velocity*1.5, MAX_X_VELOCITY);
        }
        else if (input.wasPressed(Keys.K)) {
            velocity = Math.min(velocity/1.5, MIN_X_VELOCITY);
        }
        return velocity;
    }

}
