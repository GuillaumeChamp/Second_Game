import java.util.ArrayList;
import characters.Player;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import image_define.Level;


public class Render extends Application {
    final long width = 800; //width of the window
    final long height = 600; //height of the window
    SoundBackground music= new SoundBackground();
    Integer currentLevelNum = 5;
    private Scene menuScene;
    private Scene theScene;
    long startNanoTime;
    AnimationTimer mainGameLoopTimer;

    private void defineMainMenu(Stage theStage) {
        //TODO : make the start menu
        Label label1 = new Label("Main Menu");
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            theStage.setScene(theScene);
            startNanoTime = System.nanoTime();
            mainGameLoopTimer.start();
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> theStage.close());
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, startButton, exitButton);
        menuScene = new Scene(layout1, 200, 200);
    }

    private void defineGameLoop(Stage theStage) {
        ArrayList<KeyCode> input = new ArrayList<>(); //store the keyboard input

        Group root = new Group();
        theScene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Level currentLevel = new Level(width, height);
        currentLevel.modifyLevel(currentLevel, currentLevelNum);
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
        Image background0 = new Image( "resources/Level/Background/CloudsBack.png" );
        Image background1 = new Image( "resources/Level/Background/CloudsFront.png" );
        Image background2 = new Image( "resources/Level/Background/BGBack.png" );
        Image background3 = new Image( "resources/Level/Background/BGFront.png" );

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


                if (player.nextLevel) {
                    currentLevelNum = (currentLevelNum + 1);
                    player.location.clear();
                    player.nextLevel = false;
                    currentLevel.modifyLevel(currentLevel, currentLevelNum);
                    player.skin.setPosition(currentLevel.startX, player.skin.currentBlock(player.location, 0,player.skin.getPositionY()).getBlock().getMinY()-1);
                }

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
        defineGameLoop(theStage);
        
        theStage.setScene(menuScene);
        theStage.show();
    }
}