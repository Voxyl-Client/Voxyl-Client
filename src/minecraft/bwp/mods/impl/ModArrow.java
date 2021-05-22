package bwp.mods.impl;

import bwp.gui.hud.HUDConfigScreen;
import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModArrow extends ModDraggable{

	@Override
	public int getWidth() {
		return mc.fontRendererObj.getStringWidth("Arrows : " + this.getRemainingArrows()) + 3;
	}

	@Override
	public int getHeight() {
		return mc.fontRendererObj.FONT_HEIGHT + 2;
	}

	@Override
	public void render(ScreenPosition pos) {

		if(this.getRemainingArrows() < 1) {
			Render.drawString("Arrows : " + this.getRemainingArrows(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), Color.RED.getRGB(), true);

		} else if(this.getRemainingArrows() < 33){
			Render.drawString("Arrows : " + this.getRemainingArrows(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), Color.WHITE.getRGB(), true);
		}
		else{
			Render.drawString("Arrows : " + this.getRemainingArrows(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), Color.GREEN.getRGB(), true);
		}
	}
	
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		render(pos);
	}
	
	   private int getRemainingArrows() {
		  int i = 0;

		  for(ItemStack itemstack : this.mc.thePlayer.inventory.mainInventory) {
			 if(itemstack != null && itemstack.getItem().equals(Items.arrow)) {
				i += itemstack.stackSize;
			 }
		  }

		  return i;
	   }
}
