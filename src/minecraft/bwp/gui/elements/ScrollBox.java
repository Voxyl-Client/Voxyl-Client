package bwp.gui.elements;

import bwp.gui.elements.template.CustomButton;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ScrollBox extends GuiElement {
    private final List<CustomButton> buttons = new ArrayList<>();
    private List<GuiElement> elements = new ArrayList<>();
    private int scroll = 0;
    private int heightOutOfFrame = 0;

    public ScrollBox(int x, int y, int widthIn, int heightIn) {
        super(x, y, widthIn, heightIn);
    }

    public void setPosition(int x, int y, int widthIn, int heightIn) {
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
    }

    public void setElements(List<GuiElement> elements) {
        this.elements = elements;
    }

    public void addElement(GuiElement element) {
        this.elements.add(element);
    }

    @Override
    public void draw(Minecraft mc, int mouseX, int mouseY) {
        super.draw(mc, mouseX, mouseY);

        buttons.clear();

        int baseY = y + mc.fontRendererObj.FONT_HEIGHT + 15;
        int yToSet = 0;
        int xToSet = x + 10;

        int yOffset = 10;
        for (GuiElement element : elements) {
            yToSet = baseY + yToSet + (yOffset + element.height);

            element.x = xToSet;
            element.y = yToSet - scroll;
            element.draw(mc, Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);

            if (element instanceof CustomButton) buttons.add((CustomButton) element);
        }

        int listHeight = yToSet - baseY;

        heightOutOfFrame = listHeight - height + yOffset + elements.get(elements.size() - 1).height;
    }

    public void handleMouse() {
        int dWheel = Mouse.getEventDWheel();
        int scrollChange = dWheel / 10;

        scroll -= scrollChange;
        if (scroll < 0) scroll = 0;
        if (scroll > heightOutOfFrame) scroll = heightOutOfFrame;

        for (CustomButton button : buttons) {
            button.handleClick(Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
        }
    }
}
