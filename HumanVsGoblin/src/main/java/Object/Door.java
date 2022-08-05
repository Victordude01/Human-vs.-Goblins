package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Door extends Entity {
    public Door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setup("/Object/Door");
    }
}
