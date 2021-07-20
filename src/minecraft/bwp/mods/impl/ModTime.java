package bwp.mods.impl;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.HUDMod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ModTime extends HUDMod {

	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

	public ModTime() {
		super("Time Display");
	}

	@Override
	protected void init() {
		settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
		settings.addSetting(new ModSetting(1, "24 Hour Time", ModSettingType.CHECKBOX, this, false));
	}

	@Override
	public int getWidth() {
		return font.getStringWidth("12:00 AM");
	}

	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void render() {
		Render.drawHUDString(getCurrentTime(), renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), (boolean) settings.getSetting(0).getValue());
	}

	public String getCurrentTime() {
		String ampm = "AM";

		LocalDateTime now = LocalDateTime.now();
		String time = now.format(dtf);
		String[] timeSplit = time.split(":");
		int hours = Integer.parseInt(timeSplit[0]);

		if (hours > 12 && !(boolean) settings.getSetting(1).getValue()) {
			timeSplit[0] = String.valueOf(hours % 12);
			ampm = "PM";
		} else if ((boolean) settings.getSetting(1).getValue()) {
			ampm = "";
		}

		time = timeSplit[0] + ":" + timeSplit[1] + " " + ampm;
		return time;
	}

	@Override
	public void onSettingChange(int settingId, GuiIntractable intractable) {
		if (settingId == 0) {
			settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
		} else if (settingId == 1) {
			settings.getSetting(1).setValue(((CheckBoxButton) intractable).isChecked());
		}
	}
}
