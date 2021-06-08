package bwp.gui.hud;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import bwp.gui.GuiErrorLogin;
import bwp.gui.GuiLogin;
import bwp.gui.GuiModMain;
import bwp.gui.GuiModRescale;
import bwp.gui.main.MainGui;
import com.google.common.collect.Sets;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;

import bwp.FileManager;
import bwp.SessionChanger;
import bwp.event.EventManager;
import bwp.event.EventTarget;
import bwp.event.impl.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
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
		/*GuiModToggle guiModToggle = new GuiModToggle();
		guiModToggle.initGui();
		mc.displayGuiScreen(guiModToggle);*/

	}
	public void openLoginScreen(){
		if(FileManager.doesLoginFileExist()) {
			String user = FileManager.readFromJson(new File(FileManager.getCacheDirectory(), "temp_name.json"), String.class);
			String pass = FileManager.readFromJson(new File(FileManager.getCacheDirectory(), "temp_pass.json"), String.class);
			
			try {

			SessionChanger sessionChanger = new SessionChanger();
			sessionChanger.setUser(user, pass);
			


	
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				GuiLogin guiLogin = new GuiLogin(new GuiMultiplayer(new GuiMainMenu()));
				guiLogin.initGui();
				mc.displayGuiScreen(new GuiLogin(new GuiMultiplayer(new GuiMainMenu())));
			}

		}
		else {
		GuiLogin guiLogin = new GuiLogin(new GuiMultiplayer(new GuiMainMenu()));
		guiLogin.initGui();
		mc.displayGuiScreen(new GuiLogin(new GuiMultiplayer(new GuiMainMenu())));
		}

	}
	public void openErrorScreen() {
		if(FileManager.doesLoginFileExist()) {
			String user = FileManager.readFromJson(new File(FileManager.getCacheDirectory(), "temp_name.json"), String.class);
			String pass = FileManager.readFromJson(new File(FileManager.getCacheDirectory(), "temp_pass.json"), String.class);
			
			try {

			SessionChanger sessionChanger = new SessionChanger();
			sessionChanger.setUser(user, pass);
			


	
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				GuiErrorLogin guiError = new GuiErrorLogin(new GuiMainMenu());
				guiError.initGui();
				mc.displayGuiScreen(new GuiErrorLogin(new GuiMainMenu()));
			}

		}
		else {
		GuiErrorLogin guiError = new GuiErrorLogin(new GuiMainMenu());
		guiError.initGui();
		mc.displayGuiScreen(new GuiErrorLogin(new GuiMainMenu()));
		}
	}
	public void openMainScreen(){
		MainGui guiMain = new MainGui();
		guiMain.initGui();
		mc.displayGuiScreen(guiMain);
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
			pos = new ScreenPosition(0.5, 0.5, 1F);
		}
		renderer.render(pos);
  }
}
