package image_define;

import image_define.Levels.Block;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class MovingAnimatedImage implements java.io.Serializable{
	private final double width;
	private final double height;
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

	public MovingAnimatedImage(double x, double y, double w, double h) {
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

	public double getWidth() {
		return width;
	}

	public double getHeight() {
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

	/**
	 * Check if the position (x,y) is not on a wall of the level
	 * @param location Level where the entity is
	 * @param x x position checked
	 * @param y y position checked
	 * @return true if the entity can't move here
	 */
	public boolean unable_to_move(Level location,double x,double y){
		boolean ans = false;
		for (Block b : location.getBlocks()){
			if (b.getBlock().contains(x,y-height,width,height)) ans = true;
			if (b.getBlock().intersects(x,y-height,width,height)) ans =true;
		}
		return ans;
	}

	/**
	 * Give the block under the point (x,y) in the Level location
	 * @param location active level
	 * @param x x coordinate of the point check
	 * @param y y coordinate of the point check
	 * @return the block
	 */
	public Block currentBlock(Level location,double x,double y){
		ArrayList<Block> ground= new ArrayList<>();
		Block ans = new Block(0,location.getSizeY()-1,location.getSizeX(),1,"");
		if(!location.isFire)	ground.addAll(location.getIce());
		ground.addAll(location.getBlocks());
		for(Block b : ground){
			if (b.getBlock().intersects(x,y,1,location.getSizeY()-y)){
				if (b.getBlock().getMinY()-y < ans.getBlock().getMinY()-y) ans = b;
			}
		}
		return ans;
	}

	/**
	 * Check if the moving animated image is on the ground
	 * @param location current location of the entity
	 * @return if the player is on the ground
	 */
	public boolean IsOnTheGround(Level location){
		Block b =currentBlock(location,positionX,positionY);
		double groundLevel = b.getBlock().getMinY();
		return (positionY >  groundLevel- 1);
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
