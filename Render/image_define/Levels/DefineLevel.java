package image_define.Levels;

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
            case 6:
                Level6(currentLevel);
                break;
            case 100:
                End(currentLevel);
                break;
            case 101:
                SecretLevel(currentLevel);
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
        currentLevel.addExitBlock(new Exit(750,348,50,535-348,"",1));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrl),new javafx.scene.image.Image(imageUrl));
        currentLevel.Resize();
        currentLevel.setTips("I fell from a high place");
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
        currentLevel.setTips("maybe I can try to freeze this water with my E tem");
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire),new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setIce(ice);
        currentLevel.addExitBlock(new Exit(1550,340,50,496-348,"",2));
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
        currentLevel.addExitBlock(new Exit(1550,348,50,600-348,"",3));
        currentLevel.startX = 10;

        currentLevel.enemies = new ArrayList<>();
        currentLevel.setTips("I really don't like these spiders");
        Spider spider1 = new Spider(350, 514, 40, 40);
        spider1.setXLimit(350, 720);
        currentLevel.enemies.add(spider1);
        Spider spider2 = new Spider(1100, 514, 20, 20);
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
        currentLevel.addExitBlock(new Exit(750,0,50,392,"",4));
        currentLevel.startX = 100;

        currentLevel.enemies = new ArrayList<>();
        currentLevel.setTips("I hope be able to go to home");
        Spider spider1 = new Spider(380, 1114, 40, 40);
        spider1.setXLimit(540, 800);
        spider1.setYLimit(1114,574);
        currentLevel.enemies.add(spider1);
        Spider spider2 = new Spider(47, 672, 20, 20);
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
        currentLevel.startX = 100;
        ArrayList<Water> ice = new ArrayList<>(Arrays.asList(
                new Water(229,422,786-229,490-422),
                new Water(787,422,1092-787,459-422),
                new Water(379,491,786-379,539-491)
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire),new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setIce(ice);
        currentLevel.setTips("I ... don't know where am I");
        currentLevel.addExitBlock(new Exit(1550,348,50,496-348,"",5));
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
        currentLevel.addExitBlock(new Exit(1550, 0, 50, 396,"",6));
        currentLevel.startX = 150;

        currentLevel.setTips("I think I'm  inside their nest");
        Spider spider1 = new Spider(100, 404, 20, 20);
        spider1.setXLimit(100, 225-20);
        currentLevel.enemies.add(spider1);
        Spider spider2 = new Spider(229, 221, 20, 20);
        spider2.setXLimit(229, 309);
        currentLevel.enemies.add(spider2);
        Spider spider3 = new Spider(310, 513, 20, 20);
        spider3.setXLimit(310, 385-20);
        spider3.setGroundOnly();
        currentLevel.enemies.add(spider3);
        Spider spider4 = new Spider(585, 514, 60, 60);
        spider4.setXLimit(547, 680-60);
        spider4.setGroundOnly();
        currentLevel.enemies.add(spider4);
        Spider spider5 = new Spider(842, 514, 50, 50);
        spider5.setXLimit(842, 1000-50);
        currentLevel.enemies.add(spider5);
    }
    static void Level6(Level currentLevel) {
        String imageUrlFire = "resources/Level/Level_6_fire_V1.png";
        String imageUrlIce = "resources/Level/Level_6_ice_V1.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0,404,228,600-404,""),
                new Block(229,491,378-229,600-491,""),
                new Block(379,540,787-379,600-540,""),
                new Block(787,460,913-787,600-460,""),
                new Block(976,460,1091-976,600-460,""),
                new Block(1091,541,1321-1091,600-373,""),
                new Block(1321,422,1390-1321,600-422,""),
                new Block(1390,496,1600-1390,600-496,""),
                new Block(139,0,1423-139,176,""),
                new Block(1321,0,1321-1090,351,"")
        ));
        currentLevel.setBlocks(blocks);
        currentLevel.startX = 100;
        ArrayList<Water> ice = new ArrayList<>(Arrays.asList(
                new Water(229,422,786-229,490-422),
                new Water(379,491,786-379,539-491),
                new Water(787,422,1092-787,460-422),
                new Water(914,460,975-914,600-460),
                new Water(1092,422,1320-1092,540-422)
        ));
        currentLevel.addBreakable(new Block(1321,352,1391-1321,422-352,"breakable"));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire),new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setIce(ice);
        currentLevel.setTips("Is that a rink ?");
        currentLevel.addExitBlock(new Exit(1550,348,50,496-348,"",100));
        currentLevel.addExitBlock(new Exit(914,550,60,50,"",101));
        currentLevel.Resize();
        Spider spider1 = new Spider(390, 540, 20, 20);
        spider1.setXLimit(390, 775-20);
        currentLevel.enemies.add(spider1);

    }
    static void SecretLevel(Level currentLevel){
        String imageUrlFire = "resources/Level/Secret_Level_A.png";
        String imageUrlIce = "resources/Level/Secret_Level.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0, 0, 198, 600, ""),
                new Block(0, 514, 800, 600 - 514, ""),
                new Block(600, 0, 200, 443, "")
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire), new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setTips("I'm not suppose to be here");
        currentLevel.Resize();
        currentLevel.setBlocks(blocks);
        currentLevel.addExitBlock(new Exit(750, 443, 50, 80,"",6));
        currentLevel.startX = 220;
    }
    static void End(Level currentLevel){
        String imageUrlFire = "resources/Level/Level_end_V1.png";
        String imageUrlIce = "resources/Level/Level_end_V1.png";
        ArrayList<Block> blocks = new ArrayList<>(Arrays.asList(
                new Block(0, 432, 299, 600-432, ""),
                new Block(299, 398, 374-299, 600 - 398, ""),
                new Block(374, 365, 523-374, 600-374, ""),
                new Block(532, 334, 800-532, 600-532, "")
        ));
        currentLevel.setBackground(new javafx.scene.image.Image(imageUrlFire), new javafx.scene.image.Image(imageUrlIce));
        currentLevel.setTips("Finally, my house is near");
        currentLevel.Resize();
        currentLevel.setBlocks(blocks);
        currentLevel.addExitBlock(new Exit(780, 300, 20, 80,"",100));
        currentLevel.endGame = true;
        currentLevel.startX = 10;
    }
}