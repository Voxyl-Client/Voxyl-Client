package bwp.utils;

import java.awt.Color;

import javax.vecmath.Vector3d;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;


public class RoundedRect {
	public static void renderRoundedQuad(Vector3d from, Vector3d to, int rad, Color col) {
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
	


}
