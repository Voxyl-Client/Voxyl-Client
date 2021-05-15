package bwp.mods;

import bwp.Client;
import bwp.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Mod {
	
	private boolean isEnabled = true;
	
	protected final Minecraft mc;
	protected final FontRenderer font;
	protected final Client client;

	protected OnToggle onToggle = mod -> {};
	
	public Mod() {
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.client = Client.getInstance();
		
		setEnabled(isEnabled);
	}

	public void setOnToggle(OnToggle onToggle) {
		this.onToggle = onToggle;
	}

	public void setEnabled(boolean isEnabled) {
		
		this.isEnabled = isEnabled;
		
		if(isEnabled) {
			EventManager.register(this);
		}else {
			EventManager.unregister(this);
		}
	}
	public boolean isEnabled() {
		return isEnabled;
	}

	public OnToggle getOnToggle() {
		return onToggle;
	}
}
