package image_define.Levels;

import javafx.geometry.Rectangle2D;
import image_define.ExtendImage.Spider;
import image_define.Level;

import java.util.ArrayList;
import java.util.Arrays;

//TODO : make a level loader from files

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
                Level1(currentLevel);
                break;
            case 2:
                Level2(currentLevel);
                break;
            case 3:
                Level3(currentLevel);
                break;
            case 4:
                Level4(currentLevel);
                break;
            case 5:
                Level5(currentLevel);
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
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(205,436,277-205,600-436,"fire"),
                new Block(0,10,205,600-10,"fire"),
                new Block(277,468,426-277,600-468,"fire"),
                new Block(426,501,500-426,600-501,"fire"),
                new Block(501,535,800-501,600-535,"fire")
        ));
        currentLevel.setBlocks(blocks);
        currentLevel.setExitBlock(new Rectangle2D(750,348,50,535-348));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl),new javafx.scene.image.Image(imageUrl));
        currentLevel.Resize();
        currentLevel.setTips("go to the right");
        currentLevel.enemies = new ArrayList<>();
    }
    /**
     * build the level1 including spiders and collision
     * @param currentLevel active level which will be update to become level1
     */
    static void Level1(Level currentLevel) {
        String imageUrlFire = "resources/Level/Level_1_fire_V1.png";
        String imageUrlIce = "resources/Level/Level_1_ice_V1.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0,535,113,600-265,""),
                new Block(113,510,226-113,600-510,""),
                new Block(226,491,564-226,600-491,""),
                new Block(564,536,694-564,600-536,""),
                new Block(694,497,808-674,600-497,""),
                new Block(808,471,921-808,600-471,""),
                new Block(921,453,1270-921,600-453,""),
                new Block(1270,491,1371-1270,600-491,""),
                new Block(1371,405,1600-1371,600-405,"")
        ));
        currentLevel.setBlocks(blocks);

        ArrayList<Water> ice = new ArrayList<>(Arrays.asList(
                new Water(565,508,694-565,600-508),
                new Water(1271,453,1370-1271,490-453)
        ));
        currentLevel.setTips("press c to freeze the water");
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire),new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setIce(ice);
        currentLevel.setExitBlock(new Rectangle2D(1550,340,50,496-348));
        currentLevel.Resize();
        currentLevel.enemies = new ArrayList<>();

    }

    static void Level2(Level currentLevel) {
        String imageUrlFire = "resources/Level/Level_2_fire_V1.png";
        String imageUrlIce = "resources/Level/Level_2_ice_V1.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0,404,228,600-404,""),
                new Block(228,515,1270-228,600-515,""),
                new Block(1268,348,1600-1268,600-348,""),
                new Block(352,361,682-352,452-361,""),
                new Block(900,132,1160-900,269-132,""),
                new Block(1160,0,1329-1160,149,"")
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire),new javafx.scene.image.Image(imageUrlIce));
        currentLevel.Resize();
        currentLevel.setBlocks(blocks);
        currentLevel.setExitBlock(new Rectangle2D(1550,348,50,600-348));
        currentLevel.startX = 150;

        currentLevel.enemies = new ArrayList<>();
        currentLevel.setTips("pressing c make also spider go up with web");
        Spider spider1 = new Spider(350, 514, 40, 40, currentLevel);
        spider1.setXLimit(350, 720);
        currentLevel.enemies.add(spider1);
        Spider spider2 = new Spider(1100, 514, 20, 20, currentLevel);
        spider2.setXLimit(1100, 1220);
        currentLevel.enemies.add(spider2);

    }
    static void Level3(Level currentLevel) {
        String imageUrlFire = "resources/Level/Level_3_fire_V1.png";
        String imageUrlIce = "resources/Level/Level_3_ice_V1.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0,948,331,1200-948,""),
                new Block(330,1115,800-330,1200-1115,""),
                new Block(13,676,631-13,844-676,""),
                new Block(461,392,800-461,583-392,""),
                new Block(47,0,630-47,186,""),
                new Block(169,392,461-169,558-392,"")
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire),new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setBlocks(blocks);
        currentLevel.setExitBlock(new Rectangle2D(750,0,50,392));
        currentLevel.setRatio(2);
        //adding start x position
        currentLevel.startX = 100;

        currentLevel.enemies = new ArrayList<>();
        currentLevel.setTips("you should try to think by yourself now");
        Spider spider1 = new Spider(380, 1114, 40, 40, currentLevel);
        spider1.setXLimit(380, 600);
        spider1.setYLimit(1114,574);
        currentLevel.enemies.add(spider1);
        Spider spider2 = new Spider(47, 672, 20, 20, currentLevel);
        spider2.setXLimit(47, 630-47);
        spider2.setYLimit(672,180);
        currentLevel.enemies.add(spider2);
        currentLevel.Resize();
    }
    static void Level4(Level currentLevel) {
        String imageUrlFire = "resources/Level/Level_4_fire_V1.png";
        String imageUrlIce = "resources/Level/Level_4_ice_V1.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0,404,228,600-404,""),
                new Block(229,491,378-113,600-491,""),
                new Block(379,540,787-379,600-540,""),
                new Block(787,460,1093-787,600-460,""),
                new Block(1093,373,1387-1093,600-373,""),
                new Block(1388,496,1600-1388,600-496,""),
                new Block(139,0,1423-139,176,"")
        ));
        currentLevel.setBlocks(blocks);

        ArrayList<Water> ice = new ArrayList<>(Arrays.asList(
                new Water(229,422,786-229,490-422),
                new Water(787,422,1092-787,459-422),
                new Water(379,491,786-379,539-491)
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire),new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setIce(ice);
        currentLevel.setTips("");
        currentLevel.setExitBlock(new Rectangle2D(1550,348,50,496-348));
        currentLevel.Resize();
        currentLevel.enemies = new ArrayList<>();

    }
    static void Level5(Level currentLevel) {
        String imageUrlFire = "resources/Level/Level_5_fire_V1.png";
        String imageUrlIce = "resources/Level/Level_5_ice_V1.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0, 404, 228, 600 - 404, ""),
                new Block(229, 221, 308 - 229, 600 - 221, ""),
                new Block(308, 515, 1268 - 308, 600 - 515, ""),
                new Block(387, 382, 546 - 387, 515 - 382, ""),
                new Block(680, 336, 839 - 680, 515 - 336, ""),
                new Block(1002, 191, 1205 - 1002, 515-191, ""),
                new Block(1268, 396, 1600 - 1268, 600-396, ""),
                new Block(181, 0, 1450 - 181, 119, "")
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire), new javafx.scene.image.Image(imageUrlIce));
        currentLevel.Resize();
        currentLevel.setBlocks(blocks);
        currentLevel.setExitBlock(new Rectangle2D(1550, 0, 50, 396));
        currentLevel.startX = 150;

        currentLevel.enemies = new ArrayList<>();
        currentLevel.setTips("pressing c make also spider go up with web");
        Spider spider1 = new Spider(350, 514, 40, 40, currentLevel);
        spider1.setXLimit(350, 720);
        currentLevel.enemies.add(spider1);
        Spider spider2 = new Spider(1100, 514, 20, 20, currentLevel);
        spider2.setXLimit(1100, 1220);
        currentLevel.enemies.add(spider2);
    }
}