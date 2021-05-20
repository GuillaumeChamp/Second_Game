package image_define.Levels;

import javafx.scene.image.Image;

public class Exit extends Block{
    int link;
    /**
     * Create a block that can be use to define the level
     *
     * @param x      position of the upper left corner
     * @param y      position of the upper left corner
     * @param width  of the block
     * @param height of the block
     * @param type   to define the texture
     * @param destination to define the level where the player goes
     */
    public Exit(double x, double y, double width, double height, String type,int destination) {
        super(x, y, width, height, type);
        link = destination;
        skin = new Image("resources/breakable.png");
    }
}
