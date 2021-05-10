package image_define;

import image_define.Levels.DefineLevel;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;
import image_define.ExtendImage.Spider;
import image_define.ExtendImage.Web;

import javafx.geometry.Rectangle2D;
import java.util.ArrayList;

public class Level implements DefineLevel {
    private javafx.scene.image.Image background;
    private ArrayList<Pair<Integer, Integer>> levelDescription;
    private ArrayList<Integer> iceDescription = new ArrayList<>();
    private ArrayList<Web> ladder= new ArrayList<>();
    private double sizeX;
    private double sizeY;
    public ArrayList<image_define.MovingAnimatedImage> enemies = new ArrayList<>();

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
     * Set the foreground of the Level
     * @param background the new picture
     */
    public void setBackground(javafx.scene.image.Image background) {
        this.background = background;
    }

    public void setLevelDescription(ArrayList<Pair<Integer, Integer>> levelDescription) {
        this.levelDescription = levelDescription;
    }

    public void setIceDescription(ArrayList<Integer> iceDescription) {
        this.iceDescription = iceDescription;
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    /**
     * function to detect where the player can stand
     * @param currentX localisation of the player
     * @param width size of the player
     * @return Array list of all possible place to stand
     */
    public Pair<Integer, Boolean> getGround(Double currentX, Integer width) {
        for (int i = levelDescription.size() - 1; i >= 0; i--) {
            int curHeight = levelDescription.get(i).getValue();
            int prevHeight = (int) sizeY;
            int nextHeight = (int) sizeY;

            int curStartX = levelDescription.get(i).getKey();
            int leftBorder = curStartX - width;
            int curEndX = (int) sizeX - width;
            int rightBorder = curEndX + width;
            if (i > 0) {
                prevHeight = levelDescription.get(i - 1).getValue();
            }
            if (i < levelDescription.size() - 1) {
                nextHeight = levelDescription.get(i + 1).getValue();
                curEndX = levelDescription.get(i + 1).getKey() - width;
                rightBorder = curEndX + width;
            }
            if (leftBorder <= currentX && currentX < curStartX) {
                if (prevHeight > curHeight) {
                    boolean onIce = iceDescription.contains(curStartX);
                    return new Pair<>(curHeight, onIce);
                }
            } else if (curStartX <= currentX && currentX <= curEndX) {
                boolean onIce = iceDescription.contains(curStartX);
                return new Pair<>(curHeight, onIce);
            } else if (curEndX < currentX && currentX < rightBorder) {
                if (nextHeight > curHeight) {
                    boolean onIce = iceDescription.contains(curStartX);
                    return new Pair<>(curHeight, onIce);
                }
            }
        }
        return new Pair<>((int) sizeY, false);
    }

    public boolean thereisaladder(double X, double Y) {
        boolean ans= false;
        for (Web string : ladder){
            Rectangle2D Hitbox = new Rectangle2D(string.getPositionX(),string.getPositionY(), string.getWidth(), string.getHeight());
            if (Hitbox.contains(X,Y)) ans = true;
        }
        return ans;
    }

    public void Resize(){
        this.sizeX=background.getWidth();
        this.sizeY=background.getHeight();

    }
    public void updateLevel(double time) {
        for (image_define.MovingAnimatedImage enemy : enemies) {
            enemy.update(time);
        }
    }

    public void setGroundOnly() {
        ArrayList<Web> newladder = new ArrayList<>();
        for (image_define.MovingAnimatedImage enemy : enemies) {
            if (enemy instanceof Spider) {
                ((Spider)enemy).setGroundOnly();
                if (!((Spider) enemy).isGroundOnly()) {
                    newladder.add(((Spider) enemy).putweb());
                    System.out.println("new ladder at " + enemy.getPositionX() +" "+ enemy.getPositionY());
                }
            }
            this.ladder=newladder;
        }
    }

    public void drawLevel(GraphicsContext gc, double offsetLandX, double offsetLandY, double time) {
        gc.drawImage(background, offsetLandX, offsetLandY, sizeX, sizeY,0,0,sizeX,sizeY);
        for (image_define.MovingAnimatedImage enemy : enemies) {
            double offsetEnemy = enemy.getPositionX() - offsetLandX;
            if (offsetEnemy > 0 && offsetEnemy < sizeX) {
                gc.drawImage(enemy.getFrame(time), offsetEnemy, enemy.getPositionY(), enemy.getWidth(), enemy.getHeight());
            }
        }
        for (image_define.MovingAnimatedImage web : ladder){
            double offsetweb = web.getPositionX() - offsetLandX;
            if (offsetweb > 0 && offsetweb < sizeX) {
                gc.drawImage(web.getFrame(time), offsetweb, web.getPositionY(), web.getWidth(), web.getHeight());
            }
        }
    }
}
