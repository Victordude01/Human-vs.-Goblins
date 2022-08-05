package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Blue_Shield extends Entity {
    public Blue_Shield(GamePanel gp) {
        super(gp);

        name = "Blue Shield";
        type = shield;
        stackable = true;
        still = setup("/Object/shield_blue");
        down1 = setup("/Object/shield_blue");
        defenseValue = 3;
        description = "[" + name + "]\nA modern shield"+"\n+4 Defense";
    }
}
