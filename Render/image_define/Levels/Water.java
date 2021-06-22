package image_define.Levels;

import javafx.scene.image.Image;

public class Water extends Block{
    public Water(double x,double y,double width,double height){
        super( x, y, width, height,"null");
        skin1 = new Image("resources/Level/ice1.png",width,height,false,true);
        skin2 = new Image("resources/Level/ice2.png",width,height,false,true);
    }
}
