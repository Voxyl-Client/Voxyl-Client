package bwp.mods.impl;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.HUDMod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;


public class ModPing extends HUDMod {

	public ModPing() {
		super("Ping Display");
	}

	@Override
	protected void init() {
		settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
	}

	@Override
	public int getWidth() {
		return font.getStringWidth("200 |||||");
	}

	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void render() {
		int ping;
		if (mc.getCurrentServerData() == null) ping = 0;
		else ping = (int) mc.getCurrentServerData().pingToServer;

		Render.drawHUDString(ping + "", renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), (boolean) settings.getSetting(0).getValue());

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Gui.icons);
		int i = 0;
		int j = 0;

		if (ping < 0)
		{
			j = 5;
		}
		else if (ping < 150)
		{
			j = 0;
		}
		else if (ping < 300)
		{
			j = 1;
		}
		else if (ping < 600)
		{
			j = 2;
		}
		else if (ping < 1000)
		{
			j = 3;
		}
		else
		{
			j = 4;
		}

		new Gui().drawTexturedModalRect(renderInfo.getX() + font.getStringWidth(ping + "") + 3, renderInfo.getY(), 0 + i * 10, 176 + j * 8, 10, 8);
	}

	@Override
	public void onSettingChange(int settingId, GuiIntractable intractable) {
		if (settingId == 0) {
			settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
		}
	}
}
