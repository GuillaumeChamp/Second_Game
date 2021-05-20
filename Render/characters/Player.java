package characters;

import image_define.ExtendImage.PlayerSkin;
import image_define.ExtendImage.Spider;
import image_define.Level;
import image_define.Levels.Block;
import image_define.Levels.Exit;
import image_define.MovingAnimatedImage;
import javafx.geometry.Rectangle2D;

public class Player {
    public PlayerSkin skin;
    public Level location;
    public boolean nextLevel = false;

    public Player(int x, int y, int width, int height, int mass, Level currentLevel){
        skin = new PlayerSkin(x, y, width, height);
        skin.setMass(mass);
        skin.setDuration(0.1);
        skin.setPosition(x, 10);
        location = currentLevel;
    }
    public boolean CanJump(){
        return skin.CanJump(this.location);
    }
    public void updateSkin() {
        for (MovingAnimatedImage e :location.enemies){
            if (e instanceof Spider) ((Spider) e).Hit(skin);
        }
        this.skin.update(this.location);
        boolean end = false;
        for (Exit exit : location.getExitList())
        if (exit.getBlock().intersects(this.skin.getPositionX(),this.skin.getPositionY(),1,location.getSizeY()-this.skin.getPositionY())) end =true;
        if (end)  this.nextLevel = true;
    }
}
