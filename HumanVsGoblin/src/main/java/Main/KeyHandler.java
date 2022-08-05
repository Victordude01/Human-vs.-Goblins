package main.java.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,leftPressed,rightPressed,spacePressed,enterPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();


        //Title State
        if(gp.gameState == gp.titleState){
            if (key == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum<0){
                    gp.ui.commandNum = 2;
                }
            }
            if (key == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }

            if (key == KeyEvent.VK_ENTER){
                if (gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 1){
                    gp.gameState = gp.controlsState;
                    key = 0;
                }
                if (gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }

        //Play State
        if (gp.gameState == gp.playState || gp.gameState == gp.pauseState){

            if (key == KeyEvent.VK_W) {upPressed = true;}
            if (key == KeyEvent.VK_S) {downPressed = true;}
            if (key == KeyEvent.VK_D) {rightPressed = true;}
            if (key == KeyEvent.VK_A) {leftPressed = true;}
            if (key == KeyEvent.VK_SPACE) {spacePressed = true;}
            if (key == KeyEvent.VK_C){gp.gameState = gp.characterState;}

            if(key == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.optionState;
                key = 0;
            }

            //Pause State
            if (key == KeyEvent.VK_P) {
                if (gp.gameState == gp.playState){
                    gp.gameState = gp.pauseState;
                }else if(gp.gameState == gp.pauseState){
                    gp.gameState = gp.playState;
                }
            }
        }
        //Character State
        else if (gp.gameState == gp.characterState) {
            if (key == KeyEvent.VK_C){gp.gameState = gp.playState;}
            if(key == KeyEvent.VK_W) {if (gp.ui.slotRow != 0) {gp.ui.slotRow--;}}
            if(key == KeyEvent.VK_A) {if (gp.ui.slotCol != 0) {gp.ui.slotCol--;}}
            if(key == KeyEvent.VK_S){if (gp.ui.slotRow != 3) {gp.ui.slotRow++;}}
            if(key == KeyEvent.VK_D){if (gp.ui.slotCol != 4) {gp.ui.slotCol++;}}
            if(key == KeyEvent.VK_ENTER){gp.player.selectItem();}
        }

        //Options State
        if (gp.gameState == gp.optionState){
            if(key == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
            if(key == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if (key == KeyEvent.VK_W && gp.ui.subState == 0) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum<0){
                    gp.ui.commandNum = 3;
                }
            }
            if (key == KeyEvent.VK_S && gp.ui.subState == 0) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3){
                    gp.ui.commandNum = 0;
                }
            }
            if (key == KeyEvent.VK_ENTER){
                if (gp.ui.commandNum == 2){
                    System.exit(0);
                }
                if (gp.ui.commandNum == 3){
                    gp.gameState = gp.playState;
                }
            }
        }


        //Controls State
        if (gp.gameState == gp.controlsState){
            if (key == KeyEvent.VK_ENTER){
                gp.gameState = gp.titleState;
            }
        }

        //Game Over State
        if (gp.gameState == gp.gameOverState){
            if(key == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }
            }
            if(key == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
            }
            if(key == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.player.setDefaultValues();
                    gp.gameState = gp.playState;
                }else if(gp.ui.commandNum == 1){
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {upPressed = false;}
        if (key == KeyEvent.VK_S) {downPressed = false;}
        if (key == KeyEvent.VK_D) {rightPressed = false;}
        if (key == KeyEvent.VK_A) {leftPressed = false;}
        if (key == KeyEvent.VK_SPACE) {spacePressed = false;}
        if (key == KeyEvent.VK_ENTER) {enterPressed = false;}

    }
}
