package bwp.gui.elements;

import bwp.gui.elements.CustomButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class CheckBoxButton extends CustomButton {
    public boolean checked;
    private static final String X = "✗";
    private static final String CHECK = "✔";
    private static final Color X_COLOR = Color.RED;
    private static final Color CHECK_COLOR = Color.GREEN;

    public CheckBoxButton(int x, int y, int width, int height, boolean checked) {
        super(x, y, width, height);
        this.checked = checked;
    }

    @Override
    public void draw(Minecraft mc, int mouseX, int mouseY) {
        super.draw(mc, mouseX, mouseY);
        FontRenderer fontrenderer = mc.fontRendererObj;
        mc.getTextureManager().bindTexture(buttonTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int i = 1;

        if (hovered) {
            i = 2;
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);

        String displayString = X;
        int color = X_COLOR.getRGB();


        if(checked) {
            displayString = CHECK;
            color = CHECK_COLOR.getRGB();
        }

        drawCenteredString(fontrenderer, displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
    }

    @Override
    public void onLeftClick(int mouseX, int mouseY) {
        checked = !checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
