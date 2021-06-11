package bwp.mods;

import bwp.Client;
import bwp.event.EventManager;
import bwp.mods.settings.ModSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.List;

public class Mod {
	
	private boolean isEnabled = true;
	
	protected final Minecraft mc;
	protected final FontRenderer font;
	protected final Client client;
	protected final String name;

	protected final List<ModSetting> settings;
	
	public Mod(String name, List<ModSetting> settings) {
		this.name = name;
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.client = Client.getInstance();

		this.settings = settings;
		
		setEnabled(isEnabled);
	}

	public void setEnabled(boolean isEnabled) {

		boolean oldStatus = this.isEnabled;
		this.isEnabled = isEnabled;

		if(isEnabled) {
			EventManager.register(this);
		}else {
			EventManager.unregister(this);
		}

		if (oldStatus != this.isEnabled)
			onToggle();
	}
	public boolean isEnabled() {
		return isEnabled;
	}

	public void onToggle() {
	}

	public String getName() {
		return name;
	}

	public List<ModSetting> getSettings() {
		return settings;
	}

	public void addSetting(ModSetting setting) {
		settings.add(setting);
	}
}
