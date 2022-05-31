

    import bagel.Image;
    import bagel.Input;
    import bagel.Keys;
    import bagel.util.Point;
    import bagel.util.Rectangle;

    /**
     * Class which represents the Bird in the game.
     * Parts of code in this class is also taken from solutions to Project 1, found at
     * https://canvas.lms.unimelb.edu.au/courses/108869/files/9136756?module_item_id=3206730
     * Author of solution code is Betty Lin
     */
    public class Bird implements Collidable{
        private final Image WING_DOWN_IMAGE0 = new Image("res/level-0/birdWingDown.png");
        private final Image WING_UP_IMAGE0 = new Image("res/level-0/birdWingUp.png");
        private final Image WING_DOWN_IMAGE1 = new Image("res/level-1/birdWingDown.png");
        private final Image WING_UP_IMAGE1 = new Image("res/level-1/birdWingUp.png");
        private final double X = 200;
        private final double FLY_SIZE = 6;
        private final double FALL_SIZE = 0.4;
        private final double INITIAL_Y = 350;
        private final double Y_TERMINAL_VELOCITY = 10;
        private final double SWITCH_FRAME = 10;
        private int frameCount = 0;
        private double y;
        private double yVelocity;
        private Rectangle boundingBox;
        protected boolean isLvl1 = false;

        /**
         * Constructor for Bird.
         */
        public Bird() {
            spawn();
        }

        /**
         * Performs state update upon Bird.
         * @param input is the input from the player - keys pressed.
         */
        public void update(Input input) {
            frameCount += 1;
            if (input.wasPressed(Keys.SPACE)) {
                yVelocity = -FLY_SIZE;
                if (!isLvl1) {
                    WING_DOWN_IMAGE0.draw(X, y);
                }
                else {
                    WING_DOWN_IMAGE1.draw(X, y);
                }
            }
            else {
                yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
                if (frameCount % SWITCH_FRAME == 0) {
                    if (!isLvl1) {
                        WING_UP_IMAGE0.draw(X, y);
                    }
                    else {
                        WING_UP_IMAGE1.draw(X, y);
                    }
                    boundingBox = WING_UP_IMAGE0.getBoundingBoxAt(new Point(X, y));
                }
                else {
                    if (!isLvl1) {
                        WING_DOWN_IMAGE0.draw(X, y);
                    }
                    else {
                        WING_DOWN_IMAGE1.draw(X, y);
                    }
                    boundingBox = WING_DOWN_IMAGE0.getBoundingBoxAt(new Point(X, y));
                }
            }
            y += yVelocity;
        }

        /**
         * Getter for y-coord of Bird.
         * @return double This is the y-coord.
         */
        public double getY() {
            return y;
        }

        /**
         * Getter for x-coord of Bird.
         * @return double This is the x-coord.
         */
        public double getX() {
            return X;
        }

        /**
         * Getter for the bounding box of the bird
         * @return Rectangle This is the bounding box.
         */
        public Rectangle getBox() {
            return boundingBox;
        }

        /**
         * Function implemented from Collidable. 'spawns' the Bird in the window.
         */
        @Override
        public void spawn() {
            y = INITIAL_Y;
            yVelocity = 0;
            boundingBox = WING_DOWN_IMAGE0.getBoundingBoxAt(new Point(X, y));
        }

        /**
         * Method which resets the y-coord of the bird.
         */
        public void respawn() {
            y = INITIAL_Y;
        }
    }
