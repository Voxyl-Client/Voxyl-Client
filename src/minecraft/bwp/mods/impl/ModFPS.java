package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;


public class ModFPS extends ModDraggable{



	private boolean isChroma = true;
	private boolean betterUI = false;

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
		if (isChroma) {

			Render.drawChromaString(betterUI ? mc.getDebugFPS() + " FPS" : "FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), false);
		}
		else {
			font.drawString(betterUI ? mc.getDebugFPS() + " FPS" : "FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
		}
	}

	public boolean isChroma() {
		return isChroma;
	}

	public void setChroma(boolean chroma) {
		isChroma = chroma;
	}

	public void setBetterUI(boolean betterUI) {
		this.betterUI = betterUI;
	}




}
