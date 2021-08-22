package bwp.mods;

import bwp.FileManager;
import bwp.gui.hud.RenderInfo;

import java.io.*;

public abstract class HUDMod extends Mod {

	protected RenderInfo renderInfo;

	protected File renderInfoFile;

	public HUDMod(String name) {
		super(name);

		if (renderInfoFile == null) renderInfoFile = new File(FileManager.getModsDirectory(), name.replaceAll(" ", "") + "Position.json");
	}

	public void setPos(RenderInfo pos) {
		renderInfo = pos;
	}

	@Override
	public void onToggle() {

	}

	@Override
	public void loadDataFromFile() {
		super.loadDataFromFile();

		loadPosFromFile();
	}

	@Override
	public void saveDataToFile() {
		super.saveDataToFile();
		if (renderInfoFile == null) renderInfoFile = new File(FileManager.getModsDirectory(), name.replaceAll(" ", "") + "Position.json");

		FileManager.writeJsonToFile(renderInfoFile, renderInfo);
	}

	public void loadPosFromFile() {

		if (renderInfoFile == null) renderInfoFile = new File(FileManager.getModsDirectory(), name.replaceAll(" ", "") + "Position.json");
		RenderInfo loaded = FileManager.readFromJson(renderInfoFile, RenderInfo.class);

		if (loaded == null) {
			loaded = new RenderInfo(0.0D, 0.0D, 1F);
		}

		renderInfo = loaded;
	}

	public boolean shouldUsePadding() {
		return true;
	}

	public RenderInfo getPos() {
		return renderInfo;
	}

	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
	}

	public void renderDummy() {
		render();
	}

	public void render() {

	};
}