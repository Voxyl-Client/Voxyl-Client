package bwp.mods.impl;

import bwp.utils.Render;

import org.lwjgl.opengl.GL11;

import bwp.gui.hud.RenderInfo;
import bwp.mods.HUDMod;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModArmorStatus extends HUDMod {


	public ModArmorStatus() {
		super("Armor Status");
	}

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
	public void render() {

		for(int i = 0; i< mc.thePlayer.inventory.armorInventory.length; i++) {
		ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
		renderItemStack(i, itemStack, renderInfo);
		}

	}

	@Override
	public void renderDummy() {
		renderItemStack(3, new ItemStack(Items.leather_helmet), renderInfo);
		renderItemStack(2, new ItemStack(Items.leather_chestplate), renderInfo);
		renderItemStack(1, new ItemStack(Items.leather_leggings), renderInfo);
		renderItemStack(0, new ItemStack(Items.leather_boots), renderInfo);
	}

	private void renderItemStack(int i, ItemStack is, RenderInfo position) {
		if (is == null) {
			return;
		}

		int yAdd = (-16 * i) + 48;
		if (is.getItem().isDamageable()) {
			double damage = ((is.getMaxDamage() + is.getItemDamage()) / (double) is.getMaxDamage()) * 100;
			Render.drawString(String.format("%.2f%%", damage), position.getX() + (int) (20 * renderInfo.getScale()), position.getY() + (int) ((yAdd + 5) * renderInfo.getScale()), position.getScale(), true);
		}

		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslatef(position.getX(), position.getY() + (yAdd * renderInfo.getScale()), 1F);
		GL11.glScalef(renderInfo.getScale(), renderInfo.getScale(), 1F);
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, 0, 0);
		GL11.glPopMatrix();
	}
}
