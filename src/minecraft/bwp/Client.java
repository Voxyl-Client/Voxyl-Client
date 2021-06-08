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
	public static final Client getInstance() {
		return INSTANCE;
	}
	
	private DiscordRP discordRP = new DiscordRP();

	private int prevPosX = 0;
	private int prevPosY = 0;

	private HUDManager hudManager;

	public void init() {
		FileManager.init();
		SplashProgress.setProgress(1, "Creating Discord Connection");
		discordRP.start();
		EventManager.register(this);
	}
	public void start() {
		hudManager = HUDManager.getInstance();

		ModInstances.register(hudManager);
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
		int height = mc.displayHeight;
		int width = mc.displayWidth;



		if(Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
			hudManager.openMainScreen();
		}
		if(Minecraft.getMinecraft().gameSettings.CLIENT_LOGIN.isPressed()) {
			hudManager.openLoginScreen();
		}


		prevPosX = mc.displayHeight;
		prevPosY = mc.displayWidth;
	}
}
