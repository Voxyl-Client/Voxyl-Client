package bwp.gui.elements;

import bwp.mods.Mod;
import bwp.mods.toggle.GuiCheckBox;
import bwp.utils.ColorUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ModButton extends GuiButton {
    private final Mod mod;
    private final GuiCheckBox checkBox;

    public ModButton(int buttonId, int x, int y, int widthIn, int heightIn, Mod mod) {
        super(buttonId, x, y, widthIn, heightIn, mod.getName());
        this.mod = mod;
        this.checkBox = new GuiCheckBox(0, x, y, 20, 20, mod.isEnabled());
    }

    public void setPosition(int x, int y, int width, int height) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.drawButton(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fr = mc.fontRendererObj;
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            Color backgroundColor = ColorUtils.fromHex("#FF261e1d");
            if (this.hovered) backgroundColor = ColorUtils.fromHex("#43261e1d");

            Render.drawRoundedRectangle(xPosition, yPosition, width, height, 5, backgroundColor);
            Render.drawString(displayString, xPosition + 5, yPosition + 10, (float) (height - 20) / fr.FONT_HEIGHT, true);

            checkBox.drawButton(mc, mouseX, mouseY);
        }
    }

    public Mod getMod() {
        return mod;
    }

    public int getButtonHeight() {
        return height;
    }
}