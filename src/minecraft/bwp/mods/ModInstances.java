package bwp.mods;

import bwp.mods.impl.*;
import bwp.mods.impl.togglemotion.ToggleSprint;


public class ModInstances {
	
	private static ModArmorStatus modArmorStatus;
	
	private static ModFPS modFPS;
	
	private static ModArrow modArrow;
	
	private static ModKeystrokes modKeyStrokes;

	private static ToggleSprint toggleSprint;

	private static ModCPS modCPS;

	private static ModPerspective modPerspective;

	private static ModAutoGG autoGG;

	private static ModTime time;

	private static FullBright fullbright;

	private static ModPing ping;
	
	public static void register() {
		ModAPI api = ModAPI.getInstance();
		
		modArmorStatus = new ModArmorStatus();
		api.register(modArmorStatus);
		
		modFPS = new ModFPS();
		api.register(modFPS);
		
		modArrow = new ModArrow();
		api.register(modArrow);
		
		modKeyStrokes = new ModKeystrokes();
		api.register(modKeyStrokes);

		toggleSprint = new ToggleSprint();
		api.register(toggleSprint);

		modCPS = new ModCPS();
		api.register(modCPS);

		modPerspective = new ModPerspective();
		api.register(modPerspective);

		autoGG = new ModAutoGG();
		api.register(autoGG);

		time = new ModTime();
		api.register(time);

		fullbright = new FullBright();
		api.register(fullbright);

		ping = new ModPing();
		api.register(ping);
	}

	public static ToggleSprint getToggleSprint() {
		return toggleSprint;
	}

	public static ModPerspective getModPerspective(){
		return modPerspective;

	}
	public static ModAutoGG getModAutoGG(){
		return autoGG;
	}
}
