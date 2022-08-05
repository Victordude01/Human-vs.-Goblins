package main.java.Entity;

import main.java.Main.GamePanel;
import main.java.Object.*;

import java.util.Random;

public class hardGoblin extends Entity {
    public hardGoblin(GamePanel gp) {
        super(gp);
        type = 1;
        name = "hard Goblin";
        speed = 2;
        maxLife = 40;
        life = maxLife;
        strength = 10;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }



    public void getImage(){
        up1 = setup("/Goblin/hardup2");
        up2 = setup("/Goblin/hardup3");
        down1 = setup("/Goblin/harddown2");
        down2 = setup("/Goblin/harddown3");
        left1 = setup("/Goblin/hardleft1");
        left2 = setup("/Goblin/hardleft2");
        right1 = setup("/Goblin/hardright1");
        right2 = setup("/Goblin/hardright2");
    }

    public void setAction() {
        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol,goalRow);
        }else {
            actionLockCounter ++;

            if (actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100)+1;

                if (i <= 25){
                    direction = "up";
                } else if (i > 25 && i <= 50) {
                    direction = "down";
                } else if (i > 50 && i <= 75) {
                    direction = "left";
                }else{
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void update(){
        super.update();

        int xDistance = Math.abs(worldX-gp.player.worldX);
        int yDistance = Math.abs(worldY-gp.player.worldY);
        int tileDistance = (xDistance+yDistance)/gp.tileSize;

        if (!onPath && tileDistance<5){
            int i = new Random().nextInt(100)+1;
            if(i > 50){
                onPath = true;
            }
            if(onPath && tileDistance > 20){
                onPath = false;
            }
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop(){
        int i = new Random().nextInt(150)+1;

        //Set the goblin drop
        if(i <= 50) {
            dropItem(new Red_Potion(gp));
            dropItem(new Red_Potion(gp));
        }else if(i > 50 && i <= 100){
            dropItem(new Blue_Potion(gp));
        }else if (i > 100 && i <= 115){
            dropItem(new Red_Potion(gp));
            dropItem(new Shield(gp));
        }else if (i > 135 && i <= 150){
            dropItem(new Red_Potion(gp));
            dropItem(new Sword(gp));
        }
    }
}
