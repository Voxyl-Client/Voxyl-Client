package bwp.gui.elements;

import bwp.gui.elements.template.CustomSlider;
import bwp.utils.ColorUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.Random;

public class SettingSlider extends CustomSlider {
    protected boolean barHovered = false;
    protected int barX;
    protected int barY;
    protected int barHeight;
    protected int barWidth;

    public SettingSlider(int x, int y, int widthIn, int heightIn, int startValue, int endValue, float defaultValue, float currentValue) {
        super(x, y, widthIn, heightIn, startValue, endValue, defaultValue, currentValue);
    }

    @Override
    public void draw(Minecraft mc, int mouseX, int mouseY) {
        super.draw(mc, mouseX, mouseY);

        Render.drawRoundedRectangle(x, y, width, height, mc.displayWidth / 384, ColorUtils.fromHex("#FF261e1d"));

        /*
        if (hovered) {
            Render.drawRoundedRectangle(x - 1, y - 1, width + 2, height + 2, mc.displayWidth / 384, Color.BLACK);
        } else {
        }
        */

        barX = (width / (getEndValue() - getStartValue())) * (int) getValue() + x - 4;
        barY = y - (height / 6);
        barHeight = height + 2 * (height / 6);
        barWidth = 8;

        barHovered = mouseX >= barX && mouseX <= barX + barWidth && mouseY >= barY && mouseY <= barY + barHeight;

        if (barHovered) {
            barX = (width / (getEndValue() - getStartValue())) * (int) getValue() + x - 5;
            barY = y - (height / 5);
            barHeight = height + 2 * (height / 5);
            barWidth = 10;
        }

        Render.drawRoundedRectangle(barX, barY, barWidth, barHeight, mc.displayWidth / 960, ColorUtils.fromHex("#FF080606"));
    }

    @Override
    public void onDrag(int dx) {
        if (barHovered) {
            int change = (width / (getEndValue() - getStartValue())) * dx;
            setValue(change);
        }
    }

    @Override
    public void onLeftClick(int mouseX, int mouseY) {

    }
}