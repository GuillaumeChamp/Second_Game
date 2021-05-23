import java.util.ArrayList;
import characters.Player;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import image_define.Level;


public class Render extends Application {
    final long width = 800; //width of the window
    final long height = 600; //height of the window
    SoundBackground music= new SoundBackground();
    private Scene menuScene;
    private Scene theScene;

    long startNanoTime;

    AnimationTimer mainGameLoopTimer;

    Image background0 = new Image( "resources/Level/Background/CloudsBack.png" );
    Image background1 = new Image( "resources/Level/Background/CloudsFront.png" );
    Image background2 = new Image( "resources/Level/Background/BGBack.png" );
    Image background3 = new Image( "resources/Level/Background/BGFront.png" );

    private void defineMainMenu(Stage theStage) {
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            theStage.setScene(theScene);
            startNanoTime = System.nanoTime();
            mainGameLoopTimer.start();
        });
        startButton.setLayoutX((width >> 1) - 10);
        startButton.setLayoutY((height >> 1) - 100);
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> theStage.close());
        exitButton.setLayoutX((width >> 1) - 10);
        exitButton.setLayoutY((height >> 1) - 50);
        Label controlInfo = new Label("H: restart the current level\nE: switch between ice/fire\nD: go right\nQ: go left\nZ: go upward");
        controlInfo.setLayoutX((width * 2) / 5);
        controlInfo.setLayoutY(height >> 1);

        Group root = new Group();
        menuScene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        root.getChildren().add(startButton);
        root.getChildren().add(exitButton);
        root.getChildren().add(controlInfo);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background0,0,0);
        gc.drawImage(background1,0,0);
        gc.drawImage(background2,0,0);
        gc.drawImage(background3,0,0);
    }

    private void defineGameLoop() {
        ArrayList<KeyCode> input = new ArrayList<>(); //store the keyboard input

        Group root = new Group();
        theScene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Level currentLevel = new Level(width, height);
        currentLevel.modifyLevel(currentLevel, 0);
        Player player = new Player(210,436,30,60, 40, currentLevel);
        theScene.setOnKeyPressed(e -> {
                    KeyCode code = e.getCode();
                    if (!input.contains(code))
                        input.add(code);
                    if (code==KeyCode.E){
                        currentLevel.swap();
                    }
                    if(code==KeyCode.R) System.out.println(player.skin.getPositionX() + " " + player.skin.getPositionY());
                }
        );

        theScene.setOnKeyReleased(e -> {
            KeyCode code = e.getCode();
            input.remove(code);
        });

        mainGameLoopTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                player.skin.setForceX(0);
                player.skin.setForceY(0);
                if (input.contains(KeyCode.Q)) {
                    player.skin.addForces(-10,0);
                }
                if (input.contains(KeyCode.D)) {
                    player.skin.addForces(10, 0);
                }
                if (input.contains(KeyCode.Z)) {
                    if (player.CanJump()||player.location.ThereIsALadder(player.skin.getPositionX(),player.skin.getPositionY())) player.skin.addForces(0, -20);
                }
                // H is the help key
                if (input.contains(KeyCode.H)) {
                    player.skin.setPosition(currentLevel.startX, player.skin.currentBlock(player.location, currentLevel.startX,player.skin.getPositionY()).getBlock().getMinY()-1);
                }
                currentLevel.updateLevel(t);
                player.updateSkin();

                double OffSetLandX = player.skin.getPositionX() - (width >> 1);
                double OffSetLandY = player.skin.getPositionY() - (height >> 1);
                if (OffSetLandX < 0) OffSetLandX = 0;
                if (OffSetLandX > currentLevel.getSizeX()-width) OffSetLandX = currentLevel.getSizeX()-width;
                if (OffSetLandY < 0) OffSetLandY = 0;
                if (OffSetLandY > currentLevel.getSizeY()-height) OffSetLandY = currentLevel.getSizeY()-height;

                gc.drawImage(background0,0,0);
                gc.drawImage(background1,0,0);
                gc.drawImage(background2,0,0);
                gc.drawImage(background3,0,0);
                currentLevel.drawLevel(gc, OffSetLandX, OffSetLandY, t);
                Image playerIm = player.skin.getFrame(t);
                gc.drawImage(playerIm, (int) player.skin.getPositionX() - OffSetLandX, (int) player.skin.getPositionY()-player.skin.getHeight()-OffSetLandY, playerIm.getWidth() * (player.skin.getHeight() / playerIm.getHeight()), player.skin.getHeight());
            }
        };
    }

    public void start(Stage theStage) {
        theStage.setTitle("Stony Journey");
        music.start(theStage);

        defineMainMenu(theStage);
        defineGameLoop();
        
        theStage.setScene(menuScene);
        theStage.show();
    }
}