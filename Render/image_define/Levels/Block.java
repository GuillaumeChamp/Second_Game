package image_define.Levels;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class Block {
    Rectangle2D block;
    Image skin;

    /**
     * Create a block that can be use to define the level
     * @param x position of the upper left corner
     * @param y position of the upper left corner
     * @param width of the block
     * @param height of the block
     * @param type to define the texture
     */
    public Block(double x,double y,double width,double height,String type){
        block = new Rectangle2D(x,y,width,height);
        switch (type){
            //later grass and more argument will be add
            case "rock" :
                //path to fire shape block
                break;
            case "grass":
                //path to ice shape block
                break;
            default:
                //path to no shape block
                break;
        }
    }

    public Rectangle2D getBlock() {
        return block;
    }
}
