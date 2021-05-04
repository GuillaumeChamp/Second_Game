package image_define.ExtendImage;

import image_define.Level;
import image_define.MovingAnimatedImage;
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

        this.yLow = currentLevel.getGround((double)x, width).getKey() - height;
        this.yHigh = 0;
        this.setPosition(x, currentLevel.getGround((double)x, width).getKey() - height);
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

    /**
     * Switch the behavior of the spider (up and down or right and left)
     */
    public void setGroundOnly() {
        this.groundOnly = !this.groundOnly;
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
            if (this.getPositionX() >= this.xEnd) {
                this.setPosition(this.xEnd, this.getPositionY() + this.speed);
                if (this.getPositionY() >= this.yLow) {
                    this.speed = -this.speed;
                    this.setPosition(this.xEnd, this.yLow);
                }

                if (this.getPositionY() <= this.yHigh) {
                    this.speed = -this.speed;
                    this.setPosition(this.xEnd, this.yHigh);
                }
            } else {
                this.setPosition(this.getPositionX()+this.speed, this.yLow);

                if (this.getPositionX() < this.xStart) {
                    this.setPosition(this.xStart, this.getPositionY());
                    this.speed = -this.speed;
                }
            }
        }
    }
}