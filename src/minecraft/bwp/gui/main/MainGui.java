package bwp.gui.main;

import java.awt.*;
import java.io.IOException;

import bwp.gui.elements.ModButton;
import bwp.gui.hud.IRenderer;
import bwp.gui.window.GuiWindow;

import bwp.gui.hud.HUDManager;
import bwp.mods.Mod;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class MainGui extends GuiWindow {

	private int prevPosX;
	private int prevPosY;
	private Minecraft mc = Minecraft.getMinecraft();

	private int modWidth;
	private int modHeight;

	private int listHeight = 0;

	private int scroll = 0;

	private int heightOutOfFrame = 0;

	public MainGui() {
		super("Mods");
	}

	@Override
	public void drawWindowParts() {
		super.drawWindowParts();

		buttons.clear();

		ScaledResolution sr = new ScaledResolution(mc);

		modWidth = this.windowWidth - 20;
		modHeight = (this.windowHeight - 10) / 10;

		int scrollAreaHeight = windowHeight - fontRendererObj.FONT_HEIGHT - 15 - 5;

		int baseY = y + fontRendererObj.FONT_HEIGHT + 15;
		int yToSet = 0;
		int xToSet = x + 10;

		GL11.glScissor(x * sr.getScaleFactor(), (height * sr.getScaleFactor()) - (y * sr.getScaleFactor() + windowHeight * sr.getScaleFactor()) + 10 * sr.getScaleFactor(), windowWidth * sr.getScaleFactor(), windowHeight * sr.getScaleFactor() - 20 * sr.getScaleFactor() - fontRendererObj.FONT_HEIGHT * sr.getScaleFactor() - 5 * sr.getScaleFactor());
		GL11.glEnable(GL11.GL_SCISSOR_TEST);

		int yOffset = modHeight / 5;
		int modsRendered = 0;
		for (IRenderer renderer : HUDManager.getInstance().getRegisteredRenderers()) {
			Mod mod = (Mod) renderer;

			yToSet = baseY + (yOffset + modHeight) * modsRendered;

			buttons.add(new ModButton(modsRendered, xToSet, yToSet - scroll, modWidth, modHeight, mod));

			Render.drawRoundedRectangle(xToSet, yToSet - scroll, modWidth, modHeight, 5, Color.decode("#261e1d"));
			Render.drawString(mod.getName(), xToSet + 5, yToSet + 10 - scroll, (float) (modHeight - 20) / fontRendererObj.FONT_HEIGHT, true);


			modsRendered ++;
		}

		listHeight = yToSet - baseY;

		heightOutOfFrame = listHeight - scrollAreaHeight;

		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		int dWheel = Mouse.getEventDWheel();
		int scrollChange = dWheel / 10;

		scroll -= scrollChange;
		if (scroll < 0) scroll = 0;
		if (scroll > heightOutOfFrame) scroll = heightOutOfFrame;
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

  
