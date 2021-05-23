package characters;

import image_define.ExtendImage.PlayerSkin;
import image_define.ExtendImage.Spider;
import image_define.Level;
import image_define.Levels.Exit;
import image_define.MovingAnimatedImage;

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
    public void updateSkin() {
        for (MovingAnimatedImage e :location.enemies){
            if (e instanceof Spider) ((Spider) e).Hit(skin);
        }
        this.skin.update(this.location);
        for (Exit exit : location.getExitList())
        if (exit.getBlock().intersects(this.skin.getPositionX(),this.skin.getPositionY(),skin.getWidth(),skin.getHeight())) this.exit(exit);
    }

    /**
     * Make the player go to the next level
     * @param exit hit by the player
     */
    public void exit(Exit exit){
        int newLevel = exit.getLink();
        location.clear();
        location.modifyLevel(location,newLevel);
        skin.setPosition(location.startX, skin.currentBlock(location, 0,skin.getPositionY()).getBlock().getMinY()-1);
        location.setGroundOnly();
        location.setGroundOnly();
    }
}
