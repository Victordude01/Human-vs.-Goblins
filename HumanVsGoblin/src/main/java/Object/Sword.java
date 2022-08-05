package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Sword extends Entity {

    public Sword(GamePanel gp) {
        super(gp);

        name = "Sword";
        type = sword;
        stackable = true;
        down1 = setup("/Object/sword_normal");
        still = setup("/Object/sword_normal");
        attackValue = 2;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword"+"\n+2 Attack";
    }
}
