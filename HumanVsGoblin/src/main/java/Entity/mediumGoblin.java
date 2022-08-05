package main.java.Entity;

import main.java.Main.GamePanel;
import main.java.Object.Blue_Potion;

import main.java.Object.Red_Potion;

import java.util.Random;

public class mediumGoblin extends Entity {
    public mediumGoblin(GamePanel gp) {
        super(gp);
        type = 1;
        name = "medium Goblin";
        speed = 2;
        maxLife = 15;
        life = maxLife;
        strength = 8;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }



    public void getImage(){
        up1 = setup("/Goblin/mediumup2");
        up2 = setup("/Goblin/mediumup3");
        down1 = setup("/Goblin/mediumdown2");
        down2 = setup("/Goblin/mediumdown3");
        left1 = setup("/Goblin/mediumleft1");
        left2 = setup("/Goblin/mediumleft2");
        right1 = setup("/Goblin/mediumright1");
        right2 = setup("/Goblin/mediumright2");
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
        int i = new Random().nextInt(100)+1;

        //Set the goblin drop
        if(i <= 50) {
            dropItem(new Red_Potion(gp));
        }else if(i > 50 && i <= 100){
            dropItem(new Blue_Potion(gp));
        }
    }
}
