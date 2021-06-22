package image_define.ExtendImage;

import image_define.MovingAnimatedImage;
import javafx.scene.image.Image;

public class Web extends MovingAnimatedImage {
    public Image[] webImage;

    /**
     * Return a web shaped image
     * @param x of top left conner
     * @param y of top left conner
     * @param width of the web (match with the hit box)
     * @param height of the web (match with the hit box)
     */
    public Web(double x, double y, double width, double height){
        super((int)x, (int)y, width, height);
        webImage = new Image[1];
        webImage[0] = new Image("resources/spider/web.png",width,height,false,true);
        this.setFrames(webImage);
    }
}
