package bwp;

import bwp.event.EventManager;
import bwp.event.EventTarget;
import bwp.event.impl.ClientTickEvent;
import bwp.gui.SplashProgress;
import bwp.gui.hud.HUDManager;
import bwp.login.Accounts;
import bwp.login.SessionChanger;
import bwp.mods.ModInstances;

import net.minecraft.client.Minecraft;

public class Client {
	
	private static final Client INSTANCE = new Client();
	public static Client getInstance() {
		return INSTANCE;
	}

	public void init() {
		FileManager.init();
		SplashProgress.setProgress(1, "Creating Discord Connection");
		EventManager.register(this);

		Accounts.loadFromFile();

		SessionChanger.init();
	}
	public void start() {
		ModInstances.register();
	}

	@EventTarget
	public void onTick(ClientTickEvent e) {

		Minecraft mc = Minecraft.getMinecraft();

		if(mc.gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
			HUDManager.getInstance().openMainScreen();
		}
	}
}
