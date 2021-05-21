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

		toggleSprint = new ToggleSprint();
		api.register(toggleSprint);

		toggleSneak = new ToggleSneak();
		api.register(toggleSneak);

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
		fullbright.onToggle();
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
