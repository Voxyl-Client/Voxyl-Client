package bwp.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class CustomButton extends Gui {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean hovered = false;

    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    public CustomButton(int x, int y, int widthIn, int heightIn) {
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void draw(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    public void handleClick(int mouseX, int mouseY) {
        int mButtonPressed = Mouse.getEventButton();
        boolean mButtonState = Mouse.getEventButtonState();

        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
            if (mButtonPressed == 0 && mButtonState) {
                onLeftClick(mouseX, mouseY);
            }
        }
    }

    public void onLeftClick(int mouseX, int mouseY) {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHovered() {
        return hovered;
    }
}
