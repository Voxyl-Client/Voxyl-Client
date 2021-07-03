package bwp.gui.hud;

import java.io.File;

import bwp.event.EventManager;
import bwp.gui.GuiLogin;
import bwp.gui.main.MainGui;
import bwp.mods.ModAPI;
import com.mojang.authlib.exceptions.AuthenticationException;

import bwp.FileManager;
import bwp.SessionChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;

public class HUDManager {
	private final Minecraft mc = Minecraft.getMinecraft();

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

	public void openToggleScreen(){
		MainGui mainGui = new MainGui();
		mainGui.initGui();
		mc.displayGuiScreen(mainGui);
	}

	public void openConfigScreen() {
		mc.displayGuiScreen(new HUDConfigScreen());
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

	public void openMainScreen(){
		MainGui guiMain = new MainGui();
		guiMain.initGui();
		mc.displayGuiScreen(guiMain);
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
				GuiLogin guiError = new GuiLogin(new GuiMultiplayer(new GuiMainMenu()));
				guiError.initGui();
				mc.displayGuiScreen(new GuiLogin(new GuiMultiplayer(new GuiMainMenu())));
			}

		}
		else {
			GuiLogin guiError = new GuiLogin(new GuiMultiplayer(new GuiMainMenu()));
			guiError.initGui();
			mc.displayGuiScreen(new GuiLogin(new GuiMultiplayer(new GuiMainMenu())));
		}
	}
}
