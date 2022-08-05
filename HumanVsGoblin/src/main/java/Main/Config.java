package main.java.Main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/config.txt"));

            //Full Screen
            if(gp.fullScreenOn){
                bw.write("On");
            }
            if(!gp.fullScreenOn){
                bw.write("Off");
            }
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/config.txt"));
            String s = br.readLine();

            //Full Screen
            if(s.equals("On")){
                gp.fullScreenOn = true;
            }
            if(s.equals("Off")){
                gp.fullScreenOn = false;
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
