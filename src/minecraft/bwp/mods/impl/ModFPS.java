package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.HUDMod;
import bwp.utils.Render;


public class ModFPS extends HUDMod {



	private boolean betterUI = false;
	private int color = -1;

	public ModFPS() {
		super("FPS");
	}

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
	public void render() {
		if (chroma) {


			Render.drawChromaString(betterUI ? mc.getDebugFPS() + " FPS" : "FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true);
		}
		else {
			Render.drawString(betterUI ? mc.getDebugFPS() + " FPS" : "FPS: " + mc.getDebugFPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), color, true);
		}
	}

	public void setBetterUI(boolean betterUI) {
		this.betterUI = betterUI;
	}

}
