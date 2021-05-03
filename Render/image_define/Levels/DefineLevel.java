package image_define.Levels;

import javafx.util.Pair;
import image_define.ExtendImage.Spider;
import image_define.Level;

import java.util.ArrayList;
import java.util.Arrays;

public interface DefineLevel {
    /**
     * Swap the currentLevel to the next level
     * @param currentLevel Active level (might be modify to be include in Level.java)
     * @param choice id of the next level
     */
    default void modifyLevel(Level currentLevel, Integer choice) {
        switch (choice) {
            case 0:
                LevelEntry(currentLevel);
                break;
            case 1:
                LevelEntry(currentLevel);
                break;
            case 2:
                Level1_fire(currentLevel);
                break;
            case 3:
                Level1_ice(currentLevel);
                break;
            default:
                LevelEntry(currentLevel);
                break;
        }
    }

    /**
     * build the level0 including spiders and collision
     * @param currentLevel active level which will be update to become level0
     */
    static void LevelEntry(Level currentLevel) {
        String imageUrl = "resources/Level/Level_0_V1.png";

        //Define the ground of the level
        ArrayList<Pair<Integer, Integer>> levelDescription = new ArrayList<>(Arrays.asList(
                new Pair<>(0, 30),
                new Pair<>(205,436),
                new Pair<>(277,467),
                new Pair<>(426,500),
                new Pair<>(501,534)
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl));
        currentLevel.setLevelDescription(levelDescription);
        currentLevel.Resize();
        currentLevel.enemies = new ArrayList<>();
    }

    /**
     * build the level1 including spiders and collision
     * @param currentLevel active level which will be update to become level1
     */
    static void Level1_fire(Level currentLevel) {
        String imageUrl = "resources/Level/Level_1_fire_V1.png";
        ArrayList<Pair<Integer, Integer>> levelDescription = new ArrayList<>(Arrays.asList(
                new Pair<>(0, 535),
                new Pair<>(113, 509),
                new Pair<>(226,490),
                new Pair<>(564,535),//bottom of the lake 1
                new Pair<>(694,496),
                new Pair<>(808,470),
                new Pair<>(921,451),
                new Pair<>(1271,490),//bottom of the lake 2
                new Pair<>(1371,403)
        ));
        ArrayList<Integer> iceDescription = new ArrayList<>(Arrays.asList(
                564, 1271
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl));
        currentLevel.setLevelDescription(levelDescription);
        currentLevel.setIceDescription(iceDescription);
        currentLevel.Resize();
        currentLevel.enemies = new ArrayList<>();

        Spider spider1 = new Spider(100, 0, 20, 20, currentLevel);
        spider1.setXLimit(100, 200);
        currentLevel.enemies.add(spider1);

    }

    /**
     * build the level1 including spiders and collision
     * @param currentLevel active level which will be update to become level1
     */
    static void Level1_ice(Level currentLevel) {
        String imageUrl = "resources/Level/Level_1_ice_V1.png";
        ArrayList<Pair<Integer, Integer>> levelDescription = new ArrayList<>(Arrays.asList(
                new Pair<>(0, 535),
                new Pair<>(113, 509),
                new Pair<>(226,490),
                new Pair<>(564,510),//bottom of the lake 1
                new Pair<>(694,496),
                new Pair<>(808,470),
                new Pair<>(921,451),
                new Pair<>(1371,403)
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl));
        currentLevel.setLevelDescription(levelDescription);
        currentLevel.Resize();
        currentLevel.enemies = new ArrayList<>();

        Spider spider1 = new Spider(100, 0, 20, 20, currentLevel);
        spider1.setXLimit(100, 200);
        currentLevel.enemies.add(spider1);

    }

    static void Level2_fire(Level currentLevel) {
        String imageUrl = "resources/Level/Level_2_fire_V1.png";
        ArrayList<Pair<Integer, Integer>> levelDescription = new ArrayList<>(Arrays.asList(
                new Pair<>(0, 403),
                new Pair<>(230, 514),
                new Pair<>(1268,347)
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl));
        currentLevel.setLevelDescription(levelDescription);

        currentLevel.enemies = new ArrayList<>();

        Spider spider1 = new Spider(100, 0, 20, 20, currentLevel);
        spider1.setXLimit(100, 200);
        currentLevel.enemies.add(spider1);
    }

    static void Level2_ice(Level currentLevel) {
        String imageUrl = "resources/Level/Level_2_ice_V1.png";
        ArrayList<Pair<Integer, Integer>> levelDescription = new ArrayList<>(Arrays.asList(
                new Pair<>(0, 403),
                new Pair<>(230, 514),
                new Pair<>(1268,347)
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