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

	protected File settingsFile;

	protected ModSettings settings;
	
	public Mod(String name) {
		this.name = name;
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.client = Client.getInstance();

		this.settings = new ModSettings();

		settingsFile = new File(FileManager.getModsDirectory(), name.replaceAll(" ", "") + "Settings.json");

		init();

		loadDataFromFile();

		onToggle();
	}

	protected void init() {

	}

	public void onToggle() {
	}

	public String getName() {
		return name;
	}

	public ModSettings getSettings() {
		return settings;
	}

	public void saveDataToFile() {
		FileManager.writeJsonToFile(settingsFile, settings);
	}

	public void loadDataFromFile() {
		ModSettings loaded = FileManager.readFromJson(settingsFile, ModSettings.class);

		if (loaded == null){
			loaded = new ModSettings();
			loaded.setEnabled(false);
		} else {
			ModSettings compromisedSettings = new ModSettings();

			compromisedSettings.setEnabled(loaded.getEnabled());

			for (ModSetting settingLocal : settings.getSettings()) {
				for (ModSetting setting : loaded.getSettings()) {
					if (setting.getId() == settingLocal.getId()) {
						settingLocal.setValue(setting.getValue());
					}
				}

				compromisedSettings.addSetting(settingLocal);
			}
			settings = compromisedSettings;
		}
	}

	public void onSettingChange(int settingId, GuiIntractable intractable) {

	}
}
