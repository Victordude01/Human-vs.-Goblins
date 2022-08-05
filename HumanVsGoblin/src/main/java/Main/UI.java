package main.java.Main;

import main.java.Entity.Entity;
import main.java.Object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage heart_full,heart_half,heart_blank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public int commandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.BOLD,40);

        //Create HUD Object
        Entity heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //Title State
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //Controls State
        if (gp.gameState == gp.controlsState){
            drawControlsScreen();
        }

        //Play State
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            //Time
            playTime += (double)1/60;
            g2.drawString("Time: "+dFormat.format(playTime),gp.tileSize*15,65);

            //displaying a message
            int messageX = gp.tileSize;
            int messageY = gp.tileSize*4;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));

            for (int i = 0; i < message.size();i++){
                if (message.get(i) != null){
                    g2.setColor(Color.white);
                    g2.drawString(message.get(i),messageX,messageY);

                    int counter = messageCounter.get(i) + 1;
                    messageCounter.set(i,counter);
                    messageY += 50;

                    if(messageCounter.get(i) > 180){
                        message.remove(i);
                        messageCounter.remove(i);
                        messageOn = false;
                    }
                }
            }

        }

        //Pause State
        if (gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }

        //Character State
        if (gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }

        //Options State
        if(gp.gameState == gp.optionState){
            drawOptionScreen();
        }

        //Game Over State
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
    }

    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        text = "Game Over";
        //Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);

        //Game Over
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        //Stats
        g2.setFont(g2.getFont().deriveFont(40F));
        text = "Kills: ";
        y += gp.tileSize*2;
        g2.drawString(text+gp.player.killCounter,x+50,y);
        text = "Deaths: ";
        y += 50;
        g2.drawString(text+gp.player.deathCounter,x+50,y);
        text = "Total Time: ";
        y += 50;
        g2.drawString(text + dFormat.format(playTime) + " seconds", x + 50, y);



        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize+45;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.setColor(Color.red);
            g2.drawString(">",x-40,y);
        }

        //Quit
        g2.setColor(Color.white);
        text = "Quit";
        x = getXforCenteredText(text);
        y += 60;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.setColor(Color.red);
            g2.drawString(">",x-40,y);
        }
    }

    public void drawOptionScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(28F));

        //Sub Window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch (subState) {
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_FullScreen(frameX, frameY);
            case 2 -> options_control(frameX, frameY);
        }
        gp.keyH.enterPressed = false;
    }

    public void options_top(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        //FullScreen On/Off
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full Screen",textX,textY);
        if(commandNum == 0){
            g2.setColor(Color.red);
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed){
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }

        }

        //Controls
        g2.setColor(Color.white);
        textY += gp.tileSize;
        g2.drawString("Controls",textX,textY);
        if(commandNum == 1){
            g2.setColor(Color.red);
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed){
                subState = 2;
            }
        }

        //End Game
        g2.setColor(Color.white);
        textY += gp.tileSize;
        g2.drawString("End Game",textX,textY);
        if(commandNum == 2){
            g2.setColor(Color.red);
            g2.drawString(">",textX-25,textY);
        }

        //Back
        g2.setColor(Color.white);
        textY += gp.tileSize*2;
        g2.drawString("Back",textX,textY);
        if(commandNum == 3){
            g2.setColor(Color.red);
            g2.drawString(">",textX-25,textY);
        }

        //Fullscreen checkbox
        g2.setColor(Color.white);
        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,24,24);
        if(gp.fullScreenOn){
            g2.fillRect(textX,textY,24,24);
        }

        gp.config.saveConfig();
    }

    public void options_FullScreen(int frameX,int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        String currentDialogue = "The change will take \neffect after restarting \nthe game.";

        for(String line:currentDialogue.split("\n")){
            g2.drawString(line,textX,textY);
            textY += 40;
        }

        //back
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back",textX,textY);
        if (commandNum == 0){
            g2.setColor(Color.red);
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed){
                subState = 0;
            }
        }
    }

    public void options_control(int frameX,int frameY){
        int textX;
        int textY;

        //Title
        String text = "Controls";
        g2.setFont(g2.getFont().deriveFont(24F));
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move",textX,textY); textY += gp.tileSize;
        g2.drawString("Pause",textX,textY); textY += gp.tileSize;
        g2.drawString("Option Menu",textX,textY); textY += gp.tileSize;
        g2.drawString("Select",textX,textY); textY += gp.tileSize;
        g2.drawString("Attack/Shoot",textX,textY); textY += gp.tileSize;
        g2.drawString("Inventory/Stats",textX,textY);

        textX = frameX + gp.tileSize*5+10;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD",textX,textY); textY += gp.tileSize;
        g2.drawString("P",textX,textY); textY += gp.tileSize;
        g2.drawString("ESC",textX,textY); textY += gp.tileSize;
        g2.drawString("ENTER",textX,textY); textY += gp.tileSize;
        g2.drawString("Space",textX,textY); textY += gp.tileSize;
        g2.drawString("C",textX,textY);

        //Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back",textX,textY);
        g2.setColor(Color.red);
        g2.drawString(">",textX-25,textY);
        if(gp.keyH.enterPressed){
            subState = 0;
        }
    }

    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //Draw Max Life
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x += gp.tileSize;
        }

        //Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //Draw Current Life
        while(i < gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    public void drawControlsScreen(){
        int back = 0;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        //Title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,60));
        String text = "Controls";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*2;

        //Shadow
        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);
        //Main Color
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40));
        text = "\"WASD\" -> Movement";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text,x,y);

        text = "\"P\" -> Pause";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);

        text = "\"ESC\" -> Option Menu";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);

        text = "\"ENTER\" -> Select";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);

        text = "\"SPACE\" -> ATTACK";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);

        text = "\"C\" -> Inventory";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);

        text = "BACK";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text,x,y);
        if(back == 0){
            g2.setColor(Color.red);
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawTitleScreen(){
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        //Title Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80));
        String text = "Human vs. Goblins";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        //Shadow
        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);
        //Main Color
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //Character
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40));

        text = "PLAY GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.setColor(Color.red);
            g2.drawString(">",x-gp.tileSize,y);
        }

        g2.setColor(Color.white);
        text = "CONTROLS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.setColor(Color.red);
            g2.drawString(">",x-gp.tileSize,y);
        }

        g2.setColor(Color.white);
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 2){
            g2.setColor(Color.red);
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(80F));
        String text = "PAUSED";
        int x;
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - length/2;
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawCharacterScreen(){
        //create a frame
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*8;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 40;

        //Names
        g2.drawString("Health",textX,textY);
        textY += lineHeight;
        g2.drawString("Speed",textX,textY);
        textY += lineHeight;
        g2.drawString("Strength",textX,textY);
        textY += lineHeight;
        g2.drawString("Dexterity",textX,textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon",textX,textY);
        textY += lineHeight + 10;
        g2.drawString("Shield",textX,textY);
        textY += lineHeight;
        g2.drawString("Attack Value",textX,textY);
        textY += lineHeight;
        g2.drawString("Defense Value",textX,textY);


        //Values
        int tailX = (frameX + frameWidth) - 30;
        //Reset TextY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.life);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.speed);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.still,tailX - gp.tileSize,textY-25,null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.still,tailX - gp.tileSize,textY-25,null);
        textY += gp.tileSize;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY+3);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY+3);
    }

    public void drawInventory(){
        //Frames
        final int frameX = gp.tileSize*12;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //Draw Player's Items
        for(int i = 0; i < gp.player.inventory.size();i++){
            //Equip Cursor
            if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }
            g2.drawImage(gp.player.inventory.get(i).still,slotX,slotY,null);

            //Display amount
            if(gp.player.inventory.get(i).amount > 1){
                g2.setFont(g2.getFont().deriveFont(32F));
                int amountX;
                int amountY;

                String s = "" + gp.player.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s,slotX + 44);
                amountY = slotY + gp.tileSize;

                //Shadow
                g2.setColor(new Color(60,60,60));
                g2.drawString(s,amountX,amountY);

                //number
                g2.setColor(Color.white);
                g2.drawString(s,amountX-3,amountY-3);
            }
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //Cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        //Description Frame
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = gp.tileSize*3;

        //Draw Descrition Text
        int textX = frameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();
        if(itemIndex < gp.player.inventory.size()){
            drawSubWindow(frameX,dFrameY, frameWidth,dFrameHeight);
            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line,textX,textY);
                textY += 32;
            }
        }
    }

    public int getItemIndexOnSlot(){
        return slotCol + (slotRow*5);
    }

    public void drawSubWindow(int x,int y,int width,int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return tailX - length;
    }
}
