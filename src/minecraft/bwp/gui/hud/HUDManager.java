package bwp.gui.hud;

import java.util.Collection;
import java.util.Set;

import bwp.mods.toggle.GuiModToggle;
import bwp.mods.toggle.ModEntry;
import bwp.mods.toggle.ScrollListModToggle;
import com.google.common.collect.Sets;

import bwp.event.EventManager;
import bwp.event.EventTarget;
import bwp.event.impl.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;

public class HUDManager {
	
	private HUDManager() {}
	
	private static HUDManager instance = null;
	
	public static HUDManager getInstance() {
		
		if(instance != null) {
			return instance;
		}
		
		instance = new HUDManager();
		EventManager.register(instance);
		
		return instance;
		
		
	}
	
	private Set<IRenderer> registeredRenderers = Sets.newHashSet();
	private Minecraft mc = Minecraft.getMinecraft();
	
	public void register(IRenderer...renderers) {
		for(IRenderer render : renderers) {
			this.registeredRenderers.add(render);
		}
	}
	
	public void unRegister(IRenderer...renderers) {
		for(IRenderer render : renderers) {
			this.registeredRenderers.remove(render);
		}
	}
	
	public Collection<IRenderer> getRegisteredRenderers(){
		return Sets.newHashSet(registeredRenderers);
	}
	public void openConfigScreen() {
		mc.displayGuiScreen(new HUDConfigScreen(this));
		
	}
	public void openToggleScreen(){
		GuiModToggle guiModToggle = new GuiModToggle();
		guiModToggle.initGui();
		mc.displayGuiScreen(new GuiModToggle());

	}

	
	@EventTarget
	public void onRender(RenderEvent e) {
		if(mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiChat) {
			for(IRenderer renderer: registeredRenderers) {
				callRenderer(renderer);
			}
			
		}
	}

	private void callRenderer(IRenderer renderer) {
		if(!renderer.isEnabled()) {
			return;
		
	    }
		ScreenPosition pos = renderer.load();
		if(pos == null) {
			pos = new ScreenPosition(0.5, 0.5);
		}
		renderer.render(pos);
  }
}
