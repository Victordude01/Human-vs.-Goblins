package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Chest extends Entity {
    public Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        type = etc;
        down1 = setup("/Object/chest");
        collision = true;
    }

    public boolean use(Entity entity){
        openChest();
        return true;
    }
}
