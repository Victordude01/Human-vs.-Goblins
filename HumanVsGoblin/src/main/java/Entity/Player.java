package main.java.Entity;

import main.java.Main.GamePanel;
import main.java.Main.KeyHandler;
import main.java.Main.UtilityTool;
import main.java.Object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX,screenY;
    int standCounter = 0;
    public int killCounter = 0;
    public int deathCounter = 0;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        type = 0;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 28;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 16;
        solidArea.height = 16;

        setDefaultValues();
        getPlayerImage();
        getAttackImages();
        setItems();
    }

    public void getPlayerImage(){
        up1 = setup("up1",gp.tileSize,gp.tileSize);
        up2 = setup("up2",gp.tileSize,gp.tileSize);
        up3 = setup("up3",gp.tileSize,gp.tileSize);
        down1 = setup("down1",gp.tileSize,gp.tileSize);
        down2 = setup("down2",gp.tileSize,gp.tileSize);
        down3 = setup("down3",gp.tileSize,gp.tileSize);
        left1 = setup("left1",gp.tileSize,gp.tileSize);
        left2 = setup("left2",gp.tileSize,gp.tileSize);
        left3 = setup("left3",gp.tileSize,gp.tileSize);
        right1 = setup("right1",gp.tileSize,gp.tileSize);
        right2 = setup("right2",gp.tileSize,gp.tileSize);
        right3 = setup("right3",gp.tileSize,gp.tileSize);
    }

    public void getAttackImages(){
        if(currentWeapon.type == sword){
            //attack images
            attackUp1 = setup("swordup1",gp.tileSize,gp.tileSize*2);
            attackUp2 = setup("swordup2",gp.tileSize,gp.tileSize*2);
            attackDown1 = setup("sworddown1",gp.tileSize,gp.tileSize*2);
            attackDown2 = setup("sworddown2",gp.tileSize,gp.tileSize*2);
            attackLeft1 = setup("swordleft1",gp.tileSize*2,gp.tileSize);
            attackLeft2 = setup("swordleft2",gp.tileSize*2,gp.tileSize);
            attackRight1 = setup("swordright1",gp.tileSize*2,gp.tileSize);
            attackRight2 = setup("swordright2",gp.tileSize*2,gp.tileSize);
        }
        if(currentWeapon.type == axe){
            //attack images
            attackUp1 = setup("axeup1",gp.tileSize,gp.tileSize*2);
            attackUp2 = setup("axeup2",gp.tileSize,gp.tileSize*2);
            attackDown1 = setup("axedown1",gp.tileSize,gp.tileSize*2);
            attackDown2 = setup("axedown2",gp.tileSize,gp.tileSize*2);
            attackLeft1 = setup("axeleft1",gp.tileSize*2,gp.tileSize);
            attackLeft2 = setup("axeleft2",gp.tileSize*2,gp.tileSize);
            attackRight1 = setup("axeright1",gp.tileSize*2,gp.tileSize);
            attackRight2 = setup("axeright2",gp.tileSize*2,gp.tileSize);
        }
        if(currentWeapon.type == bow){
            attackUp1 = setup("bowup1",gp.tileSize,gp.tileSize);
            attackUp2 = setup("bowup2",gp.tileSize,gp.tileSize);
            attackDown1 = setup("bowdown1",gp.tileSize,gp.tileSize);
            attackDown2 = setup("bowdown2",gp.tileSize,gp.tileSize);
            attackLeft1 = setup("bowleft1",gp.tileSize,gp.tileSize);
            attackLeft2 = setup("bowleft2",gp.tileSize,gp.tileSize);
            attackRight1 = setup("bowright1",gp.tileSize,gp.tileSize);
            attackRight2 = setup("bowright2",gp.tileSize,gp.tileSize);
        }
    }

    public BufferedImage setup(String imageName,int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image,width,height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 34;
        worldY = gp.tileSize * 25;
        speed = 3;
        direction = "down";

        //Player's Status
        maxLife = 12;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        currentWeapon = new Sword(gp);
        currentShield = new Shield(gp);
        attack = getAttack();
        defense = getDefense();
        projectile = new Bow(gp);
    }

    public void setItems(){
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength + currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity + currentShield.defenseValue;
    }

    public void update(){
        if(keyH.spacePressed){
            attacking = true;
        }

        if (attacking){
            attacking();
        }else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            }else if(keyH.downPressed){
                direction = "down";
            }else if(keyH.leftPressed){
                direction = "left";
            } else if(keyH.rightPressed){
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gp.cCheck.checkTitle(this);

            //check object collision
            int objIndex = gp.cCheck.checkObject(this,true);
            pickUpObject(objIndex);

            //check monster collision
            int monsterIndex = gp.cCheck.checkEntity(this,gp.goblin);
            contactWithGoblin(monsterIndex);

            //if collision is false, player can move
            if (!collisionOn){
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                } else if(spriteNum == 2){
                    spriteNum = 3;
                }else if(spriteNum == 3){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }else{
            standCounter++;

            if (standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if (invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        //Bow and Arrow
        if(gp.player.currentWeapon.type == bow){
            if(gp.keyH.spacePressed && !projectile.alive && shotCounter == 40){
                projectile.set(worldX,worldY,direction,true,this);
                gp.projectileList.add(projectile);
                shotCounter = 0;
            }
        }

        if(shotCounter < 40){
            shotCounter++;
        }

        if(life <= 0){
            gp.gameState = gp.gameOverState;
            deathCounter++;
        }
    }

    public void attacking(){
        spriteCounter++;
        if (spriteCounter <= 5){
            spriteNum = 1;
        } else if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for the attack area
            switch (direction){
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }
            //Attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //check goblin's collision with the updated x,y and solid area.
            int goblinIndex = gp.cCheck.checkEntity(this,gp.goblin);
            damageGoblin(goblinIndex);

            //after checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;


        } else if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damageGoblin(int i){
        if(i != 999){
            if(!gp.goblin[i].invincible){

                int damage = attack - gp.goblin[i].defense;
                if (damage < 0){damage = 0;}
                gp.goblin[i].life -= damage;
                gp.ui.showMessage(damage + " damage!");
                gp.goblin[i].invincible = true;
                gp.goblin[i].damageReaction();

                if (gp.goblin[i].life <= 0){
                    gp.goblin[i].dying = true;
                    gp.ui.showMessage("killed the " + gp.goblin[i].name + "!");
                    killCounter++;
                }
            }
        }
    }

    public void contactWithGoblin(int i){
        if (i != 999){
            if (!invincible && !gp.goblin[i].dying){
                int damage = gp.goblin[i].strength - defense;
                if (damage > 0){
                    life -= damage;
                }
                invincible = true;
            }
        }
    }

    public void pickUpObject(int i){
        if (i != 999){
            String objectName = gp.obj[i].name;
            String text = "";

            if ("Chest".equals(objectName)) {
                gp.obj[i] = null;
                openChest();
            }

            if(!(gp.obj[i] == null)){
                if(canObtainItem(gp.obj[i])){
                    text = "Got a " + gp.obj[i].name + "!";
                }else {
                    inventory.add(gp.obj[i]);
                    text = "You cannot carry any more!";
                }
            }
            gp.ui.showMessage(text);
            gp.obj[i] = null;
        }

    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == 0 || selectedItem.type == 1){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImages();
            }
            if (selectedItem.type == 2){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == 3){
                if(selectedItem.use(this)){
                    if (selectedItem.amount > 1){
                        selectedItem.amount--;
                    }else{
                        inventory.remove(itemIndex);
                    }
                }

            }
            if (selectedItem.type == 4){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImages();
            }
        }
    }

    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for(int i = 0; i < inventory.size();i++){
            if(inventory.get(i).name.equals(itemName))
                itemIndex = i;
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtain = false;
        //Check if stackable
        if(item.stackable){
            int index = searchItemInInventory(item.name);
            if (index != 999){
                inventory.get(index).amount++;
                canObtain = true;
                if(item.type == bow||item.type == axe||item.type == sword){
                    gp.ui.showMessage("You already have this "+item.name+"\n you gain +1 Strength");
                    gp.player.strength += 1;
                    gp.player.getAttack();
                }
                if(item.type == shield){
                    gp.ui.showMessage("You already have this "+item.name+"\n you gain +1 Dexterity");
                    gp.player.dexterity += 1;
                    gp.player.getDefense();
                }
            }else{
                //New Item
                if(inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }else{
            //not stackable
            if(inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                    if (spriteNum == 3) {image = up3;}
                }
                if (attacking){
                    if (gp.player.currentWeapon.type != bow) {
                        tempScreenY = screenY - gp.tileSize;
                    }
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
            }
            case "down" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                    if (spriteNum == 3) {image = down3;}

                }
                if (attacking){
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
            }
            case "left" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                    if (spriteNum == 3) {image = left3;}

                }
                if (attacking){
                    if (gp.player.currentWeapon.type != bow) {
                        tempScreenX = screenX - gp.tileSize;
                    }
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
            }
            case "right" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                    if (spriteNum == 3) {image = right3;}

                }
                if (attacking){
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
            }
        }

        if (invincible){
            changeAlpha(g2,0.7f);
        }

        g2.drawImage(image,tempScreenX,tempScreenY,null);
        //Collision Area
//        g2.setColor(Color.red);
//        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        //reset Alpha
        changeAlpha(g2,1f);
    }
}
