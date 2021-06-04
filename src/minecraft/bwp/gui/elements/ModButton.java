package bwp.gui.elements;

import bwp.mods.Mod;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ModButton extends GuiButton {
    private final Mod mod;

    public ModButton(int buttonId, int x, int y, int widthIn, int heightIn, Mod mod) {
        super(buttonId, x, y, widthIn, heightIn, mod.getName());
        this.mod = mod;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.drawButton(Minecraft.getMinecraft(), Mouse.getY(), Mouse.getY());
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fr = mc.fontRendererObj;
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            Color background = Color.decode("#261e1d");
            if (this.hovered) background = Color.decode("#431e1d");

            Render.drawRoundedRectangle(xPosition, yPosition, width, height, 5, background);
            Render.drawString(displayString, xPosition + 5, yPosition + 10, (float) (height - 20) / fr.FONT_HEIGHT, true);
        }
    }

    public Mod getMod() {
        return mod;
    }
}