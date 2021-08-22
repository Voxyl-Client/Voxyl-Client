package bwp.gui.hud;

import java.io.File;

import bwp.Client;
import bwp.event.EventManager;
import bwp.gui.GuiLogin;
import bwp.gui.GuiAccounts;
import bwp.gui.main.MainGui;

import bwp.FileManager;
import bwp.login.Accounts;
import com.google.gson.Gson;
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
		GuiLogin guiLogin = new GuiLogin(new GuiMultiplayer(new GuiMainMenu()));
		guiLogin.initGui();
		mc.displayGuiScreen(guiLogin);
	}

	public void openMainScreen(){
		MainGui guiMain = new MainGui();
		guiMain.initGui();
		mc.displayGuiScreen(guiMain);
	}

	public void openStartScreen(){
		GuiMainMenu guiMain = new GuiMainMenu();
		guiMain.initGui();
		mc.displayGuiScreen(guiMain);
	}

	public void openAccountsScreen(){
		GuiAccounts guiProfiles = new GuiAccounts();
		guiProfiles.initGui();

		Accounts.loadFromFile();

		this.mc.displayGuiScreen(guiProfiles);
	}
}
