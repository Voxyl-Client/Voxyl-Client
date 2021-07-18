package bwp.mods.impl;

import bwp.mods.HUDMod;
import bwp.utils.Render;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ModTime extends HUDMod {

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

	public ModTime() {
		super("Time Display");
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

		if (chroma) {
			Render.drawChromaString(getCurrentTime(), renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true);
		}
		else {
			Render.drawString(getCurrentTime(), renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true);
		}
	}

	public String getCurrentTime() {
		String ampm = "AM";

		LocalDateTime now = LocalDateTime.now();
		String time = now.format(dtf);
		String[] timeSplit = time.split(":");
		int hours = Integer.parseInt(timeSplit[0]);

		if (hours > 12) {
			timeSplit[0] = String.valueOf(hours % 12);
			ampm = "PM";
		}

		time = timeSplit[0] + ":" + timeSplit[1] + " " + ampm;
		return time;
	}
}
