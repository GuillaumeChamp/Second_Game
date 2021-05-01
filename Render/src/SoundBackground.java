import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class SoundBackground extends Application implements Runnable {
    final File file = new File("Render/src/resources/TwinMusicom-8-bit-March.mp3");
    final Media media = new Media(file.toURI().toString());
    final MediaPlayer music = new MediaPlayer(media);

    @Override
    public void run() {
        music.setCycleCount(100);
        music.setVolume(music.getVolume()/8);
        music.setOnReady(()-> {
            music.play();
            music.setOnEndOfMedia(music::play);
        });
    }

    @Override
    public void start(Stage primaryStage){
        this.run();
    }
}