package bwp.gui.elements;

import bwp.gui.elements.template.CustomButton;
import bwp.utils.ColorUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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

        if (hovered) {
            Render.drawRoundedRectangle(x - 1, y - 1, width + 2, height + 2, mc.displayWidth / 384, Color.BLACK);
        } else {
            Render.drawRoundedRectangle(x, y, width, height, mc.displayWidth / 384, Color.BLACK);
        }

        String displayString = X;
        int color = X_COLOR.getRGB();


        if(checked) {
            displayString = CHECK;
            color = CHECK_COLOR.getRGB();
        }

        float textScale = ((float) height - (((float) height / 5F) * 2F)) / (float) fontrenderer.FONT_HEIGHT;
        Render.drawString(displayString, this.x + (width / 5), this.y + (height / 5), textScale, true, color);
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
