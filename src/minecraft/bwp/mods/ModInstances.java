package bwp.mods;

import bwp.gui.hud.HUDManager;
import bwp.mods.impl.*;
import bwp.mods.impl.togglemotion.ToggleSneak;
import bwp.mods.impl.togglemotion.ToggleSprint;


public class ModInstances {
	
	private static ModArmorStatus modArmorStatus;
	
	private static ModFPS modFPS;
	
	private static ModCoords modCoords;
	
	private static ModArrow modArrow;
	
	private static ModKeystrokes modKeyStrokes;

	private static ToggleSprint toggleSprint;

	private static ToggleSneak toggleSneak;

	private static ModCPS modCPS;

	private static ModPerspective modPerspective;

	private static ModAutoGG autoGG;

	private static ModTime time;

	private static FullBright fullbright;
	
	public static void register(HUDManager api) {
		
		modArmorStatus = new ModArmorStatus("Armor Status");
		api.register(modArmorStatus);
		
		modFPS = new ModFPS("FPS");
		api.register(modFPS);
		
		modCoords = new ModCoords("Coordinates");
		api.register(modCoords);
		
		modArrow = new ModArrow("Arrow HUD");
		api.register(modArrow);
		
		modKeyStrokes = new ModKeystrokes("Keystrokes");
		api.register(modKeyStrokes);

		toggleSprint = new ToggleSprint("Toggle Sprint");
		api.register(toggleSprint);

		toggleSneak = new ToggleSneak("Toggle Sneak");
		api.register(toggleSneak);

		modCPS = new ModCPS("CPS");
		api.register(modCPS);

		modPerspective = new ModPerspective("Perspective");
		api.register(modPerspective);

		autoGG = new ModAutoGG("Auto GG");
		api.register(autoGG);

		time = new ModTime("Time Display");
		api.register(time);

		fullbright = new FullBright("Full Bright");
		api.register(fullbright);
	}

	public static ToggleSprint getToggleSprint() {
		return toggleSprint;
	}

	public static ToggleSneak getToggleSneak() {
		return toggleSneak;
	}

	public static ModPerspective getModPerspective(){
		return modPerspective;

	}
	public static ModAutoGG getModAutoGG(){
		return autoGG;
	}
}
