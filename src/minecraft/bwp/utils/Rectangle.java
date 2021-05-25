package bwp.utils;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3d;
import java.awt.*;


public class Rectangle {
	public static void render(int x, int y, int x2, int y2, Color color) {
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
	


}
