package image_define;

import image_define.Levels.Block;
import image_define.Levels.Exit;
import image_define.Levels.Water;
import javafx.scene.canvas.GraphicsContext;
import image_define.ExtendImage.Spider;
import image_define.ExtendImage.Web;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Level {
    public boolean isFire = true;
    public boolean endGame = false;
    private ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<Exit> exitList = new ArrayList<>();
    private ArrayList<Block> breakableList = new ArrayList<>();
    private ArrayList<Water> ice = new ArrayList<>();
    private ArrayList<Web> ladder = new ArrayList<>();
    private double sizeX;
    private double sizeY;
    public double startX;
    public double startY;
    public ArrayList<image_define.MovingAnimatedImage> enemies = new ArrayList<>();
    private String tips = "";

    public Level() {
    }

    public void setSize(double x, double y) {
        this.sizeX = x;
        this.sizeY = y;
    }

    /**
     * define levels function.
     *
     * @param tips phrase to show
     */
    public void setTips(String tips) {
        this.tips = tips;
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void addIce(Water water) {
        ice.add(water);
    }

    public void addSpider(Spider spider) {
        enemies.add(spider);
    }

    /**
     * Define levels function.
     * Used to add an exit.
     *
     * @param exitBlock to add
     */
    public void addExitBlock(Exit exitBlock) {
        this.exitList.add(exitBlock);
    }

    /**
     * define levels function.
     *
     * @param breakable add this to breakable block list
     */
    public void addBreakable(Block breakable) {
        this.breakableList.add(breakable);
        this.blocks.add(breakable);
    }

    /**
     * function use to update the world
     *
     * @param breakable remove this block from boundary
     */
    public void breakBlock(Block breakable) {
        if (breakableList.contains(breakable)) {
            this.blocks.remove(breakable);
            this.breakableList.remove(breakable);
        }
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
    public void clear() {
        ladder = new ArrayList<>();
        enemies = new ArrayList<>();
        ice = new ArrayList<>();
        isFire = true;
        blocks = new ArrayList<>();
        breakableList = new ArrayList<>();
        exitList.clear();
    }

    /**
     * function used to check if the player can climb
     *
     * @param X position of the player
     * @param Y position of the player
     * @return true if the player can climb here
     */
    public boolean ThereIsALadder(double X, double Y) {
        boolean ans = false;
        for (Web string : ladder) {
            Rectangle2D HitBox = new Rectangle2D(string.getPositionX(), string.getPositionY(), string.getWidth(), string.getHeight());
            if (HitBox.contains(X, Y)) ans = true;
        }
        return ans;
    }

    /**
     * function for loop. Animate the level.
     *
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
                ((Spider) enemy).setGroundOnly();
                if (!((Spider) enemy).isGroundOnly()) {
                    NewLadder.add(((Spider) enemy).putweb());
                }
            }
            this.ladder = NewLadder;
        }
    }

    /**
     * Draw all entity attached to this level
     *
     * @param gc          GraphicsContext of the active application
     * @param offsetLandX of the window
     * @param offsetLandY of the window
     * @param time        to animate the entities (only use for differential)
     */
    public void drawLevel(GraphicsContext gc, double offsetLandX, double offsetLandY, double time) {
        gc.fillText(tips, 400, 300);
        for (image_define.MovingAnimatedImage web : ladder) {
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
                gc.drawImage(enemy.getFrame(time), XoffsetEnemy, (YoffsetEnemy - enemy.getWidth()), enemy.getWidth(), enemy.getHeight());
            }
        }
        for (Block block : blocks) {
            gc.drawImage(block.getSkin(isFire), block.getBlock().getMinX()-offsetLandX, block.getBlock().getMinY()-offsetLandY);
        }
        for (Water water : ice){
            gc.drawImage(water.getSkin(isFire), water.getBlock().getMinX()-offsetLandX, water.getBlock().getMinY()-offsetLandY);
        }
    }

    public void calculateStart() {
        startY = sizeY-1;
        startX = 1; //0 if possible
        boolean end = false;
        while (startX < sizeX &&!end) {
            while (startY > 0 &&!end) {
                for (Block b : blocks) {
                    if (!b.getBlock().contains(startX, startY)) break;
                    else end = true;
                }
                startY -= 16;
            }
            startY = 0;
            startX += 16;
        }
    }
}