package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;


public class ModFPS extends ModDraggable{



	private boolean isChroma = false;
	private boolean betterUI = false;
	private int color = -1;

	@Override
	public int getWidth() {
		return 50;
	}

	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void changeColor(int colorin) {
		color = colorin;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void render(ScreenPosition pos) {
		if (isChroma) {


			Render.drawChromaString(betterUI ? mc.getDebugFPS() + " FPS" : "FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true);
		}
		else {
			Render.drawString(betterUI ? mc.getDebugFPS() + " FPS" : "FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true, color);
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
