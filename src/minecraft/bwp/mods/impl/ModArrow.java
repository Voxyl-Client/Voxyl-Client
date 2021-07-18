package bwp.mods.impl;

import bwp.mods.HUDMod;
import bwp.utils.Render;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModArrow extends HUDMod {
	private int color = -1;

	public ModArrow() {
		super("Arrow HUD");
	}

	@Override
	public int getWidth() {
		return mc.fontRendererObj.getStringWidth("Arrows : " + this.getRemainingArrows()) + 3;
	}

	@Override
	public int getHeight() {
		return mc.fontRendererObj.FONT_HEIGHT + 2;
	}

	@Override
	public void render() {

		if(this.getRemainingArrows() < 1) {
			Render.drawString("Arrows : " + this.getRemainingArrows(), renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true,color);

		} else if(this.getRemainingArrows() < 33){
			Render.drawString("Arrows : " + this.getRemainingArrows(), renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true, color);
		}
		else{
			Render.drawString("Arrows : " + this.getRemainingArrows(), renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true, color);
		}
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
