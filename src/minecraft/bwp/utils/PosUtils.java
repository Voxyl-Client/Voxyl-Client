package bwp.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import paulscode.sound.libraries.LibraryJavaSound;

import java.util.ArrayList;

public class PosUtils {

    private static Minecraft mc;

    public int getNewPosX(int posX, int posY, int prevX, int prevY){

        mc = Minecraft.getMinecraft();

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int scaleFactor = scaledResolution.getScaleFactor();

        int displayHeight = mc.displayHeight;
        int displayWidth = mc.displayWidth;

        int xPos = prevX / posX;
        int yPos = prevY / posY;

        ArrayList posList = new ArrayList();
        posList.add(xPos);
        posList.add(yPos);


        System.out.println(posList);




        return 0;
    }

}
