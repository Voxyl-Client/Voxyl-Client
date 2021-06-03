package bwp.gui.main;

import java.awt.*;
import java.io.IOException;

import bwp.gui.hud.IRenderer;
import bwp.gui.window.GuiWindow;

import bwp.gui.hud.HUDManager;
import bwp.mods.Mod;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class MainGui extends GuiWindow {

	private int prevPosX;
	private int prevPosY;
	private Minecraft mc = Minecraft.getMinecraft();
	private HUDManager hudManager = HUDManager.getInstance();

	private int modWidth;
	private int modHeight;

	private int listHeight = 0;

	private int scroll = 0;

	public MainGui() {
		super("Mods");
	}

	@Override
	public void drawWindowParts() {
		super.drawWindowParts();

		modWidth = this.windowWidth - 20;
		modHeight = (this.windowHeight - 10) / 10;

		buttons.clear();

		int yToSet = 0;

		int yOffset = modHeight / 5;
		int modsRendered = 0;
		for (IRenderer renderer : HUDManager.getInstance().getRegisteredRenderers()) {
			Mod mod = (Mod) renderer;

			yToSet = (yOffset + modHeight) * modsRendered;
			Render.drawRoundedRectangle(0, yToSet - scroll, modWidth, modHeight, 5, Color.decode("#261e1d"));
			Render.drawString(mod.getName(), 5, yToSet + 10 - scroll, (float) (modHeight - 20) / fontRendererObj.FONT_HEIGHT, true);

			modsRendered ++;
		}

		listHeight = yToSet + modHeight;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	// Checking on a rescale, so Gui Screen can adjust.
	public void updateScreen() {

		if(prevPosX != 0 && prevPosY != 0) {

		if(prevPosY != mc.displayHeight || prevPosX != mc.displayWidth) {
			mc.displayGuiScreen(null);
			HUDManager.getInstance().openMainScreen();

		}


		}
		prevPosX = mc.displayWidth;
		prevPosY = mc.displayHeight;
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		int dWheel = Mouse.getEventDWheel();
		int scrollChange = dWheel / 10;

		scroll -= scrollChange;
		if (scroll < 0) scroll = 0;
	}
}

  
