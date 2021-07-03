package bwp.gui.elements;

import bwp.gui.elements.template.CustomButton;
import bwp.mods.settings.ModSetting;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

public class SettingFrame extends CustomButton {
    private final ModSetting setting;
    private CheckBoxButton checkBox;

    private int checkBoxSize = 0;

    public SettingFrame(int x, int y, int widthIn, int heightIn, ModSetting setting) {
        super(x, y, widthIn, heightIn);
        this.setting = setting;
    }

    public void setPosition(int x, int y, int width, int height) {
        super.setPosition(x, y, width, height);
        checkBox.setPosition(this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, this.y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize);
        this.draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void draw(Minecraft mc, int mouseX, int mouseY) {
        FontRenderer fr = mc.fontRendererObj;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        checkBoxSize = (height / 5) * 3;

        Render.drawString(setting.getName(), x + 5, y + 10, (float) (height - 20) / fr.FONT_HEIGHT, true);
        checkBox = new CheckBoxButton(x + width - ((height - checkBoxSize) / 2), y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize, (boolean) setting.getValue());

        checkBox.draw(mc, mouseX, mouseY);
    }

    public ModSetting getSetting() {
        return setting;
    }

    @Override
    public void onLeftClick(int mouseX, int mouseY) {
        if (checkBox.handleInteract(mouseX, mouseY)) {
            //checkBox.draw(Minecraft.getMinecraft(), mouseX, mouseY);
            //setting.getMod().onSettingChange(setting.getId(), checkBox.isChecked());
        }
    }
}