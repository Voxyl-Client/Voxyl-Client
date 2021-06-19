package bwp.gui.elements;

import bwp.gui.elements.template.CustomButton;
import bwp.gui.main.ModSettingsGui;
import bwp.mods.Mod;
import bwp.mods.settings.ModSetting;
import bwp.utils.ColorUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class SettingFrame extends CustomButton {
    private final ModSetting setting;
    private final CheckBoxButton checkBox;

    private final int checkBoxSize = 20;

    public SettingFrame(int x, int y, int widthIn, int heightIn, ModSetting setting) {
        super(x, y, widthIn, heightIn);
        this.setting = setting;
        this.checkBox = new CheckBoxButton(this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, this.y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize, (boolean) setting.getBaseValue());
    }

    public void setPosition(int x, int y, int width, int height) {
        super.setPosition(x, y, width, height);
        checkBox.setPosition(this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, this.y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize);
        this.draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void draw(Minecraft mc, int mouseX, int mouseY) {
        FontRenderer fr = mc.fontRendererObj;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        Render.drawString(setting.getName(), x + 5, y + 10, (float) (height - 20) / fr.FONT_HEIGHT, true);

        checkBox.draw(mc, mouseX, mouseY);
    }

    public ModSetting getSetting() {
        return setting;
    }

    @Override
    public void onLeftClick(int mouseX, int mouseY) {
        /*if (!checkBox.handleClick(mouseX, mouseY)) {

        } else {
            mod.setEnabled(checkBox.isChecked());
        }*/
    }
}