package bwp.gui.elements;

import bwp.gui.elements.template.CustomButton;
import bwp.gui.main.ModSettingsGui;
import bwp.mods.HUDMod;
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

    private int checkBoxSize = 20;

    public ModButton(int x, int y, int widthIn, int heightIn, Mod mod) {
        super(x, y, widthIn, heightIn);
        this.mod = mod;
        this.checkBox = new CheckBoxButton(this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, this.y + ((height - checkBoxSize) / 2), checkBoxSize, checkBoxSize, mod.getSettings().getEnabled());
    }

    public void setPosition(int x, int y, int width, int height) {
        super.setPosition(x, y, width, height);
        checkBoxSize = height - ((height / 5) * 2);
        checkBox.setPosition(this.x + width - ((height - checkBoxSize) / 2) - checkBoxSize, y + (height / 5), checkBoxSize, checkBoxSize);
        this.draw(Minecraft.getMinecraft(), Mouse.getEventX() * this.width / Minecraft.getMinecraft().displayWidth, this.height - Mouse.getEventY() * this.height / Minecraft.getMinecraft().displayHeight - 1);
    }

    public void draw(Minecraft mc, int mouseX, int mouseY) {
        FontRenderer fr = mc.fontRendererObj;
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        Color backgroundColor = ColorUtils.fromHex("#FF261e1d");
        if (this.hovered) backgroundColor = ColorUtils.fromHex("#86261e1d");

        float textScale = (float) (height - 20) / fr.FONT_HEIGHT;

        if (textScale <= 0) {
            textScale = 1F;
        }

        Render.drawRoundedRectangle(x, y, width, height, mc.displayWidth / 384, backgroundColor);
        Render.drawString(mod.getName(), x + 5, y + ((height - (fr.FONT_HEIGHT * (int) textScale)) / 2), textScale, true);

        checkBoxSize = height - ((height / 5) * 2);
        checkBox.setPosition(checkBox.getX(), y + (height / 5), checkBoxSize, checkBoxSize);
        checkBox.draw(mc, mouseX, mouseY);
    }

    public Mod getMod() {
        return mod;
    }

    @Override
    public void onLeftClick(int mouseX, int mouseY) {
        if (!checkBox.handleInteract(mouseX, mouseY)) {
            ModSettingsGui modSettingsGui = new ModSettingsGui(mod);
            modSettingsGui.initGui();
            Minecraft.getMinecraft().displayGuiScreen(modSettingsGui);
        } else {
            mod.getSettings().setEnabled(checkBox.isChecked());
            if (mod instanceof HUDMod) {
                HUDMod hudMod = (HUDMod) mod;
                hudMod.saveDataToFile();
            } else {
                mod.saveDataToFile();
            }
        }
    }
}