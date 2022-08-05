package main.java.Entity;

import main.java.Main.GamePanel;
import main.java.Main.UtilityTool;
import main.java.Object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Entity {
    GamePanel gp;

    //Buffered Images
    public BufferedImage image,image2,image3;
    public BufferedImage up1, up2, up3, down1,down2,down3,left1,left2,left3,right1,right2,right3,still;
    public BufferedImage attackUp1, attackUp2, attackRight1,attackRight2,attackDown1,attackDown2,attackLeft1,attackLeft2;

    //State
    public int worldX,worldY;
    public boolean collision = false;
    public boolean collisionOn = false;
    public int spriteNum = 1;
    public String direction = "down";
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX,solidAreaDefaultY;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;

    //Counters
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotCounter = 0;

    //Character Attributes
    public boolean invincible = false;
    public int type; //0 = player, 1 = goblin
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int strength;
    public int dexterity;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    public int attack;
    public int defense;

    //Item Attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public final int sword = 0;
    public final int axe = 1;
    public final int shield = 2;
    public final int etc = 3;
    public final int bow = 4;
    public boolean stackable = false;
    public int amount = 1;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // STOP MOVING CAMERA
        if(gp.player.worldX < gp.player.screenX) {
            screenX = worldX;
        }
        if(gp.player.worldY < gp.player.screenY) {
            screenY = worldY;
        }
        int rightOffset = gp.screenWidth - gp.player.screenX;
        if(rightOffset > gp.worldWidth - gp.player.worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - gp.player.screenY;
        if(bottomOffset > gp.worldHeight - gp.player.worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                case "down" -> {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                case "left" -> {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                case "right" -> {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
            }

        //Goblin's health
        if (type == 1 && hpBarOn){
            double oneScale = (double)gp.tileSize/maxLife;
            double hpBarValue = oneScale*life;

            g2.setColor(new Color(35,35,35));
            g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

            g2.setColor(new Color(255,0,30));
            g2.fillRect(screenX,screenY - 15,(int)hpBarValue,10);

            hpBarCounter++;

            if (hpBarCounter > 600){
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

        if (invincible){
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2,0.7f);
        }

        if (dying){
            dyingAnimation(g2);
        }

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, null);
        }
        // If player is around the edge, draw everything
        else if(gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                rightOffset > gp.worldWidth - gp.player.worldX ||
                bottomOffset > gp.worldHeight - gp.player.worldY) {
            g2.drawImage(image, screenX, screenY, null);
        }

        changeAlpha(g2,1f);
    }

    public boolean use(Entity entity){
        return false;
    }

    public void checkDrop(){}

    public void openChest(){
        int i = new Random().nextInt(150)+1;
        //Set the chest loot
        if(i < 35) {
            loot(new Red_Potion(gp));
            loot(new Blue_Potion(gp));
        }else if(i >= 35 && i < 100){
            loot(new Bow(gp));
        }else if(i >= 100 && i < 115){
            loot(new Axe(gp));
        }else if(i >= 115 && i <= 150){
            loot(new Blue_Shield(gp));
        }
    }

    public void loot(Entity loot){
        if(gp.player.canObtainItem(loot)){
            gp.ui.showMessage("You found a "+loot.name);
        }
    }

    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }

    public void damageReaction(){}

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5; //frames

        if (dyingCounter <= i){changeAlpha(g2,1f);
        } else if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2,1f);
        }else if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2,0f);
        }else if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2,1f);
        }else if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2,0f);
        }else if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2,1f);
        }else if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2,0f);
        }else if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2,1f);}
        if (dyingCounter > i*8){
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2,float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }

    public void setAction(){
    }

    public void checkCollision(){
        collisionOn = false;
        gp.cCheck.checkTitle(this);
        gp.cCheck.checkObject(this,false);
        boolean contact = gp.cCheck.checkPlayer(this);

        if(this.type == 1 && contact){
            int goblinIndex = gp.cCheck.checkEntity(this,gp.goblin);
            gp.player.contactWithGoblin(goblinIndex);
        }
    }

    public void update(){
        setAction();
        checkCollision();

        //If Collision is False, Player can move
        if (!collisionOn){
            switch (direction){
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            }else if (spriteNum == 2){
                spriteNum =1;
            }
            spriteCounter = 0;
        }

        if (invincible){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow,this);

        if(gp.pFinder.search()){
            //next worldX and worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            //Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                //left or right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }else if(enTopY > nextY && enLeftX > nextX){
                //up or left
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }else if(enTopY > nextY && enLeftX < nextX){
                //up or right
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }else if(enTopY < nextY && enLeftX > nextX){
                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }else if(enTopY < nextY && enLeftX < nextX){
                //down or right
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }
        }
    }
}
