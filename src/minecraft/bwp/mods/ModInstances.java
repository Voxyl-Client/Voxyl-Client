package bwp.mods;

import bwp.gui.hud.HUDManager;
import bwp.mods.impl.*;
import bwp.mods.impl.togglesprint.ToggleSprintSneak;


public class ModInstances {
	
	
	private static ModArmorStatus modArmorStatus;
	
	private static ModFPS modFPS;
	
	private static ModCoords modCoords;
	
	private static ModArrow modArrow;
	
	private static ModKeystrokes modKeyStrokes;

	private static ToggleSprintSneak toggleSprintSneak;

	private static ModCPS modCPS;
	
	public static void register(HUDManager api) {
		
		modArmorStatus = new ModArmorStatus();
		api.register(modArmorStatus);
		
		modFPS = new ModFPS();
		api.register(modFPS);
		
		modCoords = new ModCoords();
		api.register(modCoords);
		
		modArrow = new ModArrow();
		api.register(modArrow);
		
		modKeyStrokes = new ModKeystrokes();
		api.register(modKeyStrokes);

		toggleSprintSneak = new ToggleSprintSneak();
		api.register(toggleSprintSneak);

		modCPS = new ModCPS();
		api.register(modCPS);




		
	}

	public static ToggleSprintSneak getToggleSprintSneak() {
		return toggleSprintSneak;
	}
}
