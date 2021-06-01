package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;

public class ModCoords extends ModDraggable {
	private int color = -1;


	@Override
	public int getWidth() {

		return font.getStringWidth(getXYZString());
	}

	@Override
	public int getHeight() {

		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos) {
		Render.drawString(getXYZString(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true, color);
	}

	private String getXYZString() {
		return String.format("XYZ: %.3f / %.3f / %.3f", 
				mc.getRenderViewEntity().posX,
				mc.getRenderViewEntity().getEntityBoundingBox().
				minY,mc.getRenderViewEntity().posZ);

	}



}
