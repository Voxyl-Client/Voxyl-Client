package bwp.gui.main;

import java.io.IOException;

import bwp.gui.elements.ModButton;
import bwp.gui.elements.template.CustomButton;
import bwp.gui.window.GuiWindow;

import bwp.gui.hud.HUDManager;
import bwp.mods.Mod;
import bwp.mods.ModAPI;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class MainGui extends GuiWindow {

	private int prevPosX;
	private int prevPosY;
	private final Minecraft mc = Minecraft.getMinecraft();

	private int heightOutOfFrame = 0;
	private int scroll = 0;

	public MainGui() {
		super("Mods");
	}

	@Override
	public void drawWindowParts() {
		super.drawWindowParts();

		buttons.clear();

		int modWidth = this.windowWidth - 20;
		int modHeight = (this.windowHeight - 10) / 10;

		int scrollAreaHeight = windowHeight - fontRendererObj.FONT_HEIGHT - 15 - 5;

		int baseY = y + fontRendererObj.FONT_HEIGHT + 15;
		int yToSet = 0;
		int xToSet = x + 10;

		int yOffset = modHeight / 5;
		int modsRendered = 0;
		for (Mod mod : ModAPI.getInstance().getRegisteredMods()) {

			yToSet = baseY + (yOffset + modHeight) * modsRendered;

			ModButton button = new ModButton(xToSet, yToSet - scroll, modWidth, modHeight, mod);
			buttons.add(button);
			button.draw(mc, Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);

			modsRendered ++;
		}

		int listHeight = yToSet - baseY;

		heightOutOfFrame = listHeight - scrollAreaHeight + yOffset + modHeight;
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int dWheel = Mouse.getEventDWheel();
		int scrollChange = dWheel / 10;

		scroll -= scrollChange;
		if (scroll < 0) scroll = 0;
		if (scroll > heightOutOfFrame) scroll = heightOutOfFrame;

		if (mouseX < x || mouseX > x + windowWidth || mouseY < y || mouseY > y + windowHeight) {
			if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
				HUDManager.getInstance().openConfigScreen();
			}
			return;
		}

		for (CustomButton button : buttons) {
			button.handleInteract(mouseX, mouseY);
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

  
