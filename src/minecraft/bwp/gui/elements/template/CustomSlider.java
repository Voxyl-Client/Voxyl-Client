package bwp.gui.elements.template;

import bwp.gui.elements.GuiElement;
import bwp.gui.elements.GuiIntractable;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public abstract class CustomSlider extends GuiElement implements GuiIntractable {
    private final int startValue, endValue;
    private final float defaultValue;
    private float currentValue;
    private int oldX = Mouse.getX();

    public CustomSlider(int x, int y, int widthIn, int heightIn, int startValue, int endValue, float defaultValue, float currentValue) {
        super(x, y, widthIn, heightIn);
        this.startValue = startValue;
        this.endValue = endValue;
        this.defaultValue = defaultValue;
        this.currentValue = currentValue;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void setValue(float value) {
        this.currentValue = value;
    }

    @Override
    public void draw(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    public boolean handleInteract(int mouseX, int mouseY) {

        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            onDrag(Mouse.getX() - oldX);
            System.out.println((Mouse.getX() - oldX) + " " + Mouse.getEventDX());
            oldX = Mouse.getX();
            draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
        }
        return false;
    }

    public void onDrag(int dx) {

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

    public float getValue() {
        return this.currentValue;
    }

    public int getStartValue() {
        return startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public float getDefaultValue() {
        return defaultValue;
    }
}
