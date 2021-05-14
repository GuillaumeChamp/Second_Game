package image_define.Levels;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class Block {
    Rectangle2D block;
    Image skin;

    public Block(long x,long y,long width,long height,String type){
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
