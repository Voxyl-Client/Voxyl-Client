package bwp.mods;

import bwp.event.EventManager;
import bwp.event.EventTarget;
import bwp.event.impl.RenderEvent;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.*;

public class ModAPI {

	private ModAPI() {}
	
	private static ModAPI instance = null;
	
	public static ModAPI getInstance() {
		
		if(instance != null) {
			return instance;
		}
		
		instance = new ModAPI();
		EventManager.register(instance);
		
		return instance;
	}
	
	private final List<Mod> registeredMods = new ArrayList<>();
	private final Minecraft mc = Minecraft.getMinecraft();
	
	public void register(Mod...mods) {
		this.registeredMods.addAll(Arrays.asList(mods));
	}
	
	public void unRegister(Mod...mods) {
		Arrays.asList(mods).forEach(this.registeredMods::remove);
	}
	
	public Collection<Mod> getRegisteredMods(){
		return Sets.newHashSet(registeredMods);
	}

	@EventTarget
	public void onRender(RenderEvent event) {
		if (mc.currentScreen == null && !(mc.currentScreen instanceof GuiContainer) && !(mc.currentScreen instanceof GuiChat)) {
			for (Mod mod : registeredMods) {
				if (mod instanceof HUDMod)
					renderMod((HUDMod) mod);
			}
		}
	}

	private void renderMod(HUDMod mod) {
		if (mod.getSettings().getEnabled()) {
			mod.render();
		}
	}
}
