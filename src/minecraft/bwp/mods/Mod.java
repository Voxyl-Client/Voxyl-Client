package bwp.mods;

import bwp.Client;
import bwp.FileManager;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.settings.ModSetting;
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

		loadDataFromFile();

		onToggle();
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

			settings = loaded;
		} else {
			ModSettings compromisedSettings = new ModSettings();

			compromisedSettings.setEnabled(loaded.getEnabled());

			for (ModSetting settingLocal : settings.getSettings()) {
				boolean wasFoundLocal = false;
				for (ModSetting setting : loaded.getSettings()) {
					if (setting.getId() == settingLocal.getId()) {
						wasFoundLocal = true;
						settingLocal.setValue(setting.getValue());
						compromisedSettings.addSetting(settingLocal);
					}
				}

				if (!wasFoundLocal) {
					compromisedSettings.addSetting(settingLocal);
				}
			}

			settings = compromisedSettings;

			saveDataToFile();
		}
	}

	public void onSettingChange(int settingId, GuiIntractable intractable) {

	}
}
