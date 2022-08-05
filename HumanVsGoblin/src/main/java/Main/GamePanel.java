package main.java.Main;

import main.java.AI.PathFinder;
import main.java.Entity.Entity;
import main.java.Entity.Player;
import main.java.Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    //Screen Settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 20; //16
    public final int maxScreenRow = 12; //12
    public final int screenWidth = tileSize * maxScreenCol; //960 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //World Settings
    public final int maxWorldCol = 68; //buffer of 10 blocks needed on X-axis
    public final int maxWorldRow = 50; //buffer of 6 blocks needed on Y-axis
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //For Full Screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //FPS
    int FPS = 60;

    //System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cCheck = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this,keyH);
    public Entity[] obj = new Entity[30];
    public Entity[] goblin = new Entity[20];
    public ArrayList<Entity> projectileList = new ArrayList<>();

    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int titleState = 0;
    public final int controlsState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();
        assetSetter.setGoblin();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(fullScreenOn){
            setFullScreen();
        }
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
//                repaint();
                drawToTempScreen(); //draw everything to the buffered image
                drawToScreen(); //draw the buffered image to the screen
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        if (gameState == playState){
            player.update();
            for (int i = 0; i < goblin.length; i++){
                if (goblin[i] != null){
                    if (goblin[i].alive && !goblin[i].dying ){
                        goblin[i].update();
                    }
                    if(!goblin[i].alive){
                        goblin[i].checkDrop();
                        goblin[i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++){
                if (projectileList.get(i) != null){
                    if (projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
        }
        if (gameState == pauseState){
            //nothing
        }
    }

    public void setFullScreen(){
        //Get local screen device information
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //Get full screen width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void drawToTempScreen(){
        //Title Screen
        if (gameState == titleState){ui.draw(g2);}

        //Others
        else{
            //Tile
            tileM.draw(g2);

            //Objects
            for (Entity entity : obj) {if (entity != null) {entity.draw(g2);}}

            //Goblins
            for (Entity entity : goblin) {if (entity != null) {entity.draw(g2);}}

            //Projectiles
            for (Entity entity : projectileList) {entity.draw(g2);}

            //Player
            player.draw(g2);

            //UI
            ui.draw(g2);
        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
    }

//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D)g;
//        //Title Screen
//        if (gameState == titleState){
//            ui.draw(g2);
//        }
//
//        //Others
//        else{
//            //Tile
//            tileM.draw(g2);
//
//            //Objects
//            for (Entity entity : obj) {
//                if (entity != null) {
//                    entity.draw(g2);
//                }
//            }
//
//            //Goblins
//            for (Entity entity : goblin) {
//                if (entity != null) {
//                    entity.draw(g2);
//                }
//            }
//
//            //Projectiles
//            for (Entity entity : projectileList) {
//                entity.draw(g2);
//            }
//
//            //Player
//            player.draw(g2);
//
//            //UI
//            ui.draw(g2);
//        }
//        g2.dispose();
//    }
}
