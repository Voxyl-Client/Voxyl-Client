package bwp.mods.impl;

import org.lwjgl.opengl.GL11;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModArmorStatus extends ModDraggable {
	
	private ScreenPosition pos = ScreenPosition.fromRelativePosition(0.5, 0.5);

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public void render(ScreenPosition pos) {
		for(int i = 0; i< mc.thePlayer.inventory.armorInventory.length; i++) {
		ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
		renderItemStack(i, itemStack, pos);
		}
		
	}
	public void renderDummy(ScreenPosition pos) {
		renderItemStack(3, new ItemStack(Items.leather_helmet), pos);
		renderItemStack(2, new ItemStack(Items.leather_chestplate), pos);
		renderItemStack(1, new ItemStack(Items.leather_leggings), pos);
		renderItemStack(0, new ItemStack(Items.leather_boots), pos);
	}

	private void renderItemStack(int i, ItemStack is,ScreenPosition position) {
		if(is == null) {
			return;
		}
		
		GL11.glPushMatrix();
		int yAdd = (-16 * i) + 48;
		if(is.getItem().isDamageable()) {
			double damage = ((is.getMaxDamage() + is.getItemDamage()) / (double) is.getMaxDamage()) * 100;
			font.drawString(String.format("%.2f%%", damage), position.getAbsoluteX() + 20, position.getAbsoluteY()+ yAdd + 5, -1);
		}
		
		RenderHelper.enableGUIStandardItemLighting();
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, position.getAbsoluteX(), position.getAbsoluteY() + yAdd);
		GL11.glPopMatrix();
		
	}

	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
	}

	@Override
	public ScreenPosition load() {
		
		return pos;
	}
	

}
