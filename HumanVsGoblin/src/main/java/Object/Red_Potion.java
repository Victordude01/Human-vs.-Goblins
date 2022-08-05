package main.java.Object;

import main.java.Entity.Entity;
import main.java.Main.GamePanel;

public class Red_Potion extends Entity {
    GamePanel gp;
    int value = 3;

    public Red_Potion(GamePanel gp) {
        super(gp);
        this.gp = gp;
        stackable = true;

        name = "Red Potion";
        type = etc;
        down1 = setup("/Object/red_potion");
        still = setup("/Object/red_potion");
        description = "[" + name + "]\nA potion that heals \n"+"+3 Health";
    }

    public boolean use(Entity entity){
        gp.ui.showMessage("+3 Health");
        entity.life += value;
        if (gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        return  true;
    }
}