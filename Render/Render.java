import java.io.IOException;
import java.util.ArrayList;
import characters.Player;
import image_define.Levels.LevelLoader;
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
    private Scene endScene;
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
            //mainGameLoopTimer.start();
        });
        startButton.setLayoutX((width >> 1) - 10);
        startButton.setLayoutY((height >> 1) - 100);
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> theStage.close());
        exitButton.setLayoutX((width >> 1) - 10);
        exitButton.setLayoutY((height >> 1) - 50);
        Label controlInfo = new Label("R: restart the current level\nE: switch between ice/fire\nD: go right\nQ: go left\nZ: go upward");
        controlInfo.setLayoutX((width * 2) / 5);
        controlInfo.setLayoutY(height >> 1);
        Label creditInfo = new Label("credit: music is from TwinMusicom\n");
        creditInfo.setLayoutX((width * 2) / 5);
        creditInfo.setLayoutY(height - 30);

        Group root = new Group();
        menuScene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        root.getChildren().add(startButton);
        root.getChildren().add(exitButton);
        root.getChildren().add(controlInfo);
        root.getChildren().add(creditInfo);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background0,0,0);
        gc.drawImage(background1,0,0);
        gc.drawImage(background2,0,0);
        gc.drawImage(background3,0,0);
    }

    private void defineEnd(Stage theStage) {
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> theStage.close());
        exitButton.setLayoutX(width/6*5);
        exitButton.setLayoutY(height/7*6);

        Group root = new Group();
        endScene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        root.getChildren().add(exitButton);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background0,0,0);
        gc.drawImage(background1,0,0);
        gc.drawImage(background2,0,0);
        gc.drawImage(background3,0,0);
        gc.drawImage(new Image( "resources/player/home.png" ), width/5, height/4, width/5*3, height/3*2);
    }

    private void defineGameLoop(Stage theStage) {
        ArrayList<KeyCode> input = new ArrayList<>(); //store the keyboard input
        LevelLoader levelLoader = new LevelLoader();
        Group root = new Group();
        theScene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Level currentLevel = null;
        try {
            currentLevel = levelLoader.load(1);

            currentLevel.modifyLevel(currentLevel, 0);//todo : delete
            Player player = new Player(210, 436, 30, 60, 40, currentLevel);
            Level finalCurrentLevel = currentLevel;
            theScene.setOnKeyPressed(e -> {
                        KeyCode code = e.getCode();
                        if (!input.contains(code))
                            input.add(code);
                        if (code == KeyCode.E) {
                            finalCurrentLevel.swap();
                        }
                        if (code == KeyCode.R) player.skin.setPosition(finalCurrentLevel.startX, finalCurrentLevel.startY);

                    }
            );

            theScene.setOnKeyReleased(e -> {
                KeyCode code = e.getCode();
                input.remove(code);
            });

            Level finalCurrentLevel1 = currentLevel;
            mainGameLoopTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                    player.skin.setForceX(0);
                    player.skin.setForceY(0);
                    if (input.contains(KeyCode.Q)) {
                        player.skin.addForces(-10, 0);
                    }
                    if (input.contains(KeyCode.D)) {
                        player.skin.addForces(10, 0);
                    }
                    if (input.contains(KeyCode.Z)) {
                        if (player.CanJump() || player.location.ThereIsALadder(player.skin.getPositionX(), player.skin.getPositionY()))
                            player.skin.addForces(0, -20);
                    }
                    finalCurrentLevel1.updateLevel(t);
                    Boolean shouldEndGame = player.updateSkin();
                    if (shouldEndGame) {
                        theStage.setScene(endScene);
                        this.stop();
                    }
                    mainGameLoopTimer.start();

                    double OffSetLandX = player.skin.getPositionX() - (width >> 1);
                    double OffSetLandY = player.skin.getPositionY() - (height >> 1);
                    if (OffSetLandX < 0) OffSetLandX = 0;
                    if (OffSetLandX > finalCurrentLevel1.getSizeX() - width) OffSetLandX = finalCurrentLevel1.getSizeX() - width;
                    if (OffSetLandY < 0) OffSetLandY = 0;
                    if (OffSetLandY > finalCurrentLevel1.getSizeY() - height) OffSetLandY = finalCurrentLevel1.getSizeY() - height;

                    gc.drawImage(background0, 0, 0);
                    gc.drawImage(background1, 0, 0);
                    gc.drawImage(background2, 0, 0);
                    gc.drawImage(background3, 0, 0);
                    finalCurrentLevel1.drawLevel(gc, OffSetLandX, OffSetLandY, t);
                    Image playerIm = player.skin.getFrame(t);
                    gc.drawImage(playerIm, (int) player.skin.getPositionX() - OffSetLandX, (int) player.skin.getPositionY() - player.skin.getHeight() - OffSetLandY, playerIm.getWidth() * (player.skin.getHeight() / playerIm.getHeight()), player.skin.getHeight());
                }
            };
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start(Stage theStage) {
        theStage.setTitle("Stony Journey");
        music.start(theStage);

        defineMainMenu(theStage);
        defineEnd(theStage);
        defineGameLoop(theStage);
        
        theStage.setScene(menuScene);
        theStage.show();
    }
}