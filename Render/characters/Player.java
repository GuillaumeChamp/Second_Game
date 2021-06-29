package characters;

import image_define.ExtendImage.PlayerSkin;
import image_define.ExtendImage.Spider;
import image_define.Level;
import image_define.Levels.Exit;
import image_define.Levels.LevelLoader;
import image_define.MovingAnimatedImage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Player {
    public PlayerSkin skin;
    public Level location;

    public Player(int x, int y, int width, int height, int mass, Level currentLevel){
        skin = new PlayerSkin(x, y, width, height);
        skin.setMass(mass);
        skin.setDuration(0.1);
        skin.setPosition(x, 10);
        location = currentLevel;
    }

    /**
     * methode to limit the place where the player can jump
     * @return true if player can jump from here
     */
    public boolean CanJump(){
        return skin.CanJump(location);
    }

    /**
     * Update the position of the player checking collision and the end of the level
     */
    public Boolean updateSkin() {
        for (MovingAnimatedImage e :location.enemies){
            if (e instanceof Spider) ((Spider) e).Hit(skin);
        }
        this.skin.update(this.location);
        ArrayList<Exit> exits= location.getExitList();
        for (Exit exit : exits) {
            if (exit.getBlock().intersects(this.skin.getPositionX(),this.skin.getPositionY(),skin.getWidth(),skin.getHeight())) {
                if (location.endGame) {
                    return true;
                }
                this.exit(exit);
                break;
            }
        }
        return false;
    }

    /**
     * Make the player go to the next level
     * @param exit hit by the player
     */
    public void exit(Exit exit){
        LevelLoader loader = new LevelLoader();
        location.clear();
        try {
            location=loader.load(exit.getLink());
        } catch (Exception e) {
            System.out.println("reload default");
            try {
                location=loader.load(1);
            } catch (Exception fileNotFoundException) {
                System.err.println("Default level deleted");
            }
        }
        skin.setPosition(location.startX, location.startY);
        location.setGroundOnly();
        location.setGroundOnly();
    }
}
