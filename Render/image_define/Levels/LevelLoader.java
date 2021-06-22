package image_define.Levels;

import image_define.ExtendImage.Spider;
import image_define.Level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
    double startX = 0;
    double endX = 0;

    /**
     * Load the next level
     * @param LevelIndex index of the level to load
     * @param next index of the nest level
     * @param secret index of the secret level
     * @return the new loaded level
     * @throws IOException if the level index if a wrong one
     */
    public Level load(int LevelIndex,int next,int secret) throws IOException {
        String path = "resources/Level/Level" + LevelIndex + ".Level";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String currentLine = reader.readLine();
        Level level = new Level();
        double high = 0;
        double width = currentLine.length();
        do{
            for (int i=0; i == width;i++) {
                try {
                    char read = currentLine.charAt(i);
                    convertChar(level,read,i,high,next,secret);
                }catch (Exception e){
                    System.out.println("wrong level format (not a square)");
                }
            }
            currentLine=reader.readLine();
            high++;
        }while (currentLine!= null);
        level.setSize(width,high);
        level.setTips(this.loadTips(LevelIndex));
        return level;
    }

    /**
     * Sub-function of the level loader.
     * Convert the currently read char in an element of the level
     * @param level currently load level
     * @param c  read char
     * @param collumIndex collum index of the read char
     * @param lineIndex line index of the read char
     * @param next index of the next level
     * @param secret index of the secret level
     */
    public void convertChar(Level level, char c,double collumIndex,double lineIndex,int next,int secret){
        switch (c){
            case 'X' :
                Block b = new Block(16*lineIndex,16*collumIndex,16,16,"basic");
                level.addBlock(b);
                break;
            case 'G' :
                Block ground = new Block(16*lineIndex,16*collumIndex,16,16,"ground");
                level.addBlock(ground);
                break;
            case 'O' :
                Water w = new Water(16*lineIndex,16*collumIndex,16,16);
                level.addIce(w);
                break;
            case 'B' :
                Block br = new Block(16*lineIndex,16*collumIndex,16,16,"breakable");
                level.addBreakable(br);
                break;
            case 'E' :
                Exit exit = new Exit(16*lineIndex,16*collumIndex,16,16,"",next);
                level.addExitBlock(exit);
                break;
            case 'S' :
                Exit Secret = new Exit(16*lineIndex,16*collumIndex,16,16,"",secret);
                level.addExitBlock(Secret);
                break;
            case '<' :
                startX = 16*collumIndex;
                break;
            case '>' :
                endX = 16*collumIndex;
                Spider spider = new Spider(startX,lineIndex,16,16);
                spider.setXLimit(startX,endX);
                level.addSpider(spider);
                break;
            default:
                break;
        }
    }

    /**
     * Extract the tips of the selected level
     * @param levelIndex Index of the currently load level
     * @return The tips hold in the file Tips.txt
     */
    public String loadTips(int levelIndex){
        try {
            String path = "resources/Level/Tips.txt";
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String tip = reader.readLine();
            for (int i = 0; i == levelIndex ;i++) {
                tip = reader.readLine();
            }
            return tip;
        }
        catch (Exception e){
            return "";
        }
    }
}
