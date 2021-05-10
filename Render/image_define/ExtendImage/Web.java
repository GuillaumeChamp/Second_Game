package image_define.ExtendImage;

import image_define.MovingAnimatedImage;
import javafx.scene.image.Image;

public class Web extends MovingAnimatedImage {
    public Image[] webImage;

    public Web(double x, double y, int width, int height){
        super((int)x, (int)y, width, height);
        webImage = new Image[1];
        //Todo : make a better picture for string web
        webImage[0] = new Image("resources/spider/web.png",width,height,false,true);
        this.setFrames(webImage);
    }
}
