package image_define;

import image_define.Levels.DefineLevel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import image_define.ExtendImage.Spider;

import java.util.ArrayList;

public class Level implements DefineLevel {
    private javafx.scene.image.Image background;
    private ArrayList<Pair<Integer, Integer>> levelDescription;
    private double sizeX;
    private double sizeY;
    public ArrayList<image_define.MovingAnimatedImage> enemies = new ArrayList<>();

    public Level(long width, long height){
        this.sizeY = height;
        this.sizeX = width;
    }

    public void setBackground(javafx.scene.image.Image background) {
        this.background = background;
    }

    public void setLevelDescription(ArrayList<Pair<Integer, Integer>> levelDescription) {
        this.levelDescription = levelDescription;
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public void Resize(){
        this.sizeX=background.getWidth();
        this.sizeY=background.getHeight();

    }

    public Image getBackground() {
        return background;
    }

    public Integer getGround(Double currentX) {
        for (int i = levelDescription.size() - 1; i >= 0; i--) {
            if (levelDescription.get(i).getKey() <= currentX) {
                return levelDescription.get(i).getValue();
            }
        }
        return (int) sizeY;
    }

    public void updateLevel(double time) {
        for (image_define.MovingAnimatedImage enemy : enemies) {
            enemy.update(time);
        }
    }

    public void setGroundOnly() {
        for (image_define.MovingAnimatedImage enemy : enemies) {
            if (enemy instanceof Spider) {
                ((Spider)enemy).setGroundOnly();
            }
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
    }
}
