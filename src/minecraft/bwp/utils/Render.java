package bwp.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3d;
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

    public static void drawString(String string, int x, int y, float scale, boolean shadow)
    {
        drawString(string, x, y, scale, -1, shadow);
    }

    public static void drawString(String string, int x, int y, float scale, int color, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(x + 1, y + 1, 1F);
        GL11.glScalef(scale, scale, 1F);

        mc.fontRendererObj.drawString(string, 0, 0, color, shadow);

        GL11.glPopMatrix();
    }

    public static void drawRect(int x, int y, int x2, int y2, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);
        GL11.glBegin(GL11.GL_POLYGON);
        {
            int initial = -90;
            double[][] map = new double[][]{
                    new double[]{x2,y2},
                    new double[]{x2,y},
                    new double[]{x,y},
                    new double[]{x,y2}
            };
            for(int i = 0;i<4;i++) {
                double[] current = map[i];
                GL11.glVertex2d(current[0],current[1]);
            }
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void drawRoundedRectFromV3D(Vector3d from, Vector3d to, int rad, Color col) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(col.getRed() / 255F, col.getGreen() / 255F, col.getBlue() / 255F, col.getAlpha() / 255F);
        GL11.glBegin(GL11.GL_POLYGON);
        {
            int initial = -90;
            double[][] map = new double[][]{
                    new double[]{to.x,to.y},
                    new double[]{to.x,from.y},
                    new double[]{from.x,from.y},
                    new double[]{from.x,to.y}
            };
            for(int i = 0;i<4;i++) {
                double[] current = map[i];
                initial += 90;
                for(int r = initial;r<(360/4+initial);r++) {
                    double rad1 = Math.toRadians(r);
                    double sin = Math.sin(rad1)*rad;
                    double cos = Math.cos(rad1)*rad;
                    GL11.glVertex2d(current[0]+sin,current[1]+cos);
                }
            }
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    private static void drawArc(int x, int y, int radius, int startAngle, int endAngle, Color color) {

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);

        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

        worldRenderer.begin(6, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();

        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }

        Tessellator.getInstance().draw();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawRoundedRectangle(int x, int y, int width, int height, int cornerRadius, Color color) {
        Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
        Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
        Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());

        drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
        drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
        drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
        drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
    }

    public static void drawHollowRoundedRect(int x, int y, int width, int height, int cornerRadius, Color color) {
        drawVerticalLine(x, y + cornerRadius, y + height - cornerRadius, color);
        drawHorizontalLine(x + cornerRadius, x + width - cornerRadius, y, color);
        drawVerticalLine(x + width, y + cornerRadius, y + height - cornerRadius, color);
        drawHorizontalLine(x + cornerRadius, x + width - cornerRadius, y + height, color);

        drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
        drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
        drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
        drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
    }

    public static void drawHorizontalLine(int startX, int endX, int y, Color color)
    {
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

    public static void drawVerticalLine(int x, int startY, int endY, Color color)
    {
        if (endY < startY)
        {
            int i = startY;
            startY = endY;
            endY = i;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }
}
