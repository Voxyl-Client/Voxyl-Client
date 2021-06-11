package bwp.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class GuiElement extends Gui {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GuiElement(int x, int y, int widthIn, int heightIn) {
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
    }

    public void draw(Minecraft mc, int mouseX, int mouseY) {
    }
}
