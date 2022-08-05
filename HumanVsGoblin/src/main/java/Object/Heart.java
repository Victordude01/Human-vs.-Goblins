package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Heart extends Entity {

    public Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setup("/Object/heart_full");
        image2 = setup("/Object/heart_half");
        image3 = setup("/Object/heart_blank");
    }
}
