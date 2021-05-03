package image_define;

import javafx.scene.image.Image;

public class MovingAnimatedImage implements java.io.Serializable{
	private Integer width;
	private Integer height;
	protected double positionX;
	protected double positionY;

	private Image[] frames;
	private double duration;

	protected double velocityX;
	protected double velocityY;
	protected double accelerationX;
	protected double accelerationY;
	protected double forceX;
	protected double forceY;
	protected double mass;
	protected double gravity;
	protected double friction;

	public MovingAnimatedImage(int x, int y, int w, int h) {
		this.positionX = x;
		this.positionY = y;
		this.width = w;
		this.height = h;
		this.velocityX = 0;
		this.velocityY = 0;
		this.mass = 40;
		this.gravity = 10;
		this.friction = 3.33;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setFriction(double friction){this.friction=friction;}

	public void setPosition(double x, double y) {
		positionX = x;
		positionY = y;
	}

	public void setFrames (Image[] f){
		frames=f;
	}

	public Image getFrame(double time) {
		int index = (int) Math.floor((time / duration) % frames.length);
		return frames[index];
	}

	public void setDuration (double d){
		duration=d;
	}

	public void setForceX(double forceX) {
		this.forceX = forceX;
	}

	public void setForceY(double forceY) {
		this.forceY = forceY;
	}

	public void addForces(double x, double y) {
        forceX += x;
        forceY += y;
    }

	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * This methode will be override to each entity
	 * @param time might be use for the physic
	 */
	public void update(double time) {
	}
}
