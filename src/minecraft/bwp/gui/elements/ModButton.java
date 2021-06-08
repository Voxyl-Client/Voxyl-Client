package bwp.gui.elements;

import bwp.mods.Mod;
import bwp.mods.toggle.GuiCheckBox;
import bwp.utils.ColorUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ModButton {
    public int x;
    public int y;
    public int width;
    public int height;
    private boolean hovered = false;
    private final Mod mod;
    private final GuiCheckBox checkBox;

    private final int checkBoxSize = 20;

    public ModButton(int x, int y, int widthIn, int heightIn, Mod mod) {
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
        this.mod = mod;
        this.checkBox = new GuiCheckBox(0, this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, this.y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize, mod.isEnabled());
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        checkBox.xPosition = this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize;
        checkBox.yPosition = this.y + ((height - checkBoxSize) / 2);
        this.draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void draw(Minecraft mc, int mouseX, int mouseY) {
        FontRenderer fr = mc.fontRendererObj;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        Color backgroundColor = ColorUtils.fromHex("#FF261e1d");
        if (this.hovered) backgroundColor = ColorUtils.fromHex("#86261e1d");

        Render.drawRoundedRectangle(x, y, width, height, 5, backgroundColor);
        Render.drawString(mod.getName(), x + 5, y + 10, (float) (height - 20) / fr.FONT_HEIGHT, true);

        checkBox.drawButton(mc, checkBox.xPosition, checkBox.yPosition);
    }

    public Mod getMod() {
        return mod;
    }

    public void handleClick(int mouseX, int mouseY) {
        int mButtonPressed = Mouse.getEventButton();
        boolean mButtonState = Mouse.getEventButtonState();

        if (mButtonPressed == 0 && mButtonState) {
            if (checkBox.isMouseOver())
                mod.setEnabled(!mod.isEnabled());
        }
    }
}