package bwp.utils;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Render {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static void drawChromaString(String string, int x, int y, float scale, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(x + 1, y + 1, 1F);
        GL11.glScalef(scale, scale, 1F);

        int xTmp = 0;
        for (char textChar : string.toCharArray())
        {
            long l = System.currentTimeMillis() - (xTmp * 10L - y * 10L);
            int i = Color.HSBtoRGB(l % (int) 2000.0F / 2000.0F, 0.8F, 0.8F);
            String tmp = String.valueOf(textChar);
            mc.fontRendererObj.drawString(tmp, xTmp, 0, i, shadow);

            xTmp += mc.fontRendererObj.getCharWidth(textChar);
        }

        GL11.glPopMatrix();
    }

    public static void drawString(String string, int x, int y, float scale, boolean shadow, int color)
    {
        drawString(string, x, y, scale, color, shadow);
    }

    private static void drawString(String string, int x, int y, float scale, int color, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(x + 1, y + 1, 1F);
        GL11.glScalef(scale, scale, 1F);

        mc.fontRendererObj.drawString(string, 0, 0, color, shadow);

        GL11.glPopMatrix();
    }

}
