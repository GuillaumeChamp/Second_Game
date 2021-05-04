package characters;

import image_define.ExtendImage.PlayerSkin;
import image_define.Level;

public class Player {
    public PlayerSkin skin;
    public Level location;
    public boolean onFireSide = true;
    public boolean nextLevel = false;

    public Player(int x, int y, int width, int height, int mass, Level currentLevel){
        skin = new PlayerSkin(x, y, width, height);
        skin.setMass(mass);
        skin.setDuration(0.1);
        skin.setPosition(x, currentLevel.getGround((double)x, width).getKey() - height);
        location = currentLevel;
    }

    public void updateSkin(double time) {
        this.skin.update(time, this.location, this.onFireSide);
        boolean end = this.skin.getPositionX() >= this.location.getSizeX() - 1.1 * this.skin.getWidth();
        if (end)  this.nextLevel = true;
    }
}
