package image_define.ExtendImage;

import image_define.Levels.Block;
import javafx.scene.image.Image;
import image_define.Level;
import image_define.MovingAnimatedImage;

public class PlayerSkin extends MovingAnimatedImage {
    public Image[] playerLeft;
    public Image[] playerRight;
    public Image[] playerStopped;

    public PlayerSkin(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.playerLeft = new Image[2];
        this.playerRight = new Image[2];
        this.playerStopped = new Image[1];
        for (int i=0;i<2;i++) playerLeft[i]=new Image("resources/player/left"+(i+1)+".png");
        for (int i=0;i<2;i++) playerRight[i]=new Image("resources/player/right" +(i+1)+".png");
        for (int i=0;i<1;i++) playerStopped[i]=new Image("resources/player/mid.png");
        this.setFrames(playerStopped);
        this.setFriction(0.8);
    }
    public void climb(Level location){
        double oldX = positionX;
        double oldY = positionY;
        if (positionY>0) positionY += forceY/5;
        positionX= positionX+forceX;
        if (unable_to_move(location,positionX,positionY)){
            positionY=oldY;
            positionX=oldX;
        }
    }
    public boolean CanJump(Level location){
        Block b = currentBlock(location,positionX,positionY);
        double groundLevel = b.getBlock().getMinY();
        return (positionY >  groundLevel- 30);
    }
    /**
     * Make the player move with the basic physic
     * @param location current Level (where the player is)
     */
    public void update(Level location) {
        if (location.ThereIsALadder(positionX, positionY)) {
            this.climb(location);
        }
        else {
            Image[] left = playerLeft;
            Image[] right = playerRight;
            Image[] stopped = playerStopped;
            double oldX = positionX;
            double oldY = positionY;
            accelerationX = (forceX - velocityX / friction) / mass;
            accelerationY = (forceY + gravity) / mass;
            if(this.IsOnTheGround(location)) {
                accelerationY = forceY/mass;
                velocityY=0;
            }
            velocityX = velocityX + accelerationX;
            velocityY = velocityY + accelerationY;
            if (Math.abs(velocityX) < 0.1) velocityX = 0;
            accelerationX = 0;
            if (Math.abs(velocityX) > 10) velocityX = 10*(velocityX/Math.abs(velocityX));
            if (Math.abs(velocityY) > 8) velocityY = 8*(velocityY/Math.abs(velocityY));

            positionY += velocityY;
            positionX += velocityX;
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
            if (this.unable_to_move(location,positionX,positionY)){
                if(this.unable_to_move(location,oldX,positionY)) {
                   positionY = oldY;
                   velocityY=0;
                }
                if(this.unable_to_move(location,positionX,oldY)) positionX=oldX;
            }
            if (Math.abs(velocityX) < 0.2 && this.IsOnTheGround(location)) {
                this.setFrames(stopped);
                velocityX=0;
            } else if (velocityX < 0) {
                this.setFrames(left);
            } else {
                this.setFrames(right);
            }
            //Todo replace
            /*
            if (!location.isFire && this.currentBlock(location,positionX,positionY).get== ) {
                this.setFriction(10);
            } else {
                this.setFriction(1);
            }

             */
        }
    }
}
