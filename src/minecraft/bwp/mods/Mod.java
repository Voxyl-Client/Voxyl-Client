package bwp.mods;

import bwp.Client;
import bwp.FileManager;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.settings.ModSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.io.File;

public abstract class Mod {
	
	protected final Minecraft mc;
	protected final FontRenderer font;
	protected final Client client;
	protected final String name;
	protected boolean chroma = false;

	protected ModSettings settings;
	
	public Mod(String name) {
		this.name = name;
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.client = Client.getInstance();

		this.settings = new ModSettings();
	}

	public void onToggle() {
	}

	public String getName() {
		return name;
	}

	public ModSettings getSettings() {
		return settings;
	}

	private File getBaseFolder() {
		File file = new File(FileManager.getModsDirectory(), this.getClass().getSimpleName());
		file.mkdirs();
		return file;
	}

	public void saveDataToFile() {
		FileManager.writeJsonToFile(new File(getBaseFolder(), "settings.json"), settings);
	}

	public void loadDataFromFile() {
		ModSettings loaded = FileManager.readFromJson(new File(getBaseFolder(), "settings.json"), ModSettings.class);

		if (loaded == null){
			loaded = new ModSettings();
			loaded.setEnabled(false);
			saveDataToFile();
		}

		settings = loaded;
	}

	public void onSettingChange(int settingId, GuiIntractable intractable) {

	}
}
