package image_define.ExtendImage;

import image_define.Levels.Block;
import image_define.Levels.Water;
import javafx.scene.image.Image;
import image_define.Level;
import image_define.MovingAnimatedImage;

public class PlayerSkin extends MovingAnimatedImage {
    public Image[] playerLeft;
    public Image[] playerRight;
    public Image[] playerStopped;

    public PlayerSkin(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.playerLeft = new Image[4];
        this.playerRight = new Image[4];
        this.playerStopped = new Image[1];
        for (int i = 0; i < 4; i++) playerLeft[i] = new Image("resources/player/left" + (i + 1) + ".png");
        for (int i = 0; i < 4; i++) playerRight[i] = new Image("resources/player/right" + (i + 1) + ".png");
        for (int i = 0; i < 1; i++) playerStopped[i] = new Image("resources/player/mid.png");
        this.setFrames(playerStopped);
        this.setFriction(0.8);
    }



    /**
     * Set the right frame on "frame" according to the speed of the player (can be move to MovingAnimatedImage if needed)
     *
     * @param location active level
     */
    public void setFrame(Level location) {
        if (Math.abs(velocityX) < 0.2 && this.IsOnTheGround(location)) {
            this.setFrames(playerStopped);
            velocityX = 0;
        } else if (velocityX < 0) {
            this.setFrames(playerLeft);
        } else {
            this.setFrames(playerRight);
        }
    }

    /**
     * Check if the player is on the top of a block and so able to jump
     *
     * @param location active level
     * @return if the player can jump
     */
    public boolean CanJump(Level location) {
        Block b = currentBlock(location, positionX, positionY);
        double groundLevel = b.getBlock().getMinY();
        return (positionY > groundLevel - 30);
    }


    /**
     * Make the player move with the basic physic
     *
     * @param location current Level (where the player is)
     */
    public void update(Level location) {
        if (location.ThereIsALadder(positionX, positionY)) {
            this.climb(location);
        } else {
            double oldX = positionX;
            double oldY = positionY;
            CalculateNewPosition(location);
            breakBlock(location);
            setGoodPosition(oldX,oldY,location);
            setFrame(location);
            slide(location);
        }
    }

    /**
     * Sub function of update. Check if the player is on ice and change the friction if he is on.
     *
     * @param location active level
     */
    public void slide(Level location) {
        if (this.IsOnTheGround(location) && (this.currentBlock(location, positionX, positionY) instanceof Water)) {
            this.setFriction(10);
        } else {
            this.setFriction(1);
        }
    }
    /**
     * Sub-function of update.Make the player going up
     *
     * @param location active level
     */
    public void climb(Level location) {
        double oldX = positionX;
        double oldY = positionY;
        if (positionY > 0) positionY += forceY / 5;
        positionX = positionX + forceX;
        if (unable_to_move(location, positionX, positionY)) {
            positionY = oldY;
            positionX = oldX;
        }
    }

    /**
     * Sub-function of update. Calculate the theoretic new position of the player
     * @param location active level
     */
    public void CalculateNewPosition(Level location){
        accelerationX = (forceX - velocityX / friction) / mass;
        accelerationY = (forceY + gravity) / mass;
        if (this.IsOnTheGround(location)) {
            accelerationY = forceY / mass;
            velocityY = 0;
        }
        velocityX = velocityX + accelerationX;
        velocityY = velocityY + accelerationY;
        if (Math.abs(velocityX) < 0.1) velocityX = 0;
        //accelerationX = 0;
        if (Math.abs(velocityX) > 15) velocityX = 15 * (velocityX / Math.abs(velocityX));
        if (Math.abs(velocityY) > 8) velocityY = 8 * (velocityY / Math.abs(velocityY));
        positionY += velocityY;
        positionX += velocityX;

    }

    /**
     * Set the right position of the player according to the border of the level
     * @param oldX saved position before calculating
     * @param oldY saved position before calculating
     * @param location active level
     */
    //todo ; fix with an elseif
    public void setGoodPosition(double oldX,double oldY,Level location){
        if (positionY < 0) {
            positionY = 0;
            velocityY = -velocityY / 5;
        }
        if (positionX < 0) {
            positionX = 0;
            velocityX = -velocityX / 5;
        }
        if (positionX > location.getSizeX() - this.getWidth()) {
            positionX = location.getSizeX() - this.getWidth();
            velocityX = -velocityX / 5;
        }
        if (this.unable_to_move(location, positionX, positionY)) {
            if (this.unable_to_move(location, oldX, positionY)) {
                positionY = oldY;
                velocityY = 0;
            }
            if (this.unable_to_move(location, positionX, oldY)) {
                positionX = oldX;
                velocityX = 0;
            }
        }
    }

    /**
     * Break the wall in front of the player if the wall is breakable and the player fast enough
     * @param location active level
     */
    public void breakBlock(Level location){
        Block frontBlock = currentBlock(location,positionX+50,positionY-10);
        if (Math.abs(velocityX)>9) location.breakBlock(frontBlock);

    }
}