package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ModArrow extends ModDraggable{


	@Override
	public int getWidth() {
		return 20;
	}

	@Override
	public int getHeight() {
		return 25;
	}

	@Override
	public void render(ScreenPosition pos) {
		if(this.getRemainingArrows() < 1) {
			font.drawString(this.getRemainingArrows() + "", (pos.getAbsoluteX() + 8), (pos.getAbsoluteY() + 15), Color.RED.getRGB());

		} else if(this.getRemainingArrows() < 33){
			font.drawString(this.getRemainingArrows() + "", pos.getAbsoluteX() + 8, pos.getAbsoluteY() + 15, Color.WHITE.getRGB());
		}
		else{
			font.drawString(this.getRemainingArrows() + "", pos.getAbsoluteX() + 8, pos.getAbsoluteY() + 15, Color.GREEN.getRGB());
		}
        this.mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.arrow), pos.getAbsoluteX() + 3, pos.getAbsoluteY() - 1);
        
        

	}
	
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		if(this.getRemainingArrows() < 1) {
			font.drawString(this.getRemainingArrows() + "", pos.getAbsoluteX() + 8, pos.getAbsoluteY() + 15, Color.RED.getRGB());
		} else {
			font.drawString(this.getRemainingArrows() + "", pos.getAbsoluteX() + 8, pos.getAbsoluteY() + 15, Color.WHITE.getRGB());
		}
        this.mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.arrow), (pos.getAbsoluteX() + 3) , (pos.getAbsoluteY() - 1));

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
