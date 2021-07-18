package bwp.mods;

import bwp.FileManager;
import bwp.gui.hud.ScreenPosition;

import java.io.*;

public abstract class HUDMod extends Mod {

	protected ScreenPosition renderInfo;

	protected File renderInfoFile;

	public HUDMod(String name) {
		super(name);

		renderInfoFile = new File(FileManager.getModsDirectory(), name + "Position.json");
	}

	public void changeColor(int colorIn){

	}

	public int getColor(){
		return 0;
	}

	public void setPos(ScreenPosition pos) {
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
		//FileManager.writeJsonToFile(renderInfoFile, renderInfo);
	}

	public void loadPosFromFile() {

		//ScreenPosition loaded = FileManager.readFromJson(renderInfoFile, ScreenPosition.class);

		ScreenPosition loaded = null;
		if (loaded == null) {
			loaded = new ScreenPosition(0.0D, 0.4D, 1F);
			System.out.println("s: " + loaded.getY());
		}

		renderInfo = loaded;

		saveDataToFile();
	}

	public boolean shouldUsePadding() {
		return true;
	}

	public ScreenPosition getPos() {
		return renderInfo;
	}

	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
	}

	public void renderDummy() {
		System.out.println("xDDD");
		System.out.println(renderInfo.getX());
		System.out.println(renderInfo.getY());
		render();
	}

	public void render() {

	};
}