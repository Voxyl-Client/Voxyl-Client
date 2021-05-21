package bwp.mods.impl;

import bwp.mods.OnToggle;
import bwp.utils.Render;

import org.lwjgl.opengl.GL11;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModArmorStatus extends ModDraggable {
	


	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public int getHeight() {
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
		if (is == null) {
			return;
		}

		int yAdd = (-16 * i) + 48;
		if (is.getItem().isDamageable()) {
			double damage = ((is.getMaxDamage() + is.getItemDamage()) / (double) is.getMaxDamage()) * 100;
			Render.drawString(String.format("%.2f%%", damage), position.getAbsoluteX() + (int) (20 * pos.getScale()), position.getAbsoluteY() + (int) ((yAdd + 5) * pos.getScale()), position.getScale(), true);
		}

		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslatef(position.getAbsoluteX(), position.getAbsoluteY() + (yAdd * pos.getScale()), 1F);
		GL11.glScalef(pos.getScale(), pos.getScale(), 1F);
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, 0, 0);
		GL11.glPopMatrix();
	}
}
