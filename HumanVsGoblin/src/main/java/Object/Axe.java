package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Axe extends Entity {

    public Axe(GamePanel gp) {
        super(gp);

        name = "Axe";
        stackable = true;
        type = axe;
        still = setup("/Object/axe");
        down1 = setup("/Object/axe");
        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nAn Axe"+"\n+1 Attack";
    }
}