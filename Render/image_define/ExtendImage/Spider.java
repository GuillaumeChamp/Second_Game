package image_define.ExtendImage;

import image_define.Level;
import image_define.MovingAnimatedImage;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Spider extends MovingAnimatedImage {
    private int xStart;
    private int xEnd;
    private boolean groundOnly;
    private int speed;
    private int yLow;
    private int yHigh;
    public Image[] spiderImage;

    /**
     * Define a new spider entity
     * @param x starting x position
     * @param y starting y position
     * @param width width of the spider
     * @param height height of the spider
     * @param currentLevel active Level
     */
    public Spider(int x, int y, int width, int height, Level currentLevel) {
        super(x, y, width, height);
        this.groundOnly = true;
        this.speed = 1;

        spiderImage = new Image[1];
        spiderImage[0] = new Image("resources/spider/spider.png");
        this.setFrames(spiderImage);

        this.yLow =y;
        this.yHigh = 0;
        this.setPosition(x, yLow);
    }

    /**
     * Define the range of movement of the spider
     * @param xStart minimum x of the patrol range
     * @param xEnd maximum x of the patrol range
     */
    public void setXLimit(int xStart, int xEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
    }

    public void setYLimit(int yLow, int yHigh) {
        this.yLow = yLow;
        this.yHigh = yHigh;
    }

    public boolean isGroundOnly() {
        return groundOnly;
    }
    /**
     * Switch the behavior of the spider (up and down or right and left)
     */
    public void setGroundOnly() {
        this.groundOnly = !this.groundOnly;
    }
    public Web putweb(){
        return new Web(this.getPositionX(),this.yHigh,this.getWidth(),(int) this.getPositionY());
    }
    public void Hit(MovingAnimatedImage target){
        Rectangle2D spiderHitBox = new Rectangle2D(positionX,positionY,this.getWidth(),this.getHeight());
        Rectangle2D targetHitBox = new Rectangle2D(target.getPositionX(),target.getPositionY(),target.getWidth(),target.getHeight());
        if (spiderHitBox.intersects(targetHitBox))target.addForces(-20,20);
    }
    @Override
    public void update(double time) {
        if (this.groundOnly) {
            this.setPosition(this.getPositionX()+this.speed, this.yLow);

            if (this.getPositionX() < this.xStart) {
                this.setPosition(this.xStart, this.yLow);
                this.speed = -this.speed;
            }
            if (this.getPositionX() > this.xEnd) {
                this.setPosition(this.xEnd, this.yLow);
                this.speed = -this.speed;
            }
        } else {
            this.setPosition(this.getPositionX(), this.getPositionY() + this.speed);
            if (this.getPositionY() >= this.yLow) {
                this.speed = -this.speed;
                this.setPosition(this.getPositionX(), this.yLow);
            }
            if (this.getPositionY() <= this.yHigh) {
                this.speed = -this.speed;
                this.setPosition(this.getPositionX(), this.yHigh);
            }
        }
    }
}