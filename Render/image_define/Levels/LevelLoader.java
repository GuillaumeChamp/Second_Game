package image_define.Levels;

import image_define.ExtendImage.Spider;
import image_define.Level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
    double startX = 0;
    double endX = 0;

    public Level load(String path,int next,int secret) throws IOException {
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
        return level;
    }

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
}
