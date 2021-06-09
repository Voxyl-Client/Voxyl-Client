package bwp.mods;

import bwp.Client;
import bwp.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class Mod {
	
	private boolean isEnabled = true;
	
	protected final Minecraft mc;
	protected final FontRenderer font;
	protected final Client client;
	protected final String name;
	
	public Mod(String name) {
		this.name = name;
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.client = Client.getInstance();
		
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
}
