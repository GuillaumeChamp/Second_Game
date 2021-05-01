package image_define.Levels;

import javafx.util.Pair;
import image_define.ExtendImage.Spider;
import image_define.Level;

import java.util.ArrayList;
import java.util.Arrays;

public interface DefineLevel {
     default void modifyLevel(Level currentLevel, Integer choice) {
        switch (choice) {
            case 0:
                Level0(currentLevel);
                break;
            case 1:
                Level1(currentLevel);
                break;
            default:
                Level0(currentLevel);
                break;
        }
    }

    static void Level0(Level currentLevel) {
        currentLevel.Resize(800,600);
        String imageUrl = "resources/Level/Level_0_V1.png";
        ArrayList<Pair<Integer, Integer>> levelDescription = new ArrayList<>(Arrays.asList(
                new Pair<>(0, 250),
                new Pair<>(25, 275)
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl));
        currentLevel.setLevelDescription(levelDescription);

        currentLevel.enemies = new ArrayList<>();

        Spider spider1 = new Spider(100, 0, 20, 20, currentLevel);
        spider1.setXLimit(100, 200);
        currentLevel.enemies.add(spider1);
    }

    static void Level1(Level currentLevel) {
        currentLevel.Resize(1600,600);
        String imageUrl = "resources/Level/Level_1_fire_V1.png";
        ArrayList<Pair<Integer, Integer>> levelDescription = new ArrayList<>(Arrays.asList(
                new Pair<>(0, 250),
                new Pair<>(25, 275)
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl));
        currentLevel.setLevelDescription(levelDescription);

        currentLevel.enemies = new ArrayList<>();

        Spider spider1 = new Spider(100, 0, 20, 20, currentLevel);
        spider1.setXLimit(100, 200);
        currentLevel.enemies.add(spider1);

        Spider spider2 = new Spider(300, 0, 10, 10, currentLevel);
        spider2.setXLimit(300, 350);
        currentLevel.enemies.add(spider2);
    }
}
