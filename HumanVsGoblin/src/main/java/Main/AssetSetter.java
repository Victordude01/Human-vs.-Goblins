package main.java.Main;

import main.java.Entity.easyGoblin;
import main.java.Entity.hardGoblin;
import main.java.Entity.mediumGoblin;
import main.java.Object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[1] = new Chest(gp);
        gp.obj[1].worldX = 28 * gp.tileSize;
        gp.obj[1].worldY = 31 * gp.tileSize;

        gp.obj[2] = new Chest(gp);
        gp.obj[2].worldX = 47 * gp.tileSize;
        gp.obj[2].worldY = 13 * gp.tileSize;

        gp.obj[3] = new Chest(gp);
        gp.obj[3].worldX = 47 * gp.tileSize;
        gp.obj[3].worldY = 35 * gp.tileSize;

        gp.obj[4] = new Chest(gp);
        gp.obj[4].worldX = 19 * gp.tileSize;
        gp.obj[4].worldY = 13 * gp.tileSize;
//
//        gp.obj[4] = new Boots(gp);
//        gp.obj[4].worldX = 36 * gp.tileSize;
//        gp.obj[4].worldY = 41 * gp.tileSize;
//
//        gp.obj[5] = new Boots(gp);
//        gp.obj[5].worldX = 39 * gp.tileSize;
//        gp.obj[5].worldY = 8 * gp.tileSize;

        gp.obj[5] = new Blue_Shield(gp);
        gp.obj[5].worldX = 48 * gp.tileSize;
        gp.obj[5].worldY = 32 * gp.tileSize;

        gp.obj[6] = new Red_Potion(gp);
        gp.obj[6].worldX = 11 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;
//
//        gp.obj[7] = new Axe(gp);
//        gp.obj[7].worldX = 21 * gp.tileSize;
//        gp.obj[7].worldY = 21 * gp.tileSize;
//
//        gp.obj[8] = new Potion_Red(gp);
//        gp.obj[8].worldX = 22 * gp.tileSize;
//        gp.obj[8].worldY = 24 * gp.tileSize;
//
//        gp.obj[9] = new Bow(gp);
//        gp.obj[9].worldX = 24 * gp.tileSize;
//        gp.obj[9].worldY = 24 * gp.tileSize;
    }

    public void setGoblin(){
        gp.goblin[0] = new easyGoblin(gp);
        gp.goblin[0].worldX = 24 * gp.tileSize;
        gp.goblin[0].worldY = 20 * gp.tileSize;

        gp.goblin[1] = new mediumGoblin(gp);
        gp.goblin[1].worldX = 19 * gp.tileSize;
        gp.goblin[1].worldY = 14 * gp.tileSize;

        gp.goblin[2] = new hardGoblin(gp);
        gp.goblin[2].worldX = 14 * gp.tileSize;
        gp.goblin[2].worldY = 40 * gp.tileSize;

        gp.goblin[3] = new easyGoblin(gp);
        gp.goblin[3].worldX = 14 * gp.tileSize;
        gp.goblin[3].worldY = 20 * gp.tileSize;

        gp.goblin[4] = new easyGoblin(gp);
        gp.goblin[4].worldX = 15 * gp.tileSize;
        gp.goblin[4].worldY = 9 * gp.tileSize;

        gp.goblin[5] = new mediumGoblin(gp);
        gp.goblin[5].worldX = 35 * gp.tileSize;
        gp.goblin[5].worldY = 9 * gp.tileSize;

        gp.goblin[6] = new easyGoblin(gp);
        gp.goblin[6].worldX = 50 * gp.tileSize;
        gp.goblin[6].worldY = 7 * gp.tileSize;

        gp.goblin[7] = new hardGoblin(gp);
        gp.goblin[7].worldX = 46 * gp.tileSize;
        gp.goblin[7].worldY = 15 * gp.tileSize;

        gp.goblin[8] = new easyGoblin(gp);
        gp.goblin[8].worldX = 35 * gp.tileSize;
        gp.goblin[8].worldY = 16 * gp.tileSize;

        gp.goblin[9] = new easyGoblin(gp);
        gp.goblin[9].worldX = 45 * gp.tileSize;
        gp.goblin[9].worldY = 17 * gp.tileSize;

        gp.goblin[10] = new mediumGoblin(gp);
        gp.goblin[10].worldX = 21 * gp.tileSize;
        gp.goblin[10].worldY = 33 * gp.tileSize;

        gp.goblin[11] = new easyGoblin(gp);
        gp.goblin[11].worldX = 26 * gp.tileSize;
        gp.goblin[11].worldY = 35 * gp.tileSize;

        gp.goblin[12] = new mediumGoblin(gp);
        gp.goblin[12].worldX = 43 * gp.tileSize;
        gp.goblin[12].worldY = 36 * gp.tileSize;

        gp.goblin[13] = new easyGoblin(gp);
        gp.goblin[13].worldX = 44 * gp.tileSize;
        gp.goblin[13].worldY = 31 * gp.tileSize;

        gp.goblin[14] = new hardGoblin(gp);
        gp.goblin[14].worldX = 26 * gp.tileSize;
        gp.goblin[14].worldY = 42 * gp.tileSize;

        gp.goblin[15] = new easyGoblin(gp);
        gp.goblin[15].worldX = 41 * gp.tileSize;
        gp.goblin[15].worldY = 42 * gp.tileSize;

        gp.goblin[16] = new mediumGoblin(gp);
        gp.goblin[16].worldX = 52 * gp.tileSize;
        gp.goblin[16].worldY = 36 * gp.tileSize;

        gp.goblin[17] = new easyGoblin(gp);
        gp.goblin[17].worldX = 53 * gp.tileSize;
        gp.goblin[17].worldY = 15 * gp.tileSize;
    }
}
