package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Shield extends Entity {
    public Shield(GamePanel gp) {
        super(gp);

        name = "Shield";
        type = shield;
        stackable = true;
        down1 = setup("/Object/shield_wood");
        still = setup("/Object/shield_wood");
        defenseValue = 2;
        description = "[" + name + "]\nAn old shield"+"\n+2 Defense";
    }
}
