package image_define;

import image_define.Levels.Block;
import image_define.Levels.DefineLevel;
import image_define.Levels.Water;
import javafx.scene.canvas.GraphicsContext;
import image_define.ExtendImage.Spider;
import image_define.ExtendImage.Web;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Level implements DefineLevel {
    public boolean isFire=true;
    private javafx.scene.image.Image iceBackground;
    private javafx.scene.image.Image fireBackground;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Block> breakableList = new ArrayList<>();
    private ArrayList<Water> ice = new ArrayList<>();
    private ArrayList<Web> ladder= new ArrayList<>();
    private double sizeX;
    private double sizeY;
    public ArrayList<image_define.MovingAnimatedImage> enemies = new ArrayList<>();
    private String tips = "";

    /**
     * Create a new Level with the selected limits
     * @param width  width of the Level
     * @param height height of the Level
     */
    public Level(long width, long height){
        this.sizeY = height;
        this.sizeX = width;
    }

    /**
     * Load to this level the background
     * @param fire background of the fire side
     * @param ice background of the ice side
     */
    public void setBackground(Image fire,Image ice){
        iceBackground=ice;
        fireBackground=fire;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * function use to define the level
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

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

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

    public void clear(){
        ladder= new ArrayList<>();
        enemies= new ArrayList<>();
        isFire=true;
    }

    public boolean ThereIsALadder(double X, double Y) {
        boolean ans= false;
        for (Web string : ladder){
            Rectangle2D HitBox = new Rectangle2D(string.getPositionX(),string.getPositionY(), string.getWidth(), string.getHeight());
            if (HitBox.contains(X,Y)) ans = true;
        }
        return ans;
    }

    public void Resize(){
        this.sizeX= fireBackground.getWidth();
        this.sizeY= fireBackground.getHeight();

    }
    public void updateLevel(double time) {
        for (image_define.MovingAnimatedImage enemy : enemies) {
            enemy.update(time);
        }
    }
    public void swap() {
        isFire = !isFire;
        this.setGroundOnly();
    }
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
    public Image getBackground(){
        if (isFire) return fireBackground;
        return iceBackground;
    }

    public void drawLevel(GraphicsContext gc, double offsetLandX, double offsetLandY, double time) {
        gc.drawImage(this.getBackground(), offsetLandX, offsetLandY, sizeX, sizeY,0,0,sizeX,sizeY);
        gc.fillText(tips, 400, 300);
        for (image_define.MovingAnimatedImage enemy : enemies) {
            double offsetEnemy = enemy.getPositionX() - offsetLandX;
            if (offsetEnemy > 0 && offsetEnemy < sizeX) {
                gc.drawImage(enemy.getFrame(time), offsetEnemy, enemy.getPositionY()-enemy.getHeight(), enemy.getWidth(), enemy.getHeight());
            }
        }
        for (image_define.MovingAnimatedImage web : ladder){
            double OffSetWeb = web.getPositionX() - offsetLandX;
            if (OffSetWeb > 0 && OffSetWeb < sizeX) {
                gc.drawImage(web.getFrame(time), OffSetWeb, web.getPositionY(), web.getWidth(), web.getHeight());
            }
        }
    }
}
