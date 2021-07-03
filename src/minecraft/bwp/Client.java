package bwp;

import bwp.event.EventManager;
import bwp.event.EventTarget;
import bwp.event.impl.ClientTickEvent;
import bwp.gui.SplashProgress;
import bwp.gui.hud.HUDManager;
import bwp.mods.ModInstances;
import net.minecraft.client.Minecraft;

public class Client {
	
	private static final Client INSTANCE = new Client();
	public static Client getInstance() {
		return INSTANCE;
	}
	
	private DiscordRP discordRP = new DiscordRP();

	private final HUDManager hudManager = HUDManager.getInstance();

	public void init() {
		FileManager.init();
		SplashProgress.setProgress(1, "Creating Discord Connection");
		discordRP.start();
		EventManager.register(this);
	}
	public void start() {
		ModInstances.register();
	}
	public void shutdown() {
		discordRP.shutdown();
	}
	
	
	public DiscordRP getDiscordRP() {
		return discordRP;
	}

	@EventTarget
	public void onTick(ClientTickEvent e) {

		Minecraft mc = Minecraft.getMinecraft();

		if(mc.gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
			hudManager.openMainScreen();
		}

		if(mc.gameSettings.CLIENT_LOGIN.isPressed()) {
			hudManager.openLoginScreen();
		}
	}
}
