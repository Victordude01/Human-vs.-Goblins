package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Blue_Potion extends Entity {
    GamePanel gp;
    int value = 1;

    public Blue_Potion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Blue Potion";
        type = etc;
        stackable = true;
        down1 = setup("/Object/blue_potion");
        still = setup("/Object/blue_potion");
        description = "[" + name + "]\nA new pair of Timb's\n+1 Speed";
    }

    public boolean use(Entity entity){
        gp.ui.showMessage("+1 Speed");
        entity.speed += value;
        return true;
    }

}
