package bwp.gui.elements;

import bwp.mods.Mod;
import bwp.utils.ColorUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ModButton extends CustomButton {
    private final Mod mod;
    private final CheckBoxButton checkBox;

    private final int checkBoxSize = 20;

    public ModButton(int x, int y, int widthIn, int heightIn, Mod mod) {
        super(x, y, widthIn, heightIn);
        this.mod = mod;
        this.checkBox = new CheckBoxButton(this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, this.y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize, mod.isEnabled());
    }

    public void setPosition(int x, int y, int width, int height) {
        super.setPosition(x, y, width, height);
        checkBox.setPosition(this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, this.y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize);
        this.draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void draw(Minecraft mc, int mouseX, int mouseY) {
        FontRenderer fr = mc.fontRendererObj;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        Color backgroundColor = ColorUtils.fromHex("#FF261e1d");
        if (this.hovered) backgroundColor = ColorUtils.fromHex("#86261e1d");

        Render.drawRoundedRectangle(x, y, width, height, 5, backgroundColor);
        Render.drawString(mod.getName(), x + 5, y + 10, (float) (height - 20) / fr.FONT_HEIGHT, true);

        checkBox.draw(mc, mouseX, mouseY);
    }

    public Mod getMod() {
        return mod;
    }

    @Override
    public void onLeftClick(int mouseX, int mouseY) {
        checkBox.handleClick(mouseX, mouseY);
        mod.setEnabled(checkBox.isChecked());
    }
}