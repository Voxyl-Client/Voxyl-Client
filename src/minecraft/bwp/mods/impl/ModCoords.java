package bwp.mods.impl;

import bwp.mods.HUDMod;
import bwp.utils.Render;

public class ModCoords extends HUDMod {
	private int color = -1;


	public ModCoords() {
		super("Coords Display");
	}

	@Override
	public int getWidth() {

		return font.getStringWidth(getXYZString());
	}

	@Override
	public int getHeight() {

		return font.FONT_HEIGHT;
	}

	@Override
	public void render() {
		Render.drawString(getXYZString(), renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true, color);
	}

	private String getXYZString() {
		return String.format("XYZ: %.3f / %.3f / %.3f", 
				mc.getRenderViewEntity().posX,
				mc.getRenderViewEntity().getEntityBoundingBox().
				minY,mc.getRenderViewEntity().posZ);

	}



}
