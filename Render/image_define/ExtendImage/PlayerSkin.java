package image_define.ExtendImage;

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

    /**
     * Make the player move with the basic physic
     * @param time not used (might be remove latter)
     * @param location current Level (where the player is)
     */
    public void update(double time, Level location,boolean onFireSide) {
        Image[] left = playerLeft;
        Image[] right = playerRight;
        Image[] stopped = playerStopped;
        double oldX = positionX;
        double oldY = positionY;

        accelerationX = (forceX-velocityX/friction)/mass;
        accelerationY = (forceY+gravity)/mass;

        velocityX = velocityX+accelerationX;
        velocityY = velocityY+accelerationY;
        if (Math.abs(velocityX)<0.1) velocityX = 0; accelerationX=0;
        if (Math.abs(velocityX)>10) velocityX=10;
        if (Math.abs(velocityY)>8) velocityY=8;

        positionY += velocityY;
        positionX += velocityX;
        if (positionY<0) {
            positionY=0;
            velocityY=-velocityY/5;
        }
        if (positionY > location.getGround(this.getPositionX(), this.getWidth()).getKey() - this.getHeight()) {
            positionY = location.getGround(this.getPositionX(), this.getWidth()).getKey() - this.getHeight();
            velocityY=-velocityY/5;
        }
        if (positionX<0) {
            positionX=0;
            velocityX=-velocityX/5;
        }
        if (positionX > location.getSizeX() - this.getWidth()) {
            positionX = location.getSizeX() - this.getWidth();
            velocityX=-velocityX/5;
        }
        // unable to climb without jumping.
        //fixme : unable to jump while pressing left or right key
        if ((int)oldY > location.getGround(positionX, this.getWidth()).getKey() - this.getHeight()) {
            positionX = oldX;
            positionY = oldY;
            velocityX = 0;
        }

        if (Math.abs(velocityX) < 0.2 && Math.abs(velocityY)< 0.1) {
            this.setFrames(stopped);
            positionY = location.getGround(positionX, this.getWidth()).getKey() - this.getHeight();
        } else if (velocityX < 0) {
            this.setFrames(left);
        } else {
            this.setFrames(right);
        }
//Todo make the code here more readable
        if (!onFireSide && location.getGround(positionX, this.getWidth()).getValue()) {
            //set friction when on ice
            this.setFriction(10);
        } else {
            this.setFriction(1);
        }

    }
}
