package image_define.Levels;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class Block {
    Rectangle2D block;
    Image skin1;
    Image skin2;

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
            case "breakable" :
                skin1 = new Image("resources/Level/breakable1.png",width,height,false,true);
                skin2 = new Image("resources/Level/breakable2.png",width,height,false,true);
                break;
            case "basic":
                skin1 = new Image("resources/Level/basic1.png",width,height,false,true);
                skin2 = new Image("resources/Level/basic2.png",width,height,false,true);
                break;
            case "ground" :
                skin1 = new Image("resources/Level/ground1.png",width,height,false,true);
                skin2 = new Image("resources/Level/ground2.png",width,height,false,true);
                break;
            default:
                //path to no shape block
                break;
        }
    }

    public Image getSkin1() {
        return skin1;
    }
    public Image getSkin(boolean side){
        if (side) return skin1;
        return skin2;
    }

    public Rectangle2D getBlock() {
        return block;
    }
}
