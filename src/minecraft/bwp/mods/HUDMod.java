package bwp.mods;

import bwp.FileManager;
import bwp.gui.hud.ScreenPosition;
import bwp.mods.settings.ModSettings;

import java.io.File;
import java.util.ArrayList;

public abstract class HUDMod extends Mod {

	protected ScreenPosition pos;

	public HUDMod(String name) {
		super(name);
		loadPosFromFile();
	}

	public void changeColor(int colorIn){

	}

	public int getColor(){
		return 0;
	}

	public void setPos(ScreenPosition pos) {
		this.pos = pos;
	}

	private File getBaseFolder() {
		File file = new File(FileManager.getModsDirectory(), this.getClass().getSimpleName());
		file.mkdirs();
		return file;
	}

	@Override
	public void saveDataToFile() {
		super.saveDataToFile();
		FileManager.writeJsonToFile(new File(getBaseFolder(), "pos.json"), pos);
	}

	@Override
	public void onToggle() {
		if (settings != null) {
			saveDataToFile();
		}
	}

	@Override
	public void loadDataFromFile() {
		super.loadDataFromFile();

		loadPosFromFile();
	}

	public void loadPosFromFile() {
		ScreenPosition loaded = FileManager.readFromJson(new File(getBaseFolder(), "pos.json"), ScreenPosition.class);

		if(loaded == null){
			loaded = ScreenPosition.fromRelativePosition(0.5, 0.5, 1F);
			this.pos = loaded;
			FileManager.writeJsonToFile(new File(getBaseFolder(), "pos.json"), pos);
		} else {
			this.pos = loaded;
		}
	}
	
	public final int getLineOffset(ScreenPosition pos, int lineNum) {
		return pos.getAbsoluteY() + getLineOffset(lineNum);
	}
	private int getLineOffset(int lineNum) {
		return (font.FONT_HEIGHT + 3) * lineNum;
	}

	public boolean shouldUsePadding() {
		return true;
	}

	public ScreenPosition getPos() {
		return pos;
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