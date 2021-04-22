package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;

public class ModFPS extends ModDraggable{
	

	

	@Override
	public int getWidth() {
		return 50;
	}


	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {
		font.drawString("FPS: " +  mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
		
	}





}
