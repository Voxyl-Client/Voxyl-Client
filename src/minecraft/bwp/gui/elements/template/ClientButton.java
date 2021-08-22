package bwp.gui.elements.template;

import java.awt.Color;

import bwp.utils.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class ClientButton extends GuiButton {

    public ClientButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fr = mc.fontRendererObj;
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            Render.drawRoundedRectangle(this.xPosition, this.yPosition, this.width, this.height, 3, Color.BLACK);
            int j = -1;
            if (this.hovered)
                j = 0xFF5555FF;

            this.drawCenteredString(fr, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }
}