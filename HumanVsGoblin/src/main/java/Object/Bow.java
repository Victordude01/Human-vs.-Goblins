package main.java.Object;

import main.java.Entity.Projectile;
import main.java.Main.GamePanel;

public class Bow extends Projectile {
    GamePanel gp;

    public Bow(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = bow;

        stackable = true;
        name = "Bow";
        description = "[" + name + "]\nA Bow"+"\n+2 Attack";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attackValue = 2;
        alive = false;
        getImage();
    }

    public void getImage(){
        still = setup("/Projectile/bow");
        up1 = setup("/Projectile/arrowup");
        up2 = setup("/Projectile/arrowup");
        down1 = setup("/Projectile/arrowdown");
        down2 = setup("/Projectile/arrowdown");
        left1 = setup("/Projectile/arrowleft");
        left2 = setup("/Projectile/arrowleft");
        right1 = setup("/Projectile/arrowright");
        right2 = setup("/Projectile/arrowright");
    }
}
