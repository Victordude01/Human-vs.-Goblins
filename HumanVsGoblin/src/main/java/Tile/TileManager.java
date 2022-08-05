package main.java.Tile;

import main.java.Main.GamePanel;
import main.java.Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    boolean drawPath = true;

    public static class Tile {
        public BufferedImage image;
        public boolean collision = false;
    }

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[60];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/Maps/map1.txt");
    }



    public void getTileImage(){
        setup(0,"grass1",true);
        setup(1,"grass1",true);
        setup(2,"grass1",true);
        setup(3,"grass1",true);
        setup(4,"grass1",true);
        setup(5,"grass1",true);
        setup(6,"grass1",true);
        setup(7,"grass1",true);
        setup(8,"grass1",true);
        setup(9,"grass1",true);


        setup(10,"brick1",true);
        setup(11,"brick2",true);
        setup(12,"brick3",true);
        setup(13,"brick4",false);
        setup(14,"dirt1",false);
        setup(15,"dirt2",false);
        setup(16,"dirt3",false);
        setup(17,"dirt4",false);
        setup(18,"dirt5",false);
        setup(19,"grass1",false);
        setup(20,"grass2",false);
        setup(21,"grass3",false);
        setup(22,"grass4",false);
        setup(23,"grass5",false);
        setup(24,"sand1",true);
        setup(25,"sand2",true);
        setup(26,"sand3",true);
        setup(27,"sand4",true);
        setup(28,"sand5",true);
        setup(29,"sand6",true);
        setup(30,"sand7",true);
        setup(31,"sand8",true);
        setup(32,"tree",true);
        setup(33,"water1",true);
        setup(34,"water2",true);
        setup(35,"water3",true);
        setup(36,"water4",true);
        setup(37,"water5",true);
        setup(38,"water6",true);
        setup(39,"water7",true);
        setup(40,"water8",true);
        setup(41,"water9",true);
    }

    public void setup(int index, String imageName,boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];

            //Camera view
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //stop moving the camera at the edge
            if (gp.player.screenX > gp.player.worldX){
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY){
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > gp.worldWidth - gp.player.worldX){
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > gp.worldHeight - gp.player.worldY){
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            //only draw the pixels the character can see
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image,screenX,screenY,null);
            } else if (gp.player.screenX > gp.player.worldX || gp.player.screenY > gp.player.worldY ||
                    rightOffset > gp.worldWidth - gp.player.worldX || bottomOffset > gp.worldHeight - gp.player.worldY){
                g2.drawImage(tile[tileNum].image,screenX,screenY,null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
        //in the event you want to see the path of AI's
//        if(drawPath){
//            g2.setColor(new Color(255,0,0,70));
//            for(int i = 0; i < gp.pFinder.pathList.size();i++){
//                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
//                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
//                int screenX = worldX - gp.player.worldX + gp.player.screenX;
//                int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//                g2.fillRect(screenX,screenY,gp.tileSize,gp.tileSize);
//            }
//        }
    }
}
