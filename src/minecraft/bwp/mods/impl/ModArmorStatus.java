package bwp.mods.impl;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;

import net.minecraft.init.Items;
import org.lwjgl.opengl.GL11;

import bwp.gui.hud.RenderInfo;
import bwp.mods.HUDMod;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ModArmorStatus extends HUDMod {

	public ModArmorStatus() {
		super("Armor Status");
	}

	@Override
	protected void init() {
		settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
	}

	@Override
	public int getWidth() {
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
			Render.drawHUDString(String.format("%.2f%%", damage), position.getX() + (int) (20 * renderInfo.getScale()), position.getY() + (int) ((yAdd + 5) * renderInfo.getScale()), position.getScale(), (boolean) settings.getSetting(0).getValue());
		}

		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslatef(position.getX(), position.getY() + (yAdd * renderInfo.getScale()), 1F);
		GL11.glScalef(renderInfo.getScale(), renderInfo.getScale(), 1F);
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, 0, 0);
		GL11.glPopMatrix();
	}

	@Override
	public void onSettingChange(int settingId, GuiIntractable intractable) {
		if (settingId == 0) {
			settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
		}
	}
}
