package bwp.gui.main;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.ModButton;
import bwp.gui.elements.template.CustomButton;
import bwp.gui.hud.HUDManager;
import bwp.gui.hud.IRenderer;
import bwp.gui.window.GuiWindow;
import bwp.mods.Mod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;
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

		int settingWidth = this.windowWidth - 40;
		int settingHeight = (this.windowHeight - 10) / 10;

		int scrollAreaHeight = windowHeight - fontRendererObj.FONT_HEIGHT - 15 - 5;

		int baseY = y + fontRendererObj.FONT_HEIGHT + 15;
		int yToSet = 0;
		int xToSet = x + 10;

		int yOffset = settingHeight / 5;
		int settingsRendered = 0;
		for (ModSetting setting : mod.getSettings()) {

			yToSet = baseY + (yOffset + settingHeight) * settingsRendered;

			if (setting.getType() == ModSettingType.CHECKBOX) {
				int checkBoxSize = 20;
				Render.drawString(setting.getName(), xToSet + 5, yToSet - scroll + 10, (float) (settingHeight - 20) / fontRendererObj.FONT_HEIGHT, true);
				CheckBoxButton button = new CheckBoxButton(xToSet + settingWidth - ((settingHeight - checkBoxSize) / 2), yToSet - scroll + ((settingHeight - checkBoxSize) / 2), checkBoxSize, checkBoxSize, (boolean) setting.getBaseValue());
				buttons.add(button);
				button.draw(mc, Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
			}

			settingsRendered ++;
		}

		int listHeight = yToSet - baseY;

		heightOutOfFrame = listHeight - scrollAreaHeight + yOffset + settingHeight;
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		int dWheel = Mouse.getEventDWheel();
		int scrollChange = dWheel / 10;

		scroll -= scrollChange;
		System.out.println(scroll);
		if (scroll < 0) scroll = 0;
		if (scroll > heightOutOfFrame) scroll = heightOutOfFrame;

		for (CustomButton button : buttons) {
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

  
