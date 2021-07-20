package bwp.mods.impl;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.HUDMod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModArrow extends HUDMod {

	public ModArrow() {
		super("Arrow HUD");
	}

	@Override
	protected void init() {
		settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
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
		Render.drawHUDString(this.getRemainingArrows() + " Arrows", renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), (boolean) settings.getSetting(0).getValue());
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

	@Override
	public void onSettingChange(int settingId, GuiIntractable intractable) {
		if (settingId == 0) {
			settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
		}
	}
}
