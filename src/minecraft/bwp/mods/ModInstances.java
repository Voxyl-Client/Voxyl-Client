package bwp.mods;

import bwp.gui.hud.HUDManager;
import bwp.mods.impl.ModArmorStatus;
import bwp.mods.impl.ModArrow;
import bwp.mods.impl.ModCoords;
import bwp.mods.impl.ModFPS;
import bwp.mods.impl.ModKeystrokes;


public class ModInstances {
	
	
	private static ModArmorStatus modArmorStatus;
	
	private static ModFPS modFPS;
	
	private static ModCoords modCoords;
	
	private static ModArrow modArrow;
	
	private static ModKeystrokes modKeyStrokes;
	
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
		
	}


}
