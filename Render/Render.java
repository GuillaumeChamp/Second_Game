import java.util.ArrayList;
import characters.Player;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import image_define.Level;


public class Render extends Application {
    final long width = 800; //width of the window
    final long height = 600; //height of the window
    SoundBackground music= new SoundBackground();
    Integer currentLevelNum = 0;
    //DefineLevel defineLevel = new DefineLevel();

    public void start(Stage theStage) {
        theStage.setTitle("stony journey");
        music.start(theStage);
        ArrayList<String> input = new ArrayList<>(); //store the keyboard input

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        final long startNanoTime = System.nanoTime();

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Level currentLevel = new Level(width, height);
        currentLevel.modifyLevel(currentLevel, currentLevelNum);
        currentLevelNum += 1;
        Player player = new Player(10,0,40,60, 40, currentLevel);
        theScene.setOnKeyPressed(e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code))
                        input.add(code);
                    if (code.equals("R")) System.out.println(player.skin.getPositionX() + player.skin.getPositionY());
                }
        );

        theScene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
        Image background0 = new Image( "resources/Level/Background/CloudsBack.png" );
        Image background1 = new Image( "resources/Level/Background/CloudsFront.png" );
        Image background2 = new Image( "resources/Level/Background/BGBack.png" );
        Image background3 = new Image( "resources/Level/Background/BGFront.png" );
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                player.skin.setForceX(0);
                player.skin.setForceY(0);
                if (input.contains("LEFT")) {
                    player.skin.addForces(-10,0);
                }
                if (input.contains("RIGHT")) {
                    player.skin.addForces(10, 0);
                }
                if (input.contains("UP")) {
                    //System.out.println(player.skin.getPositionY());
                    if (player.skin.getPositionY() > (currentLevel.getGround(player.skin.getPositionX()) - 1.5*player.skin.getHeight())) {
                        player.skin.addForces(0, -10);
                    }
                }
                if (input.contains("S")) {
                    currentLevel.setGroundOnly();
                    input.remove("S");
                }
                player.updateSkin(t);
                currentLevel.updateLevel(t);

                if (player.nextLevel) {
                    currentLevel.modifyLevel(currentLevel, currentLevelNum);
                    currentLevelNum = (currentLevelNum + 1) % 2;
                    player.nextLevel = false;
                    player.skin.setPosition(0, currentLevel.getGround(0.0));
                }

                double offsetlandX = player.skin.getPositionX() - (width >> 1);
                double offsetlandY = player.skin.getPositionY() - (height >> 1);
                if (offsetlandX < 0) offsetlandX = 0;
                if (offsetlandX > currentLevel.getSizeX()-800) offsetlandX = currentLevel.getSizeX()-width;
                if (offsetlandY < 0) offsetlandY = 0;
                if (offsetlandY > currentLevel.getSizeY()-600) offsetlandY = currentLevel.getSizeY()-height;

                // background image clears canvas;
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