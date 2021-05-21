package bwp.mods;

import bwp.FileManager;
import bwp.gui.hud.IRenderer;
import bwp.gui.hud.ScreenPosition;

import java.io.File;

public abstract class ModDraggable extends Mod implements IRenderer {

	protected ScreenPosition pos;

	public ModDraggable() {
		pos = loadPositionFromFile();
	}


	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
		savePositionToFile();

	}

	private File getBaseFolder() {
		File file = new File(FileManager.getModsDirectory(), this.getClass().getSimpleName());
		file.mkdirs();
		return file;

	}

	private void savePositionToFile() {
		FileManager.writeJsonToFile(new File(getBaseFolder(), "pos.json"), pos);

	}

	@Override
	public ScreenPosition load() {
		return pos;
	}
	private ScreenPosition loadPositionFromFile() {



			ScreenPosition loaded = FileManager.readFromJson(new File(getBaseFolder(), "pos.json"), ScreenPosition.class);

		if(loaded == null){
			loaded = ScreenPosition.fromRelativePosition(0.5, 0.5, 1F);
			this.pos = loaded;
			savePositionToFile();
		}

		return loaded;

	}




	
	public final int getLineOffset(ScreenPosition pos, int lineNum) {
		return pos.getAbsoluteY() + getLineOffset(lineNum);
	}
	private int getLineOffset(int lineNum) {
		return (font.FONT_HEIGHT + 3) * lineNum;
	}

}
