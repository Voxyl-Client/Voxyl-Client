package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ModTime extends ModDraggable{

	private boolean isChroma = false;
	private int color = -1;
	//TODO - GETTERS + SETTERS

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

	@Override
	public int getWidth() {
		return font.getStringWidth("12:00 AM");
	}

	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {

		if (isChroma) {
			Render.drawChromaString(getCurrentTime(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true);
		}
		else {
			Render.drawString(getCurrentTime(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true, color);
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

	public boolean isChroma() {
		return isChroma;
	}

	public void setChroma(boolean chroma) {
		isChroma = chroma;
	}
}
