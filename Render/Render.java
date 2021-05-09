import java.util.ArrayList;
import characters.Player;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import image_define.Level;
import javafx.util.Pair;


public class Render extends Application {
    final long width = 800; //width of the window
    final long height = 600; //height of the window
    SoundBackground music= new SoundBackground();
    Integer iceFire = 0;
    Integer currentLevelNum = 0;

    public void start(Stage theStage) {
        //TODO : make the start menu
        theStage.setTitle("Stony Journey");
        music.start(theStage);
        ArrayList<KeyCode> input = new ArrayList<>(); //store the keyboard input

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        final long startNanoTime = System.nanoTime();

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Level currentLevel = new Level(width, height);
        currentLevel.modifyLevel(currentLevel, currentLevelNum);
        Player player = new Player(10,0,30,60, 40, currentLevel);
        theScene.setOnKeyPressed(e -> {
                    KeyCode code = e.getCode();
                    if (!input.contains(code))
                        input.add(code);
                    if (code==KeyCode.C){
                        ArrayList<image_define.MovingAnimatedImage> oldenemies = currentLevel.enemies;
                        iceFire = (iceFire + 1) % 2;
                        currentLevel.modifyLevel(currentLevel, currentLevelNum+iceFire);
                        currentLevel.enemies = oldenemies;
                        Pair<Integer, Boolean> groundDes = currentLevel.getGround(player.skin.getPositionX(), player.skin.getWidth());
                        player.skin.setPosition(player.skin.getPositionX(), groundDes.getKey());
                        currentLevel.setGroundOnly();
                        player.onFireSide = iceFire != 1;
                    }
                }
        );

        theScene.setOnKeyReleased(e -> {
            KeyCode code = e.getCode();
            input.remove(code);
        });
        //Todo : make a better load (may passed by a rename)
        Image background0 = new Image( "resources/Level/Background/CloudsBack.png" );
        Image background1 = new Image( "resources/Level/Background/CloudsFront.png" );
        Image background2 = new Image( "resources/Level/Background/BGBack.png" );
        Image background3 = new Image( "resources/Level/Background/BGFront.png" );
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                player.skin.setForceX(0);
                player.skin.setForceY(0);
                if (input.contains(KeyCode.LEFT)) {
                    player.skin.addForces(-10,0);
                }
                if (input.contains(KeyCode.RIGHT)) {
                    player.skin.addForces(10, 0);
                }
                if (input.contains(KeyCode.UP)) {
                    if (player.skin.getPositionY() > (currentLevel.getGround(player.skin.getPositionX(), player.skin.getWidth()).getKey() - 1.5*player.skin.getHeight())) {
                        player.skin.addForces(0, -20);
                    }
                }

                player.updateSkin(t);
                currentLevel.updateLevel(t);

                if (player.nextLevel) {
                    currentLevelNum = (currentLevelNum + 2) % 4;
                    currentLevel.modifyLevel(currentLevel, currentLevelNum);
                    player.nextLevel = false;
                    player.skin.setPosition(0, currentLevel.getGround(0.0, player.skin.getWidth()).getKey());
                }

                double offsetlandX = player.skin.getPositionX() - (width >> 1);
                double offsetlandY = player.skin.getPositionY() - (height >> 1);
                if (offsetlandX < 0) offsetlandX = 0;
                if (offsetlandX > currentLevel.getSizeX()-width) offsetlandX = currentLevel.getSizeX()-width;
                if (offsetlandY < 0) offsetlandY = 0;
                if (offsetlandY > currentLevel.getSizeY()-height) offsetlandY = currentLevel.getSizeY()-height;

                //for (int i=0; i=3;i++) gc.drawImage((background[i]));
                gc.drawImage(background0,0,0);
                gc.drawImage(background1,0,0);
                gc.drawImage(background2,0,0);
                gc.drawImage(background3,0,0);
                currentLevel.drawLevel(gc, offsetlandX, offsetlandY, t);
                Image playerIm = player.skin.getFrame(t);
                gc.drawImage(playerIm, (int) player.skin.getPositionX() - offsetlandX, (int) player.skin.getPositionY()-offsetlandY, playerIm.getWidth() * (player.skin.getHeight() / playerIm.getHeight()), player.skin.getHeight());
            }
        }.start();
        theStage.show();
    }
}