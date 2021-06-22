package image_define;

import image_define.Levels.Block;
import image_define.Levels.DefineLevel;
import image_define.Levels.Exit;
import image_define.Levels.Water;
import javafx.scene.canvas.GraphicsContext;
import image_define.ExtendImage.Spider;
import image_define.ExtendImage.Web;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Level implements DefineLevel {
    public boolean isFire=true;
    public boolean endGame=false;
    private javafx.scene.image.Image iceBackground;
    private javafx.scene.image.Image fireBackground;
    private ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<Exit> exitList = new ArrayList<>();
    private final ArrayList<Block> breakableList = new ArrayList<>();
    private ArrayList<Water> ice = new ArrayList<>();
    private ArrayList<Web> ladder= new ArrayList<>();
    private double sizeX;
    private double sizeY;
    public double startX;
    public ArrayList<image_define.MovingAnimatedImage> enemies = new ArrayList<>();
    private String tips = "";

    /**
     * Create a new Level with the selected limits
     * @param width  width of the Level
     * @param height height of the Level
     */
    public Level(double width, double height){
        this.sizeY = height;
        this.sizeX = width;
        this.startX = 0;
    }
    public Level(){}
    public void setSize(double x,double y){
        this.sizeX=x;
        this.sizeY=y;
    }

    /**
     * define levels function.
     * Load to this level the background
     * @param fire background of the fire side
     * @param ice background of the ice side
     */
    public void setBackground(Image fire,Image ice){
        iceBackground=ice;
        fireBackground=fire;
    }

    /**
     * define levels function.
     * @param tips phrase to show
     */
    public void setTips(String tips) {
        this.tips = tips;
    }

    /**
     * define levels function.
     * @param blocks add this list of block at the level
     */
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }
    public void addBlock(Block block){
        blocks.add(block);
    }
    public void addIce(Water water) {ice.add(water);}
    public void addSpider(Spider spider) {enemies.add(spider);}
    /**
     * Define levels function.
     * Used to add an exit.
     * @param exitBlock to add
     */
    public void addExitBlock(Exit exitBlock) {
        this.exitList.add(exitBlock);
    }
    /**
     * define levels function.
     * @param breakable add this to breakable block list
     */
    public void addBreakable(Block breakable){
        this.breakableList.add(breakable);
        this.blocks.add(breakable);
    }

    /**
     * function use to update the world
     * @param breakable remove this block from boundary
     */
    public void breakBlock(Block breakable){
        if (breakableList.contains(breakable)) {
            this.blocks.remove(breakable);
            this.breakableList.remove(breakable);
        }
    }

    /**
     * define levels function.
     * Use to load the water blocks
     * @param ice list of water blocks
     */
    public void setIce(ArrayList<Water> ice) {
        this.ice = ice;
    }

    public ArrayList<Water> getIce() {
        return ice;
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }
    public ArrayList<Exit> getExitList() {
        return exitList;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    /**
     * function to change to level.
     * Need to be call after the end of a level
     */
    public void clear(){
        ladder= new ArrayList<>();
        enemies= new ArrayList<>();
        ice = new ArrayList<>();
        isFire=true;
        exitList.clear();
    }

    /**
     * function used to check if the player can climb
     * @param X position of the player
     * @param Y position of the player
     * @return true if the player can climb here
     */
    public boolean ThereIsALadder(double X, double Y) {
        boolean ans= false;
        for (Web string : ladder){
            Rectangle2D HitBox = new Rectangle2D(string.getPositionX(),string.getPositionY(), string.getWidth(), string.getHeight());
            if (HitBox.contains(X,Y)) ans = true;
        }
        return ans;
    }

    /**
     * function to define levels
     * Make the size of the level matching with
     */
    public void Resize(){
        this.sizeX= fireBackground.getWidth();
        this.sizeY= fireBackground.getHeight();

    }

    /**
     * function for loop. Animate the level.
     * @param time to animate image
     */
    public void updateLevel(double time) {
        for (image_define.MovingAnimatedImage enemy : enemies) {
            enemy.update(time);
        }
    }

    /**
     * change the side of the level and behaviour of entities
     */
    public void swap() {
        isFire = !isFire;
        this.setGroundOnly();
    }

    /**
     * Sub-function of swap
     * Make the spiders swap
     */
    public void setGroundOnly() {
        ArrayList<Web> NewLadder = new ArrayList<>();
        for (image_define.MovingAnimatedImage enemy : enemies) {
            if (enemy instanceof Spider) {
                ((Spider)enemy).setGroundOnly();
                if (!((Spider) enemy).isGroundOnly()) {
                    NewLadder.add(((Spider) enemy).putweb());
                }
            }
            this.ladder=NewLadder;
        }
    }

    /**
     * Draw all entity attached to this level
     * @param gc GraphicsContext of the active application
     * @param offsetLandX of the window
     * @param offsetLandY of the window
     * @param time to animate the entities (only use for differential)
     */
    public void drawLevel(GraphicsContext gc, double offsetLandX, double offsetLandY, double time) {
        gc.drawImage(this.getBackground(), offsetLandX, offsetLandY, sizeX, sizeY,0,0,sizeX,sizeY);
        gc.fillText(tips, 400, 300);
        for (image_define.MovingAnimatedImage web : ladder){
            double OffSetWeb = web.getPositionX() - offsetLandX;
            double OffSetWebY = web.getPositionY() - offsetLandY;
            if (OffSetWeb > 0 && OffSetWeb < sizeX) {
                gc.drawImage(web.getFrame(time), OffSetWeb, OffSetWebY, web.getWidth(), web.getHeight());
            }
        }
        for (image_define.MovingAnimatedImage enemy : enemies) {
            double XoffsetEnemy = enemy.getPositionX() - offsetLandX;
            double YoffsetEnemy = enemy.getPositionY() - offsetLandY;
            if (XoffsetEnemy > 0 && XoffsetEnemy < sizeX) {
                gc.drawImage(enemy.getFrame(time), XoffsetEnemy, (YoffsetEnemy- enemy.getWidth()), enemy.getWidth(), enemy.getHeight());
            }
        }
        for(Block block : breakableList){
            gc.drawImage(block.getSkin1(),block.getBlock().getMinX()-offsetLandX,block.getBlock().getMinY());
        }
    }

    /**
     * Sub-function of draw level
     * Print the background matching with the active side
     * @return active background
     */
    public Image getBackground(){
        if (isFire) return fireBackground;
        return iceBackground;
    }
}
