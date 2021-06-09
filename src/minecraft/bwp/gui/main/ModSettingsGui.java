package bwp.gui.main;

import bwp.gui.elements.ModButton;
import bwp.gui.hud.HUDManager;
import bwp.gui.hud.IRenderer;
import bwp.gui.window.GuiWindow;
import bwp.mods.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class ModSettingsGui extends GuiWindow {

	private int prevPosX;
	private int prevPosY;
	private final Minecraft mc = Minecraft.getMinecraft();
	private final Mod mod;

	private int scroll = 0;

	private int heightOutOfFrame = 0;

	public ModSettingsGui(Mod mod) {
		super(mod.getName() + " Settings");
		this.mod = mod;
	}

	@Override
	public void drawWindowParts() {
		super.drawWindowParts();

		buttons.clear();

		ScaledResolution sr = new ScaledResolution(mc);
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		int dWheel = Mouse.getEventDWheel();
		int scrollChange = dWheel / 10;

		scroll -= scrollChange;
		if (scroll < 0) scroll = 0;
		if (scroll > heightOutOfFrame) scroll = heightOutOfFrame;

		for (ModButton button : buttons) {
			button.handleClick(mouseX, mouseY);
		}
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
}

  
