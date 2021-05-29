package bwp.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

public class BWPUtils {
	   public static void drawEntityOnScreen(int posX, int posY, int scale, float rotation, EntityLivingBase ent)
	    {
	        float rY = ent.rotationYaw % 360;
	        float rYH = ent.rotationYawHead % 360;
	        float rYO = ent.renderYawOffset;
	        ent.rotationYawHead = rotation + rYH - rYO;
	        ent.rotationYaw = rotation;
	        ent.renderYawOffset = rotation;
	        GlStateManager.enableColorMaterial();
	        GlStateManager.pushMatrix();
	        GlStateManager.translate((float)posX, (float)posY, 50.0F);
	        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
	        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
	        RenderHelper.enableStandardItemLighting();
	        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.translate(0.0F, 0.0F, 0.0F);
	        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
	        rendermanager.setPlayerViewY(180.0F);
	        rendermanager.setRenderShadow(false);
	        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
	        rendermanager.setRenderShadow(true);
	        GlStateManager.popMatrix();
	        RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.disableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	        ent.rotationYaw = rY;
	        ent.rotationYawHead = rYH;
	        ent.renderYawOffset = rYO;
	    }

}
